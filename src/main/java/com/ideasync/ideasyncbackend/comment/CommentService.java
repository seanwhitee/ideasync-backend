package com.ideasync.ideasyncbackend.comment;

import com.ideasync.ideasyncbackend.comment.dto.CommentChunk;
import com.ideasync.ideasyncbackend.comment.dto.CommentResponse;
import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    public final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public String addReply(Long userId, Long projectId, Long parentId, String text) {
        if (parentId != null){
            Comment parentComment = commentRepository.findCommentById(parentId);
            if (parentComment == null) {
                return "Parent comment not found";
            }
        }

        // check parent project id is same as project id
        Comment parentComment = commentRepository.findCommentById(parentId);
        if (parentComment != null && !parentComment.getProject().getId().equals(projectId)) {
            return "Parent comment not found";
        }


        // only creator and mentor allow to comment
        User user = userRepository.findUserById(userId);
        Project project = projectRepository.findProjectById(projectId);

        if(user == null) {
            return "User not found";
        }

        if(project == null) {
            return "Project not found";
        }

        if (user.getUserRole().getId() == 3) {
            return "Only creator and mentor allow to reply";
        }

        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setProject(project);
        comment.setParentId(parentId);

        try {
            commentRepository.save(comment);
            return "Reply added successfully";
        } catch (Exception e) {
            System.out.println(e);
            return "Failed to add reply";
        }

    }

    public String addComment(Long userId, Long projectId, String text) {
        // only creator and mentor allow to comment
        User user = userRepository.findUserById(userId);
        Project project = projectRepository.findProjectById(projectId);

        if(user == null) {
            return "User not found";
        }

        if(project == null) {
            return "Project not found";
        }

        if (user.getUserRole().getId() == 3) {
            return "Only creator and mentor allow to comment";
        }

        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setProject(project);

        try {
            commentRepository.save(comment);
            return "Comment added successfully";
        } catch (Exception e) {
            System.out.println(e);
            return "Failed to add comment";
        }
    }

    public List<CommentChunk> getAllCommentChunks(Long projectId) {
        List<Comment> comments = commentRepository.findCommentsByProjectId(projectId);
        List<CommentChunk> commentChunks = new ArrayList<>();


        // loop through all comments, if the parentId is NULL, mean that is parent comment
        for (Comment comment : comments) {
            if (comment.getParentId() == null) {
                CommentChunk commentChunk = new CommentChunk(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getProject().getId(),
                        comment.getText(),
                        new ArrayList<>(),
                        comment.getUser().getAvatarUrl(),
                        comment.getUser().getNickName(),
                        comment.getCreateAt()
                );

                // loop through all comments, if the parentId is the parent comment id, mean that is replied
                for (Comment reply : comments) {
                    if (reply.getParentId() != null && reply.getParentId().equals(comment.getId())) {
                        CommentResponse commentResponse = new CommentResponse(
                                reply.getId(),
                                reply.getText(),
                                reply.getUser().getId(),
                                reply.getProject().getId(),
                                reply.getParentId(),
                                reply.getUser().getAvatarUrl(),
                                reply.getUser().getNickName(),
                                reply.getCreateAt()
                        );
                        commentChunk.getchildren().add(commentResponse);
                    }
                }

                commentChunks.add(commentChunk);
            }
        }
        return commentChunks;
    }
}
