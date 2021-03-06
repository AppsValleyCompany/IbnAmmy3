package com.av.ibnammy.viewprofile;

import java.io.Serializable;

/**
 * Created by Maiada on 4/2/2018.
 */

public class Profile implements Serializable {

    private String  userName  = "" ;
    private String  mobile    = "" ;
    private String  accountId = "";
    private String  subscriptionStatus = "";
    private String  active = "";
    private String  followersNumber = "";
    private String  profileImage = "";
    private String  fullUserName = "";
    private String  homeLongitude = "";
    private String  homeLatitude= "";


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getFollowersNumber() {
        return followersNumber;
    }

    public void setFollowersNumber(String followersNumber) {
        this.followersNumber = followersNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFullUserName() {
        return fullUserName;
    }

    public void setFullUserName(String fullUserName) {
        this.fullUserName = fullUserName;
    }


    public String getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(String homeLongitude) {
        this.homeLongitude = homeLongitude;
    }

    public String getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(String homeLatitude) {
        this.homeLatitude = homeLatitude;
    }
}
