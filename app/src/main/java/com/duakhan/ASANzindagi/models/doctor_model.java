package com.duakhan.ASANzindagi.models;
public class doctor_model {
    String Username ,time,fee,Usertoken,status;
    public doctor_model(){}
    public doctor_model(String username, String time, String fee,String Usertoken,String Status) {
        this.Username = username;
        this.time = time;
        this.fee = fee;
        this.Usertoken=Usertoken;
        this.status=Status;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getToken() {
        return Usertoken;
    }
    public void setToken(String Usertoken) {
        this.Usertoken = Usertoken;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getFee() {
        return fee;
    }
    public void setFee(String fee) {
        this.fee = fee;
    }
}
