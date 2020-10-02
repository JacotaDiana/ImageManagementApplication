package ro.tuiasi.messageservice.models;

public class MessageResponse {
    private long messageId;
    private String title;
    private String sender;

    public MessageResponse(long messageId, String sender, String title) {
        this.messageId = messageId;
        this.title = title;
        this.sender = sender;
    }
}
