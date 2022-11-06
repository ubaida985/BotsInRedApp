package com.example.botsinred.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class UserModel {
    public String deviceID = "", id="", userID="",  image = "", username = "", name = "", address = "", contact = "", emergencyContact = "", email = "", bloodGroup = "", weight = "";

    public UserModel() {
    }


    public UserModel(String deviceID, String id, String userID, String image, String username, String name, String address, String contact, String emergencyContact, String email, String bloodGroup, String weight) {
        this.deviceID = deviceID;
        this.id = id;
        this.userID = userID;
        this.image = image;
        this.username = username;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.emergencyContact = emergencyContact;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.weight = weight;
    }
    public UserModel(String image, String username, String name, String address, String contact, String emergencyContact, String email, String bloodGroup, String weight) {
        this.image = image;
        this.username = username;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.emergencyContact = emergencyContact;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.weight = weight;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "deviceID='" + deviceID + '\'' +
                ", id='" + id + '\'' +
                ", userID='" + userID + '\'' +
                ", image='" + image + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", email='" + email + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
