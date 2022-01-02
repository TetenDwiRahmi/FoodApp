package com.mobile.foodapp.Model;

public class Registrasi {
    private int id_customer;
    private String username;
    private String email;
    private String password;
    private String foto_customer;

    public Registrasi(int id_customer, String username, String email, String password, String foto_customer) {
        this.id_customer = id_customer;
        this.username = username;
        this.email = email;
        this.password = password;
        this.foto_customer = foto_customer;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFoto_customer() {
        return foto_customer;
    }

    public void setFoto_customer(String foto_customer) {
        this.foto_customer = foto_customer;
    }
}
