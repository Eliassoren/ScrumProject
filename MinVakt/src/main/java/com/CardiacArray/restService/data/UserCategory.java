package com.CardiacArray.restService.data;

/**
 * Data class for the different employee categories, including "Helsefagarbeider", "Sykepleier", "Assistent"
 */
public class UserCategory {
    private int userCategoryId;
    private String userCategoryType;

    public UserCategory(int userCategoryId, String userCategoryType) {
        this.userCategoryId = userCategoryId;
        this.userCategoryType = userCategoryType;
    }

    /**
     *
     * @return the id of the user category
     */
    public int getUserCategoryId() {
        return userCategoryId;
    }

    /**
     *
     * @param userCategoryId
     */
    public void setUserCategoryId(int userCategoryId) {
        this.userCategoryId = userCategoryId;
    }

    /**
     *
     * @return
     */
    public String getUserCategoryType() {
        return userCategoryType;
    }

    /**
     *
     * @param userCategoryType
     */
    public void setUserCategoryType(String userCategoryType) {
        this.userCategoryType = userCategoryType;
    }
}
