package com.example.backend.controller;

import com.example.backend.model.entity.Conversation;
import com.example.backend.model.entity.Message;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/conversations/private/{userId2}")
    public ResponseEntity<ApiDataResponse<Conversation>> createOrGetPrivateConversation(
            @RequestParam Long currentUserId,
            @PathVariable Long userId2) {
        Conversation conversation = messageService.createOrGetPrivateConversation(currentUserId, userId2);
        return ResponseEntity.ok(ApiDataResponse.<Conversation>builder()
                .status(true)
                .message("Conversation retrieved/created")
                .data(conversation)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/conversations/group")
    public ResponseEntity<ApiDataResponse<Conversation>> createGroupConversation(
            @RequestParam Long currentUserId,
            @RequestParam String name,
            @RequestBody List<Long> memberIds) {
        Conversation conversation = messageService.createGroupConversation(currentUserId, name, memberIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<Conversation>builder()
                .status(true)
                .message("Group conversation created")
                .data(conversation)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<Message>> sendMessage(
            @RequestParam Long currentUserId,
            @RequestParam Long conversationId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String mediaUrl,
            @RequestParam(required = false) String emoji) {
        Message message = messageService.sendMessage(currentUserId, conversationId, content, mediaUrl, emoji);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<Message>builder()
                .status(true)
                .message("Message sent")
                .data(message)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @DeleteMapping("/{messageId}/recall")
    public ResponseEntity<ApiDataResponse<Boolean>> recallMessage(
            @RequestParam Long currentUserId,
            @PathVariable Long messageId) {
        Boolean result = messageService.recallMessage(currentUserId, messageId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Message recalled")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<ApiDataResponse<Page<Message>>> getMessages(
            @RequestParam Long currentUserId,
            @PathVariable Long conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Message> messages = messageService.getMessages(currentUserId, conversationId, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<Message>>builder()
                .status(true)
                .message("Messages retrieved")
                .data(messages)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PutMapping("/conversations/{conversationId}/seen")
    public ResponseEntity<ApiDataResponse<Boolean>> markConversationAsSeen(
            @RequestParam Long currentUserId,
            @PathVariable Long conversationId) {
        Boolean result = messageService.markConversationAsSeen(currentUserId, conversationId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Conversation marked as seen")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
