/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.dtos;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author DELL
 */
public class CartShopping implements Serializable {

    private String customerName;
    private HashMap<String, Food> cart;

    public CartShopping() {
        this.customerName = "Guest";
        this.cart = new HashMap<>();
    }

    public CartShopping(String customerName) {
        this.customerName = customerName;
        this.cart = new HashMap<>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public HashMap<String, Food> getCart() {
        return cart;
    }

    public boolean addCart(Food foodDto) throws Exception {
        if (this.cart.containsKey(foodDto.getFoodId())) {
            int quantity = this.cart.get(foodDto.getFoodId()).getFoodQuantity() + 1;
            foodDto.setFoodQuantity(quantity);
        }
        this.cart.put(foodDto.getFoodId(), foodDto);
        return true;
    }

    public void setCart(HashMap<String, Food> cart) {
        this.cart = cart;
    }

    public float getTotalOrder() throws Exception {
        float price = 0;
        for (Food food : this.cart.values()) {
            price += (food.getFoodPrice() * food.getFoodQuantity());
        }
        return price;
    }

    public boolean remove(String foodId) throws Exception {
        if (this.cart.containsKey(foodId)) {
            this.cart.remove(foodId);
            return true;
        }
        return false;
    }

    public void update(String foodId, int quantity) throws Exception {
        if (this.cart.containsKey(foodId)) {
            this.cart.get(foodId).setFoodQuantity(quantity);
        }
    }
}
