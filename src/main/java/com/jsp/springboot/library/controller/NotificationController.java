package com.jsp.springboot.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.springboot.library.entity.Notification;
import com.jsp.springboot.library.service.NotificationService;
import com.jsp.springboot.library.utility.ResponseStructure;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<ResponseStructure<String>> sendNotification(
            @RequestParam Long userId, @RequestParam String message) {
        return notificationService.sendNotification(userId, message);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<Notification>>> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getNotificationsByUser(userId);
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<ResponseStructure<String>> markAsRead(@PathVariable Long notificationId) {
        return notificationService.markNotificationAsRead(notificationId);
    }
}
