package com.example.botsinred.models;

public class UserModel {
    static String image = "", username = "", name = "", address = "", contact = "", emergencyContact = "", email = "", bloodGroup = "", weight = "";

    public UserModel() {
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

    public static String getImage() {
        return image;
    }

    public static void setImage(String image) {
        UserModel.image = image;
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
}
