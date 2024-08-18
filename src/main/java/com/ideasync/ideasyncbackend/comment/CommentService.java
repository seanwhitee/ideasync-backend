package com.ideasync.ideasyncbackend.comment;

import com.ideasync.ideasyncbackend.comment.dto.CommentChunk;
import com.ideasync.ideasyncbackend.comment.dto.CommentResponse;
import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.project.ProjectRepository;
import com.ideasync.ideasyncbackend.user.User;
import com.ideasync.ideasyncbackend.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          ProjectRepository projectRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public CommentChunk setCommentChunkResponse(Comment comment) {
        return new CommentChunk(
                comment.getId(),
                comment.getUser().getId(),
                comment.getProject().getId(),
                comment.getText(),
                new ArrayList<>(),
                comment.getUser().getAvatarUrl(),
                comment.getUser().getNickName(),
                comment.getCreateAt()
        );
    }

    public CommentResponse setCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getUser().getId(),
                comment.getProject().getId(),
                comment.getParentId(),
                comment.getUser().getAvatarUrl(),
                comment.getUser().getNickName(),
                comment.getCreateAt()
        );
    }

    private Comment validateAndSetComment(Long userId, Long projectId, String text) {
        User user = userRepository.findUserById(userId);
        Project project = projectRepository.findProjectById(projectId);

        if(user == null) {
            return null;
        }

        if(project == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setProject(project);
        return  comment;
    }

    public CommentResponse addReply(Long userId, Long projectId, Long parentId, String text) {
        if (parentId != null){
            Comment parentComment = commentRepository.findCommentById(parentId);
            if (parentComment == null) {
                return null;
            }
        }

        // check parent project id is same as project id
        Comment parentComment = commentRepository.findCommentById(parentId);
        if (parentComment != null && !parentComment.getProject().getId().equals(projectId)) {
            return null;
        }


        Comment comment = validateAndSetComment(userId, projectId, text);

        try {
            Comment savedComment =  commentRepository.save(Optional.ofNullable(comment).orElseThrow());
            return setCommentResponse(savedComment);
        } catch (Exception e) {
            logger.error("Failed to add reply", e);
            return null;
        }

    }


    public CommentChunk addComment(Long userId, Long projectId, String text) {

        Comment comment = validateAndSetComment(userId, projectId, text);


        try {
            Comment savedComment = commentRepository.save(Optional.ofNullable(comment).orElseThrow());
            return setCommentChunkResponse(savedComment);
        } catch (Exception e) {
            logger.error("Failed to add comment", e);
            return null;
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
