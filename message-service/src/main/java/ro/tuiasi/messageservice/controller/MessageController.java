package ro.tuiasi.messageservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuiasi.messageservice.payload.ImageResponse;
import ro.tuiasi.messageservice.services.MessageService;
import ro.tuiasi.messageservice.payload.AllMessagesResponse;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/getMessages")
    public List<AllMessagesResponse> getMessages(@RequestParam(value = "userId") int userId) throws IOException {
        return messageService.getAllMessagesByUserId(userId);
    }

    @GetMapping("/getMessage/{messageId}")
    public List<ImageResponse> getMessageImages(@PathVariable(value = "messageId") long messageId, @RequestParam(value = "userId") int userId) {
        return messageService.getMessage(messageId, userId);
    }

    @PostMapping("/deleteMessage/{messageId}")
    public ResponseEntity<?> deleteMessage(@RequestParam(value = "messageId") long messageId, @RequestParam(value = "userId") int userId) {
        return messageService.deleteMessage(messageId, userId);
    }

    @PostMapping(value = "/sendMessage", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addImages(@RequestParam("imagesIds") long[] imagesIds, @RequestParam (value = "userId") int userId, @RequestParam (value = "title") String title, @RequestParam (value = "messageBody") String messageBody,@RequestParam (value = "receiverUsername") String receiverUsername) throws IOException {
        return messageService.sendMessage(imagesIds, userId, title, messageBody, receiverUsername);
    }
}
