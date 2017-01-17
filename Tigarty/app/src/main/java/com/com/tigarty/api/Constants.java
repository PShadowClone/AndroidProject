package com.com.tigarty.api;

import android.os.Environment;

import com.com.tigarty.api.Entities.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Amr Saidam on 11/13/2016.
 */

public class Constants {


    //10.2.194.71
    //192.168.1.108
    //10.2.194.71
    //10.2.194.210
    //10.2.194.210
    public final static String PORT = ":8000";
    public final static String IP = "10.2.193.205"+PORT;
    public final static String LOGIN_URL = "http://"+IP+"/API/userAuthentication";
    public final static String GET_PRODUCT_NAME_URL = "http://"+IP+"/API/getProductName/";
    public final static String USER_FILE = "userFile";
    public final static String TOKEN_FILE = "tokenFile";
    public final static String TIGARTY_DIR = "tigarty";
    public final static String PRODUCT_DIR = TIGARTY_DIR+"//product";
    public final static String ANALYTIC_DIR = TIGARTY_DIR+"//analytic";
    public final static String ANALYTIC_FILE = "analytic";
    public final static String PRODUCT_LIST = "product";
    public final static String PRODUCT_NAME_API = "https://www.datakick.org/api/items/";
    public final static String BROADCAST_TO_TRADER_INVOICE = "http://"+IP+"/API/traderInvoiceAPI/";
    public final static String BROADCAST_TO_SALE_POINT = "http://"+IP+"/API/salePointAPI/";
    public final static String LIST_OF_PRODUCTS = "http://"+IP+"/API/getAllProducts/";
    public final static String ANALYTIC_INFO = "http://"+IP+"/API/charts/products/";

    public static int getUserId() throws IOException, ClassNotFoundException {
        File tigartyDir = new File(Environment.getExternalStorageDirectory(), "."+Constants.TIGARTY_DIR);
        if(!tigartyDir.exists())
            return -1;

        File userFile = new File(tigartyDir, Constants.USER_FILE+".txt");

        if(!userFile.exists())
            return -1;

        FileInputStream fileInputStream = new  FileInputStream(userFile);
        ObjectInputStream objectInputStream = new  ObjectInputStream(fileInputStream);
        User user  = (User) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
        return Integer.parseInt(user.getId());
    }





}
