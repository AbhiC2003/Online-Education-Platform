package main.java.com.genc.project.services;

import com.genc.project.models.ForumPost;
import com.genc.project.models.Notification;
import com.genc.project.repositories.ForumPostRepository;
import com.genc.project.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class CommunicationService {

    private final ForumPostRepository forumPostRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public CommunicationService(ForumPostRepository forumPostRepository, NotificationRepository notificationRepository) {
        this.forumPostRepository = forumPostRepository;
        this.notificationRepository = notificationRepository;
    }

    public ForumPost savePost(ForumPost forumPost) {
        forumPost.setTimestamp(java.time.LocalDate.now()); // Set the current date as the timestamp
        return forumPostRepository.save(forumPost);
    }

    public List<ForumPost> findAllPosts() {
        return forumPostRepository.findAll();
    }

    public List<ForumPost> findPostsByUserId(String userId) {
        return forumPostRepository.findByUserId(userId);
    }

    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> findNotificationsByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }
}

