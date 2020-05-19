package com.example.athome.point_charge;

public class ItemPaymentCardData {
    public String cardCompany;
    public int cardNumberValue1;
    public int cardNumberValue2;

    public ItemPaymentCardData(String cardCompany, int cardNumberValue1, int  cardNumberValue2) {
        this.cardCompany = cardCompany;
        this.cardNumberValue1 = cardNumberValue1;
        this.cardNumberValue2 = cardNumberValue2;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public int getCardNumberValue1() {
        return cardNumberValue1;
    }

    public void setCardNumberValue1(int cardNumberValue1) {
        this.cardNumberValue1 = cardNumberValue1;
    }

    public int getCardNumberValue2() {
        return cardNumberValue2;
    }

    public void setCardNumberValue2(int cardNumberValue2) {
        this.cardNumberValue2 = cardNumberValue2;
    }
}
