package com.jsp.springboot.library.serviceimplementation;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.springboot.library.entity.Notification;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.repository.NotificationRepository;
import com.jsp.springboot.library.repository.UserRepository;
import com.jsp.springboot.library.service.NotificationService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

	 @Autowired
	    private NotificationRepository notificationRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Override
	    public void sendNotification(Long userId, String message) {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        Notification notification = new Notification();
	        notification.setUser(user);
	        notification.setMessage(message);
	        notification.setSentAt(LocalDateTime.now()); // Ensure sentAt is not null

	        notificationRepository.save(notification);
	    }


    @Override
    public List<Notification> getNotificationsByUser(User user) {
        return notificationRepository.findByUser(user);
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setRead(true); // Assuming you have a "read" field in Notification entity
            notificationRepository.save(notification);
        } else {
            throw new RuntimeException("Notification not found.");
        }
    }
    
    
}

