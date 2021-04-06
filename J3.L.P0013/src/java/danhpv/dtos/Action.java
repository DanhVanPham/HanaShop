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
public class Action implements Serializable{
    private String actionId;
    private String foodId;
    private String updateUser;
    private String typeAction;
    private Date   updateDate;
    private boolean status;

    public Action() {
    }

    public Action(String actionId, String foodId, String updateUser, String typeAction, Date updateDate, boolean status) {
        this.actionId = actionId;
        this.foodId = foodId;
        this.updateUser = updateUser;
        this.typeAction = typeAction;
        this.updateDate = updateDate;
        this.status = status;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
