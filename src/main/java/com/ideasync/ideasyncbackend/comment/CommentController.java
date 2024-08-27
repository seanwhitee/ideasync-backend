package com.ideasync.ideasyncbackend.comment;

import com.ideasync.ideasyncbackend.comment.dto.CommentChunk;
import com.ideasync.ideasyncbackend.comment.dto.CommentResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addReply")
    public CommentResponse addComment(@RequestParam UUID userId, @RequestParam UUID projectId, @RequestParam UUID parentId, @RequestParam String text) {
        return commentService.addReply(userId, projectId, parentId, text);
    }

    @PostMapping("/addComment")
    public CommentChunk addComment(@RequestParam UUID userId, @RequestParam UUID projectId, @RequestParam String text) {
        return commentService.addComment(userId, projectId, text);
    }

    @GetMapping("/getAllCommentChunks")
    public List<CommentChunk> getAllCommentChunks(@RequestParam UUID projectId) {
        return commentService.getAllCommentChunks(projectId);
    }
}
