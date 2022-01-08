package com.duakhan.ASANzindagi.models;

public class registration_model {
    String  profilepic, UserName, email, password, profession, specialization, days, time, address, fee, contact,serid;
    public registration_model(){}

    public registration_model(String profilepic, String userName, String email, String password, String profession, String specialization, String days, String time, String address, String fee, String contact, String serid) {
        this.profilepic = profilepic;
        UserName = userName;
        this.email = email;
        this.password = password;
        this.profession = profession;
        this.specialization = specialization;
        this.days = days;
        this.time = time;
        this.address = address;
        this.fee = fee;
        this.contact = contact;
        this.serid = serid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSerid() {
        return serid;
    }

    public void setSerid(String serid) {
        this.serid = serid;
    }
}
