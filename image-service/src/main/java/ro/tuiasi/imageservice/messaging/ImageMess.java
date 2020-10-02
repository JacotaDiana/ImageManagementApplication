package ro.tuiasi.imageservice.messaging;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = ImageMess.class)
public class ImageMess {
    private int userId;
    private List<Img> imagesList;

    public ImageMess(){}

    public ImageMess(int userId, List<Img> imagesList) {
        this.userId = userId;
        this.imagesList = imagesList;
    }

    public List<Img> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Img> imagesList) {
        this.imagesList = imagesList;
    }

    public String toString() {
        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("userId", this.userId);

            JSONArray imagesArray = new JSONArray();
            if (this.imagesList != null) {
                this.imagesList.forEach(image -> {
                    JSONObject subJson = new JSONObject();
                    try {
                        subJson.put("imageId", image.getImageId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        subJson.put("imageBytes", image.getImageBytes());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    imagesArray.put(subJson);
                });
            }
            jsonInfo.put("images", imagesArray);
        } catch (JSONException e1) {}
        return jsonInfo.toString();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ImageMess(int userId) {
        this.userId = userId;
        imagesList = new ArrayList<Img>();
    }

    public void addImage(long imageId, byte[] imageBytes) {
        Img img = new Img(imageId, imageBytes);
        imagesList.add(img);
    }
}
