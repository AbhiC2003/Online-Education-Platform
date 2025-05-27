package main.java.com.genc.project.repositories;

import main.java.com.genc.project.entities.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    // Additional query methods can be defined here if needed
}
