package main.java.com.genc.project.controllers;

import main.java.com.genc.project.services.CommunicationService;
import main.java.com.genc.project.models.ForumPost;
import main.java.com.genc.project.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/forum")
public class ForumController {

    private final CommunicationService communicationService;

    @Autowired
    public ForumController(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @PostMapping("/posts")
    public ForumPost createPost(@RequestBody ForumPost forumPost) {
        return communicationService.savePost(forumPost);
    }

    @GetMapping("/posts")
    public List<ForumPost> getAllPosts() {
        return communicationService.findAllPosts();
    }

    @GetMapping("/posts/user/{userId}")
    public List<ForumPost> getPostsByUserId(@PathVariable String userId) {
        return communicationService.findPostsByUserId(userId);
    }

    @PostMapping("/notifications")
    public Notification createNotification(@RequestBody Notification notification) {
        return communicationService.saveNotification(notification);
    }

    @GetMapping("/notifications")
    public List<Notification> getAllNotifications() {
        return communicationService.findAllNotifications();
    }

    @GetMapping("/notifications/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable String userId) {
        return communicationService.findNotificationsByUserId(userId);
    }
}
