package com.divakar.FoodApp.email_notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divakar.FoodApp.email_notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
