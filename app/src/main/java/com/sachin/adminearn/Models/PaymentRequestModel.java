package com.sachin.adminearn.Models;

public class PaymentRequestModel {

    private String paymentMethod,paymentDetails,status,date;
    private int coins;
    private String uId;
    private String userId;
    private String requestId;


    public PaymentRequestModel(String paymentMethod, String paymentDetails, String status, String date, int coins, String uId, String userId, String requestId) {
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
        this.status = status;
        this.date = date;
        this.coins = coins;
        this.uId = uId;
        this.userId = userId;
        this.requestId = requestId;
    }

    public PaymentRequestModel() {
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
