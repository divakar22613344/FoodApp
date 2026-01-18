package com.divakar.FoodApp.auth_users.services;

import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.NotFoundException;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.divakar.FoodApp.auth_users.dtos.UserDTO;
import com.divakar.FoodApp.auth_users.entity.User;
import com.divakar.FoodApp.auth_users.repository.UserRepository;
import com.divakar.FoodApp.aws.AWSS3Service;
import com.divakar.FoodApp.email_notification.dtos.NotificationDTO;
import com.divakar.FoodApp.email_notification.services.NotificationService;
import com.divakar.FoodApp.response.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final AWSS3Service awss3Service;

    @Override
    public User getCurrentLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Override
    public Response<List<UserDTO>> getAllUsers() {

        log.info("INSIDE getAllUsers()");
        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOS = modelMapper.map(userList, new TypeToken<List<UserDTO>>() {
        }.getType());

        return Response.<List<UserDTO>>builder().statusCode(HttpStatus.OK.value())
                .message("All users retrieved successfully").data(userDTOS).build();
    }

    @Override
    public Response<UserDTO> getOwnAccountDetails() {
        log.info("INSIDE getOwnAccountDetails");

        User user = getCurrentLoggedInUser();

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.<UserDTO>builder().statusCode(HttpStatus.OK.value()).message("success").data(userDTO).build();

    }

    @Override
    public Response<?> updateOwnAccount(UserDTO userDTO) throws BadRequestException {
        log.info("INSIDE updateOwnAccount()");
        // fetched current logged in user
        User user = getCurrentLoggedInUser();

        String profileUrl = user.getProfileUrl();

        MultipartFile imageFile = userDTO.getImageFile();

        // check if new image file was provided

        if (imageFile != null && !imageFile.isEmpty()) {
            // delete old image in cloud if it exists
            if (profileUrl != null && !profileUrl.isEmpty()) {
                String keyName = profileUrl.substring(profileUrl.lastIndexOf("/") + 1);
                awss3Service.deleteFile("profile/" + keyName);
                log.info("Deleted old profile image from amazon s3");
            }

            // upload new image
            String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            URL newImageUrl = awss3Service.uploadFile("profile/" + imageName, imageFile);

            user.setProfileUrl(newImageUrl.toString());
        }

        // update other user details

        if (userDTO.getName() != null)
            user.setName(userDTO.getName());
        if (userDTO.getPhoneNumber() != null)
            user.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getAddress() != null)
            user.setAddress(userDTO.getAddress());
        if (userDTO.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BadRequestException("Email Already Exists");
            }
            
        }

        // save the user
        userRepository.save(user);

        return Response.builder().statusCode(HttpStatus.OK.value()).message("Account Updated Successfully").build();
    }

    @Override
    public Response<?> deactivateOwnAccount() {
        log.info("INSIDE deactivateOwnAccount()");

        User user = getCurrentLoggedInUser();

        // deactivate the user
        user.setActive(false);
        userRepository.save(user);

        // SEND email after deactivation
        NotificationDTO notificationDTO = NotificationDTO.builder().recipient(user.getEmail())
                .subject("Account Deactivated")
                .body("Your Account has been deactivated. If this was a mistake please contact support").build();

        
        notificationService.sendEmail(notificationDTO);
        return Response.builder().statusCode(HttpStatus.OK.value()).message("Account De-Activated Successfully").build();

    }

}
