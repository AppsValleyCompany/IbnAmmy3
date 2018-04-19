package com.av.ibnammy.updateUserData;

/**
 * Created by Mina on 3/21/2018.
 */

public class User {

    private String Mobile,Password,Email,First_Name,Second_Name,Third_Name,Forth_Name,Gender,Marital_Status,BirthDate,Blood_Type,
            Account_Type,Category_TypeID,Service_CategoryID,Service_SubcategoryID,Service_TypeID,Service_Name,
            Service_Description,Price,Home_Longitude,Home_Latitude,
            Home_Country,Home_District,Home_City,Home_Region,Home_Area,Home_Street,Profile_IMG,AccountID;

            private Integer Discount;
    public User(String mobile, String password, String email, String first_Name, String second_Name, String third_Name, String forth_Name, String gender, String marital_Status, String birthDate, String blood_Type) {
        Mobile = mobile;
        Password = password;
        Email = email;
        First_Name = first_Name;
        Second_Name = second_Name;
        Third_Name = third_Name;
        Forth_Name = forth_Name;
        Gender = gender;
        Marital_Status = marital_Status;
        BirthDate = birthDate;
        Blood_Type = blood_Type;
    }

    public User(String mobile, String password, String service_Description, String price, Integer discount) {
        Mobile = mobile;
        Password = password;
        Service_Description = service_Description;
        Price = price;
        Discount = discount;
    }

    public User(String mobile, String password, String category_TypeID, String service_CategoryID, String service_SubcategoryID, String service_TypeID, String service_Name, String home_Longitude, String home_Latitude, String home_Country, String home_District, String home_City, String home_Region, String home_Area, String home_Street) {
        Mobile = mobile;
        Password = password;
        Category_TypeID = category_TypeID;
        Service_CategoryID = service_CategoryID;
        Service_SubcategoryID = service_SubcategoryID;
        Service_TypeID = service_TypeID;
        Service_Name = service_Name;
        Home_Longitude = home_Longitude;
        Home_Latitude = home_Latitude;
        Home_Country = home_Country;
        Home_District = home_District;
        Home_City = home_City;
        Home_Region = home_Region;
        Home_Area = home_Area;
        Home_Street = home_Street;
    }



    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getSecond_Name() {
        return Second_Name;
    }

    public void setSecond_Name(String second_Name) {
        Second_Name = second_Name;
    }

    public String getThird_Name() {
        return Third_Name;
    }

    public void setThird_Name(String third_Name) {
        Third_Name = third_Name;
    }

    public String getForth_Name() {
        return Forth_Name;
    }

    public void setForth_Name(String forth_Name) {
        Forth_Name = forth_Name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMarital_Status() {
        return Marital_Status;
    }

    public void setMarital_Status(String marital_Status) {
        Marital_Status = marital_Status;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getBlood_Type() {
        return Blood_Type;
    }

    public void setBlood_Type(String blood_Type) {
        Blood_Type = blood_Type;
    }

    public String getAccount_Type() {
        return Account_Type;
    }

    public void setAccount_Type(String account_Type) {
        Account_Type = account_Type;
    }

    public String getCategory_TypeID() {
        return Category_TypeID;
    }

    public void setCategory_TypeID(String category_TypeID) {
        Category_TypeID = category_TypeID;
    }

    public String getService_CategoryID() {
        return Service_CategoryID;
    }

    public void setService_CategoryID(String service_CategoryID) {
        Service_CategoryID = service_CategoryID;
    }

    public String getHome_Longitude() {
        return Home_Longitude;
    }

    public void setHome_Longitude(String home_Longitude) {
        Home_Longitude = home_Longitude;
    }

    public String getHome_Latitude() {
        return Home_Latitude;
    }

    public void setHome_Latitude(String home_Latitude) {
        Home_Latitude = home_Latitude;
    }

    public String getService_SubcategoryID() {
        return Service_SubcategoryID;
    }

    public void setService_SubcategoryID(String service_SubcategoryID) {
        Service_SubcategoryID = service_SubcategoryID;
    }

    public String getService_TypeID() {
        return Service_TypeID;
    }

    public void setService_TypeID(String service_TypeID) {
        Service_TypeID = service_TypeID;
    }

    public String getService_Name() {
        return Service_Name;
    }

    public void setService_Name(String service_Name) {
        Service_Name = service_Name;
    }

    public String getService_Description() {
        return Service_Description;
    }

    public void setService_Description(String service_Description) {
        Service_Description = service_Description;
    }

    public String getPrice() {
        return Price;
    }

    public Integer getDiscount() {
        return Discount;
    }

    public String getProfile_IMG() {
        return Profile_IMG;
    }

    public String getHome_Country() {
        return Home_Country;
    }

    public String getHome_District() {
        return Home_District;
    }

    public String getHome_City() {
        return Home_City;
    }

    public String getHome_Region() {
        return Home_Region;
    }

    public String getHome_Area() {
        return Home_Area;
    }

    public String getHome_Street() {
        return Home_Street;
    }

    public String getAccountID() {
        return AccountID;
    }
}
