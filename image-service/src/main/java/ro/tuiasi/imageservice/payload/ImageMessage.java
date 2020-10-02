package ro.tuiasi.imageservice.payload;

public class ImageMessage {
    private long id;
    private String image;
    private String name;
    private String type;

    public ImageMessage(long id, String image, String name, String type) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.type = type;
    }
}
