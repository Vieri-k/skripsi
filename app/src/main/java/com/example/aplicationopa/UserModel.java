package com.example.aplicationopa;

public class UserModel {

    private String name, Email, Phone;

    public UserModel(){

    }

    public UserModel(String name, String Email, String Phone){
        this.name = name;
        this.Email = Email;
        this.Phone = Phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

}
