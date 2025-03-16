package com.jsp.springboot.library.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.jsp.springboot.library.entity.Notification;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.utility.ResponseStructure;

public interface NotificationService {

    ResponseEntity<ResponseStructure<String>> sendNotification(Long userId, String message);

    ResponseEntity<ResponseStructure<List<Notification>>> getNotificationsByUser(Long userId);

    ResponseEntity<ResponseStructure<String>> markNotificationAsRead(Long notificationId);
}
