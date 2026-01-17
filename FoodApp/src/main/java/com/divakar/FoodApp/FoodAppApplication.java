package com.divakar.FoodApp;

import java.beans.BeanProperty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.divakar.FoodApp.email_notification.dtos.NotificationDTO;
import com.divakar.FoodApp.email_notification.services.NotificationService;
import com.divakar.FoodApp.enums.NotificationType;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableAsync
// @RequiredArgsConstructor
public class FoodAppApplication {

	//private final NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(FoodAppApplication.class, args);
	}

	// @Bean
	// CommandLineRunner runner(){
	// 	return args -> {
	// 		NotificationDTO notificationDTO = NotificationDTO.builder().recipient("divakarc72@gmail.com").subject("Hello mate").body("Hey this is a test email").type(NotificationType.EMAIL).build();

	// 		notificationService.sendEmail(notificationDTO);
	// 	};
	// }

}
