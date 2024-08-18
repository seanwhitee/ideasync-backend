package com.ideasync.ideasyncbackend.comment;

import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);
    List<Comment> findCommentsByProjectId(Long projectId);
    List<Comment> findCommentsByProject(Project project);
    List<Comment> findCommentsByUser(User user);
}
