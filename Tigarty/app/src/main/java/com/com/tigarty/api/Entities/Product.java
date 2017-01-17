package com.com.tigarty.api.Entities;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Amr Saidam on 12/1/2016.
 */

public class Product implements Serializable{

    private int id;
    private String Name;
    private float wholeQuantity;
    private int numberOfUnitInCartoon;
    private int wholePriceTrader;
    private int wholePriceSale;
    private int singleUnitPrice;
    private int singleUnitAmount;
    private int total;
    private int numberOfSale;
    private String barcode;
    private String shop_ID;
    private String userID;
    private String trader_ID;
    private String created_at;
    private String updated_at;

    public Product(){}

    public Product(int id, String name, float wholeQuantity, int numberOfUnitInCartoon, int wholePriceTrader, int wholePriceSale, int singleUnitPrice, int singleUnitAmount, int total, int numberOfSale, String barcode, String shop_ID, String userID, String trader_ID, String created_at, String updated_at) {
        this.id = id;
        Name = name;
        this.wholeQuantity = wholeQuantity;
        this.numberOfUnitInCartoon = numberOfUnitInCartoon;
        this.wholePriceTrader = wholePriceTrader;
        this.wholePriceSale = wholePriceSale;
        this.singleUnitPrice = singleUnitPrice;
        this.singleUnitAmount = singleUnitAmount;
        this.total = total;
        this.numberOfSale = numberOfSale;
        this.barcode = barcode;
        this.shop_ID = shop_ID;
        this.userID = userID;
        this.trader_ID = trader_ID;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getWholeQuantity() {
        return wholeQuantity;
    }

    public void setWholeQuantity(float wholeQuantity) {

        this.wholeQuantity = Float.parseFloat(new DecimalFormat("##.##").format(""+wholeQuantity));
    }

    public int getNumberOfUnitInCartoon() {
        return numberOfUnitInCartoon;
    }

    public void setNumberOfUnitInCartoon(int numberOfUnitInCartoon) {
        this.numberOfUnitInCartoon = numberOfUnitInCartoon;
    }

    public int getWholePriceTrader() {
        return wholePriceTrader;
    }

    public void setWholePriceTrader(int wholePriceTrader) {
        this.wholePriceTrader = wholePriceTrader;
    }

    public int getWholePriceSale() {
        return wholePriceSale;
    }

    public void setWholePriceSale(int wholePriceSale) {
        this.wholePriceSale = wholePriceSale;
    }

    public int getSingleUnitPrice() {
        return singleUnitPrice;
    }

    public void setSingleUnitPrice(int singleUnitPrice) {
        this.singleUnitPrice = singleUnitPrice;
    }

    public int getSingleUnitAmount() {
        return singleUnitAmount;
    }

    public void setSingleUnitAmount(int singleUnitAmount) {
        this.singleUnitAmount = singleUnitAmount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumberOfSale() {
        return numberOfSale;
    }

    public void setNumberOfSale(int numberOfSale) {
        this.numberOfSale = numberOfSale;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getShop_ID() {
        return shop_ID;
    }

    public void setShop_ID(String shop_ID) {
        this.shop_ID = shop_ID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTrader_ID() {
        return trader_ID;
    }

    public void setTrader_ID(String trader_ID) {
        this.trader_ID = trader_ID;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
