package com.divakar.FoodApp.email_notification.dtos;

import java.time.LocalDateTime;

import com.divakar.FoodApp.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class NotificationDTO {

    private Long Id;
    private String subject;

    @NotBlank(message = "Recipient email cannot be blank")
    private String recipient;

    private String body;

    private NotificationType type;

    private final LocalDateTime createdAt;
    private boolean isHtml;
}
