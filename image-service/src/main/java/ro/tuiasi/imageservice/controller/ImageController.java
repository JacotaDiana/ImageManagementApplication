package ro.tuiasi.imageservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.tuiasi.imageservice.service.ImageService;
import ro.tuiasi.imageservice.models.Image;
import ro.tuiasi.imageservice.payload.ResponseMessage;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/addImages", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseMessage> addImages(@RequestParam("files") MultipartFile[] images, @RequestParam (value = "userId") int userId) throws IOException {
        return imageService.addImages(userId, images);
    }

    @PostMapping("/deleteImages")
    public ResponseEntity<?> deleteImages(@RequestParam("images") long[] imageIds, @RequestParam (value = "userId") int userId) {
        return imageService.deleteImages(userId, imageIds);
    }

    @GetMapping("/getImages")
    public List<Image> getImage(@RequestParam (value = "userId") int userId) throws IOException {
        return imageService.getAllImagesByUserId(userId);
    }
}
