/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.dtos;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class FoodError implements Serializable{

    private String categoryError;
    public String foodNameError;
    public String foodPriceError;
    public String foodQuantityError;
    public String foodDescriptionError;
    private String imageUrlError;

    public FoodError() {
    }

    public FoodError(String categoryError, String foodNameError, String foodPriceError, String foodQuantityError, String foodDescriptionError, String imageUrlError) {
        this.categoryError = categoryError;
        this.foodNameError = foodNameError;
        this.foodPriceError = foodPriceError;
        this.foodQuantityError = foodQuantityError;
        this.foodDescriptionError = foodDescriptionError;
        this.imageUrlError = imageUrlError;
    }

    public String getCategoryError() {
        return categoryError;
    }

    public void setCategoryError(String categoryError) {
        this.categoryError = categoryError;
    }

    public String getFoodNameError() {
        return foodNameError;
    }

    public void setFoodNameError(String foodNameError) {
        this.foodNameError = foodNameError;
    }

    public String getFoodPriceError() {
        return foodPriceError;
    }

    public void setFoodPriceError(String foodPriceError) {
        this.foodPriceError = foodPriceError;
    }

    public String getFoodQuantityError() {
        return foodQuantityError;
    }

    public void setFoodQuantityError(String foodQuantityError) {
        this.foodQuantityError = foodQuantityError;
    }

    public String getFoodDescriptionError() {
        return foodDescriptionError;
    }

    public void setFoodDescriptionError(String foodDescriptionError) {
        this.foodDescriptionError = foodDescriptionError;
    }

    
    public String getImageUrlError() {
        return imageUrlError;
    }

    public void setImageUrlError(String imageUrlError) {
        this.imageUrlError = imageUrlError;
    }

}
