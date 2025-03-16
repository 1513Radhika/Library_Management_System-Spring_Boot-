package com.jsp.springboot.library.service;



import java.util.List;

import com.jsp.springboot.library.entity.Notification;
import com.jsp.springboot.library.entity.User;

public interface NotificationService {

    void sendNotification(Long userId, String message);  // Send notification

    List<Notification> getNotificationsByUser(User user);  // Get notifications for a user

    void markNotificationAsRead(Long notificationId);  // Mark a notification as read
}
