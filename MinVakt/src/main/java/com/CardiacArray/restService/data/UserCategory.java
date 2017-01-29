package com.CardiacArray.restService.data;

public class UserCategory {
    private int userCategoryId;
    private String userCategoryType;

    public UserCategory(int userCategoryId, String userCategoryType) {
        this.userCategoryId = userCategoryId;
        this.userCategoryType = userCategoryType;
    }

    public int getUserCategoryId() {
        return userCategoryId;
    }

    public void setUserCategoryId(int userCategoryId) {
        this.userCategoryId = userCategoryId;
    }

    public String getUserCategoryType() {
        return userCategoryType;
    }

    public void setUserCategoryType(String userCategoryType) {
        this.userCategoryType = userCategoryType;
    }
}
