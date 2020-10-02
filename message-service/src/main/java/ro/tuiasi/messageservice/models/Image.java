package ro.tuiasi.messageservice.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "image")
public class Image {
    @Id
    private long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    @Column(name="imagine_bytes", length = 1000)
    private byte[] image;

    @ManyToMany(mappedBy = "images")
    private Set<Message> messages = new HashSet<>();

    public Image() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message){
        this.messages.add(message);
        message.getImages().add(this);
    }

    public void removeMessage(Message message){
        this.messages.remove(message);
        message.getImages().remove(this);
    }
}
