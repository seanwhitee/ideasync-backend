package com.ideasync.ideasyncbackend.comment;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Comment findCommentById(UUID id);
    List<Comment> findCommentsByProjectId(UUID projectId);
    List<Comment> findCommentsByProject(Project project);
    List<Comment> findCommentsByUser(User user);
}
