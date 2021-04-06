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
public class OrderDetail implements Serializable {

    private String orderItemId;
    private String foodItemId;
    private String foodName;
    private String imageUrl;
    private String orderId;
    private int foodQuantity;
    private float foodPrice;
    private Date createAt;
    private boolean status;

    public OrderDetail() {
    }

    public OrderDetail(String orderItemId, String foodItemId, String orderId, int foodQuantity, float foodPrice, Date createAt, boolean status) {
        this.orderItemId = orderItemId;
        this.foodItemId = foodItemId;
        this.orderId = orderId;
        this.foodQuantity = foodQuantity;
        this.foodPrice = foodPrice;
        this.createAt = createAt;
        this.status = status;
    }

    public OrderDetail(String orderItemId, String foodItemId, String foodName, String imageUrl, String orderId, int foodQuantity, float foodPrice, Date createAt) {
        this.orderItemId = orderItemId;
        this.foodItemId = foodItemId;
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.orderId = orderId;
        this.foodQuantity = foodQuantity;
        this.foodPrice = foodPrice;
        this.createAt = createAt;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(String foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(float foodPrice) {
        this.foodPrice = foodPrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
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

}
