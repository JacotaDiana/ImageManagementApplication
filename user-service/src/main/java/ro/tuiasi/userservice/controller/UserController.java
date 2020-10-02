package ro.tuiasi.userservice.controller;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import ro.tuiasi.userservice.payload.request.RegisterRequest;
import ro.tuiasi.userservice.payload.request.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuiasi.userservice.service.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger;

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerUser(@RequestParam("username") String username, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("password") String password, @RequestParam("file") MultipartFile images) throws IOException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {
        RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, username, password, images.getBytes());
        return userService.registerUser(registerRequest);
    }

    @PostMapping(path = "/updateUser", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateUser(@RequestParam("id") int id, @RequestParam("username") String username, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("password") String password, @RequestParam("file") MultipartFile images) throws IOException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {
        UpdateRequest updateRequest = new UpdateRequest(id, firstName, lastName, username, password, images.getBytes());
        return userService.updateUser(updateRequest);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}