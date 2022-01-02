package com.mobile.foodapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "data_app";

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIdUser(String idUser) {
        editor.putString("idUser", idUser);
        editor.apply();
    }

    public String getIdUser() {
        return pref.getString("idUser", "");
    }

    public void setHarga(String Harga) {
        editor.putString("InterfaceHarga", Harga);
        editor.apply();
    }

    public String getHarga() {
        return pref.getString("InterfaceHarga", "");
    }

    public void setTotal(String Total) {
        editor.putString("InterfaceHarga", Total);
        editor.apply();
    }

    public String getTotal() {
        return pref.getString("InterfaceHarga", "");
    }

    public void setAlamat(String Alamat) {
        editor.putString("Alamat", Alamat);
        editor.apply();
    }

    public String getAlamat() {
        return pref.getString("Alamat", "");
    }

    public void setKodePos(String KodePos) {
        editor.putString("KodePos", KodePos);
        editor.apply();
    }

    public String getKodePos() {
        return pref.getString("KodePos", "");
    }


    public void setIdKeranjang(String idKeranjang) { ///untuk set nama customer
        editor.putString("idKeranjang", idKeranjang);
        editor.apply();
    }

    public String getidKeranjang() { ///untuk ambil nama customer
        return pref.getString("idKeranjang", "");
    }

    public void setBanyak(String Banyak) {
        editor.putString("banyakItem", Banyak);
        editor.apply();
    }

    public String getBanyak() {
        return pref.getString("banyakItem", "");
    }

    public void setLoginStatus(boolean islogin) {
        editor.putBoolean("login", islogin);
        editor.apply();
    }

    public boolean getLoginStatus() {
        return pref.getBoolean("login", false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
