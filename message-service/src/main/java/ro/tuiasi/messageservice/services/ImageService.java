package ro.tuiasi.messageservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuiasi.messageservice.messaging.ImageMess;
import ro.tuiasi.messageservice.messaging.Img;
import ro.tuiasi.messageservice.models.Image;
import ro.tuiasi.messageservice.repository.ImageRepository;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public void addImages(ImageMess imageMess){
        List<Img> imagesToAdd = imageMess.getImagesList();

        for(int i = 0; i < imagesToAdd.size(); i++){
            Image image = new Image();
            image.setImage(imagesToAdd.get(i).getImageBytes());
            image.setId(imagesToAdd.get(i).getImageId());
            imageRepository.save(image);
        }
    }
}
