package ro.tuiasi.imageservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tuiasi.imageservice.models.User;
import ro.tuiasi.imageservice.repository.UserRepository;

@Component
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void addUser(int userId) {
        User user = new User();
        user.setId(userId);
        try{
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try{
            userRepository.deleteById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
