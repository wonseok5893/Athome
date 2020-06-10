package com.example.athome.payment_list;

public class ItemPayListData {
    public String paymentDate;
    public String paymentAmount;
    public String paymentHistory;

    public ItemPayListData(String paymentDate,
                              String paymentAmount,
                              String paymentHistory
                             ) {
        this.paymentDate=paymentDate;
        this.paymentAmount=paymentAmount;
        this.paymentHistory=paymentHistory;

    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(String paymentHistory) {
        this.paymentHistory = paymentHistory;
    }
}
