package ro.tuiasi.apigateway.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    @Size(min = 3, message = "First name must have at least 3 characters")
    @NotEmpty(message = "*Please provide your name")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, message = "Last name must have at least 3 characters")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "password")
    @NotEmpty(message = "*Please provide a password")
    @JsonIgnore
    private String password;

    @Column(name = "username")
    @UniqueElements
    @Email(message = "Email should be valid")
    @NotEmpty(message = "*Please provide your email address")
    private String username;

    @Column(name = "profile_pic")
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private byte[] profilePic;


    public User() {
    }

    public User(Integer id, String firstName, String lastName, String password, String username, byte[] profilePic) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.profilePic = profilePic;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", profilePic=" + Arrays.toString(profilePic) +
                '}';
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
}

