package com.example.athome.account;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemAccountCardData {
    public String cardCompany;
    public int cardNumberValue1;
    public int cardNumberValue2;
    public int cardNumberValue3;
    public int cardNumberValue4;
    public int validity_mm;
    public int validity_yy;

    public ItemAccountCardData(){}
    public ItemAccountCardData(String cardCompany, int cardNumberValue1, int  cardNumberValue2, int cardNumberValue3, int cardNumberValue4, int validity_mm, int validity_yy) {
        this.cardCompany = cardCompany;
        this.cardNumberValue1 = cardNumberValue1;
        this.cardNumberValue2 = cardNumberValue2;
        this.cardNumberValue3 = cardNumberValue3;
        this.cardNumberValue4 = cardNumberValue4;
        this.validity_mm = validity_mm;
        this.validity_yy = validity_yy;
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

    public int getCardNumberValue3() {
        return cardNumberValue3;
    }

    public void setCardNumberValue3(int cardNumberValue3) {
        this.cardNumberValue3 = cardNumberValue3;
    }

    public int getCardNumberValue4() {
        return cardNumberValue4;
    }

    public void setCardNumberValue4(int cardNumberValue4) {
        this.cardNumberValue4 = cardNumberValue4;
    }

    public int getValidity_mm() {
        return validity_mm;
    }

    public void setValidity_mm(int validity_mm) {
        this.validity_mm = validity_mm;
    }

    public int getValidity_yy() {
        return validity_yy;
    }

    public void setValidity_yy(int validity_yy) {
        this.validity_yy = validity_yy;
    }


}