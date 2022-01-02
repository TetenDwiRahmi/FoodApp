package com.mobile.foodapp.Model;

public class DetailOrder {
    private String id_order;
    private String status_order;
    private int total_bayar;
    private int total_item;
    private String nama_item;
    private int banyak_item;
    private int harga_item;
    private String foto_item;

    public DetailOrder(String id_order, String status_order, int total_bayar, int total_item, String nama_item, int banyak_item, int harga_item, String foto_item) {
        this.id_order = id_order;
        this.status_order = status_order;
        this.total_bayar = total_bayar;
        this.total_item = total_item;
        this.nama_item = nama_item;
        this.banyak_item = banyak_item;
        this.harga_item = harga_item;
        this.foto_item = foto_item;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getStatus_order() {
        return status_order;
    }

    public void setStatus_order(String status_order) {
        this.status_order = status_order;
    }

    public int getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(int total_bayar) {
        this.total_bayar = total_bayar;
    }

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
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

    public String getFoto_item() {
        return foto_item;
    }

    public void setFoto_item(String foto_item) {
        this.foto_item = foto_item;
    }
}