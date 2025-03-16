package com.jsp.springboot.library.serviceimplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.springboot.library.entity.Notification;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.exception.NotificationNotFoundException;
import com.jsp.springboot.library.exception.UserNotFoundException;
import com.jsp.springboot.library.repository.NotificationRepository;
import com.jsp.springboot.library.repository.UserRepository;
import com.jsp.springboot.library.service.NotificationService;
import com.jsp.springboot.library.utility.ResponseStructure;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Send a notification to a user
    @Override
    public ResponseEntity<ResponseStructure<String>> sendNotification(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setSentAt(LocalDateTime.now());
        notification.setRead(false);

        notificationRepository.save(notification);

        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        responseStructure.setMessage("Notification sent successfully");
        responseStructure.setStatuscode(HttpStatus.CREATED.value());
        responseStructure.setEntity("Notification sent to User ID: " + userId);

        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
    }

    // ✅ Get all notifications for a user
    @Override
    public ResponseEntity<ResponseStructure<List<Notification>>> getNotificationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        List<Notification> notifications = notificationRepository.findByUser(user);

        ResponseStructure<List<Notification>> responseStructure = new ResponseStructure<>();
        responseStructure.setMessage("Notifications retrieved successfully");
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setEntity(notifications);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    // ✅ Mark a notification as read
    @Override
    public ResponseEntity<ResponseStructure<String>> markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with ID " + notificationId + " not found"));

        notification.setRead(true);
        notificationRepository.save(notification);

        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        responseStructure.setMessage("Notification marked as read successfully");
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setEntity("Notification ID " + notificationId + " is marked as read");

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }


}
