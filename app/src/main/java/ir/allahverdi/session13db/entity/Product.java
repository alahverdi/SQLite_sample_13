package ir.allahverdi.session13db.entity;

import android.graphics.drawable.Drawable;

public class Product {
    private int id;
    private String name;
    private int price;
    private int imageId;

    public Product(String name, int price, int imageId) {
        this.name = name;
        this.price = price;
        this.imageId = imageId;
    }

    public Product(int id, String name, int price, int imageId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
