package com.jsp.springboot.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.springboot.library.entity.Notification;
import com.jsp.springboot.library.entity.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);  // Get notifications for a specific user

    List<Notification> findByIsReadFalse();  // Find unread notifications
}
