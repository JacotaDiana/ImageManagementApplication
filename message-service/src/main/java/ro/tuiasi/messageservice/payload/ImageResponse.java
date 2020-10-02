package ro.tuiasi.messageservice.payload;

public class ImageResponse {
    private long id;
    private byte[] image;

    public long getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public ImageResponse(long id, byte[] image) {
        this.id = id;
        this.image = image;
    }
}
