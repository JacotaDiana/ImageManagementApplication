package ro.tuiasi.messageservice.messaging;

public class ImageCustomized {
    private long id;
    private byte[] imageBytes;

    ImageCustomized(long id, byte[] imageBytes) {
        this.id = id;
        this.imageBytes = imageBytes;
    }

    public long getId() {
        return id;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
