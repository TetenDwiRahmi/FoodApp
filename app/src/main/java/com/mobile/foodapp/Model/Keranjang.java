package com.mobile.foodapp;

public class Keranjang {
    private int id_keranjang;
    private int id_customer;
    private int id_item;
    private String nama_item;
    private int banyak_item;
    private int harga_item;
    private int total;
    private String foto_item;

    public Keranjang(int id_keranjang, int id_customer, int id_item, String nama_item, int banyak_item, int harga_item, int total, String foto_item) {
        this.id_keranjang = id_keranjang;
        this.id_customer = id_customer;
        this.id_item = id_item;
        this.nama_item = nama_item;
        this.banyak_item = banyak_item;
        this.harga_item = harga_item;
        this.total = total;
        this.foto_item = foto_item;
    }

    public int getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(int id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getNama_item() {
        return nama_item;
    }

    public void setNama_item(String nama_item) {
        this.nama_item = nama_item;
    }

    public int getBanyak_item() {
        return banyak_item;
    }

    public void setBanyak_item(int banyak_item) {
        this.banyak_item = banyak_item;
    }

    public int getHarga_item() {
        return harga_item;
    }

    public void setHarga_item(int harga_item) {
        this.harga_item = harga_item;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getFoto_item() {
        return foto_item;
    }

    public void setFoto_item(String foto_item) {
        this.foto_item = foto_item;
    }
}
