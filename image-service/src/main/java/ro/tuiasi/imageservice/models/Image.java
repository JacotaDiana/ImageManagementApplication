package ro.tuiasi.imageservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Id;
import ro.tuiasi.imageservice.service.Compression;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "image")
public class Image {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @NotNull
    @Column(name="data_adaugarii")
    private Date dataAdaugarii;

    @Column(name = "name")
    private String name;


    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    @Column(name="imagine_bytes", length = 1000)
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY) //todo inainte era optional = false. De vazut daca am nevoie
    @JoinColumn(name = "user_id") //aici era nullable = false
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;


    public Image() {
    }

    public Image(String name, String type, Long size, Date dataAdaugarii, byte[] imageBytes, User user) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.dataAdaugarii = dataAdaugarii;
        this.image = imageBytes;
        this.user = user;
    }

    public Image(long id, String name, String type, Long size, Date dataAdaugarii, byte[] imageBytes, User user) {
        this.id =  id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.dataAdaugarii = dataAdaugarii;
        this.image = imageBytes;
        this.user = user;
    }

    public Image(Image img) {
        this.id =  img.getId();
        this.name = img.getName();
        this.type = img.getType();
        this.size = img.getSize();
        this.dataAdaugarii = img.getDataAdaugarii();
        this.image = Compression.decompressBytes(img.getImage());
        this.user = img.getUser();
    }


    public Date getDataAdaugarii() {
        return dataAdaugarii;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }
}
