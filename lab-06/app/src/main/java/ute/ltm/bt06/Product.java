package ute.ltm.bt06;

public class Product {
    private String name;
    private int imageId;

    public Product(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() { return name; }
    public int getImageId() { return imageId; }
}