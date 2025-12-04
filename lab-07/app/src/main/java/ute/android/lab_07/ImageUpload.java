package ute.android.lab_07;

public class ImageUpload {
    private int id;
    private String username;
    private String avatar;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    // --- SETTER METHODS (Dùng cho Gson) ---

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
