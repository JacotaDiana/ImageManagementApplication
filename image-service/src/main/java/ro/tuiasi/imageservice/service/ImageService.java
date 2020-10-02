package ro.tuiasi.imageservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import ro.tuiasi.imageservice.messaging.Producer;
import ro.tuiasi.imageservice.models.User;
import ro.tuiasi.imageservice.repository.UserRepository;
import ro.tuiasi.imageservice.messaging.*;
import ro.tuiasi.imageservice.models.Image;
import ro.tuiasi.imageservice.payload.ResponseMessage;
import ro.tuiasi.imageservice.repository.ImageRepository;

import java.io.IOException;
import java.util.*;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private Messaging messaging;

    @Autowired
    Producer producer;


    public List<Image> getAllImagesByUserId(int userId) {
        if (userRepository.findById((userId)).isPresent())
        {
            User user = userRepository.findById(userId).get();
            List<Image> retrievedImages = imageRepository.findByUserId(userId);
            List<Image> decompressedImages = new ArrayList<>();
            for(Image img : retrievedImages) {
                decompressedImages.add(new Image(img.getId(), img.getName(), img.getType(), img.getSize(), img.getDataAdaugarii(), Compression.decompressBytes(img.getImage()), user));
            }
            return decompressedImages;
        }
        else return null;

    }


    public void deleteUserAndImages(int userId) {
        List<Image> listImage = imageRepository.findByUserId(userId);

        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ResponseEntity<ResponseMessage> addImages(int userId, MultipartFile[] images) {
        String message = "";

        User user = userRepository.findById(userId).get();

        try {
            List<Image> listImages = new ArrayList<>();
            List<Img> imagesToSend = new ArrayList<>();
            Arrays.asList(images).stream().forEach(image -> {
                Image img = null;
                try {
                    img = new Image(image.getOriginalFilename(), image.getContentType(), image.getSize(), new Date(System.currentTimeMillis()), Compression.compressBytes(image.getBytes()), user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Image imageToAdd = imageRepository.save(img);
                listImages.add(imageToAdd);
                imagesToSend.add(new Img(imageToAdd.getId(), imageToAdd.getImage()));
            });
            if(imagesToSend.size() > 0){
                ImageMess messageWithImages = new ImageMess(user.getId(), imagesToSend);
                sendToMessage(messageWithImages, 75);
            }
            message = "Uploaded the files successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> deleteImages(int userId, long[] imagesIds) {
        List<Image> newList = new ArrayList<>();
        for(long idImagine : imagesIds) {
            Image img = imageRepository.findByIdAndUserId(idImagine, userId).get();
            if (img == null) { throw new ResourceNotFoundException("Imaginea cu id " + idImagine + " nu a fost gasita");}
            imageRepository.deleteById(idImagine);
            newList.add(img);
        }
        return ResponseEntity.ok().build();
    }


    public void sendToMessage(ImageMess imageMess, Integer method) {
        producer.produce(imageMess);
    }
}
