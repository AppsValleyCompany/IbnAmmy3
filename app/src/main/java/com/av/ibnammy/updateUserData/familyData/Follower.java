package com.av.ibnammy.updateUserData.familyData;

/**
 * Created by Mina on 3/28/2018.
 */

public class Follower {
   String Account_FollowerID,Follower_Name,Description,Gender;

   public Follower(String follower_Name, String description) {
      Follower_Name = follower_Name;
      Description = description;
   }

   public String getAccount_FollowerID() {
      return Account_FollowerID;
   }

   public String getFollower_Name() {
      return Follower_Name;
   }

   public String getDescription() {
      return Description;
   }

   public String getGender() {
      return Gender;
   }
}
