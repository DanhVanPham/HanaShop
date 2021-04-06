/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class Food implements Serializable {

    private String foodId;
    private String categoryId;
    public String foodName;
    public float foodPrice;
    public int foodQuantity;
    public String foodDescription;
    private String imageUrl;
    private Date createDate;
    private boolean status;

    public Food() {
    }

    public Food(String foodId, String categoryId, String foodName, float foodPrice, String foodDescription) {
        this.foodId = foodId;
        this.categoryId = categoryId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodDescription = foodDescription;
    }

    public Food(String foodId, String categoryId, String foodName, float foodPrice, int foodQuantity, String foodDescription, String imageUrl, Date createDate, boolean status) {
        this.foodId = foodId;
        this.categoryId = categoryId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
        this.foodDescription = foodDescription;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
        this.status = status;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(float foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Food{" + "foodId=" + foodId + ", categoryId=" + categoryId + ", foodName=" + foodName + ", foodPrice=" + foodPrice + ", foodQuantity=" + foodQuantity + ", foodDescription=" + foodDescription + ", imageUrl=" + imageUrl + ", createDate=" + createDate + ", status=" + status + '}';
    }

}
