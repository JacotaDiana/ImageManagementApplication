package ro.tuiasi.imageservice.messaging;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Img.class)
public class Img {
    private long imageId;
    private byte[] imageBytes;

    public Img(){}

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Img(long imageId, byte[] imageBytes) {
        this.imageId = imageId;
        this.imageBytes = imageBytes;
    }
}