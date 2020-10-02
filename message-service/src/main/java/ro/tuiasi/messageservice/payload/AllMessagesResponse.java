package ro.tuiasi.messageservice.payload;

public class AllMessagesResponse {
    private long id;

    private String sender;

    private String title;

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getTitle() {
        return title;
    }

    public AllMessagesResponse(long id, String sender, String title) {
        this.id = id;
        this.sender = sender;
        this.title = title;
    }
}
