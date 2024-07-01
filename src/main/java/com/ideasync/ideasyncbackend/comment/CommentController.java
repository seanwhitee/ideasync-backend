package com.ideasync.ideasyncbackend.comment;

import com.ideasync.ideasyncbackend.comment.dto.CommentChunk;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addReply")
    public String addComment(@RequestParam Long userId, @RequestParam Long projectId, @RequestParam Long parentId, @RequestParam String text) {
        return commentService.addReply(userId, projectId, parentId, text);
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam Long userId, @RequestParam Long projectId, @RequestParam String text) {
        return commentService.addComment(userId, projectId, text);
    }

    @GetMapping("/getAllCommentChunks")
    public List<CommentChunk> getAllCommentChunks(@RequestParam Long projectId) {
        return commentService.getAllCommentChunks(projectId);
    }
}
