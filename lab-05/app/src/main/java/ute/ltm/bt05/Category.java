package ute.ltm.bt05;
import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public class Category {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("images")
    private String images;

    @SerializedName("description")
    private String description;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this. name = name;}

    public String getImage() {return images;}
    public void setImage(String image) {this.images = images;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
