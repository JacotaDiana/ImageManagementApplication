package ro.tuiasi.userservice.messaging;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserMess {
    private int userId;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserMess(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserMess() {
    }
}
