package ro.tuiasi.messageservice.messaging;

import java.time.LocalDateTime;

public class NotificationMessage {
    private long messageId;
    private int senderId;
    private int receiverId;
    private LocalDateTime sendingDate;

    public NotificationMessage(long messageId, int senderId, int receiverId, LocalDateTime sendingDate) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.sendingDate = sendingDate;
        this.receiverId = receiverId;
    }

    public NotificationMessage() {
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(LocalDateTime sendingDate) {
        this.sendingDate = sendingDate;
    }
}
