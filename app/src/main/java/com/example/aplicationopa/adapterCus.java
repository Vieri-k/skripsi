package com.example.aplicationopa;

public class adapterCus {

    String Name, Phone, Lokasi, Jarak, Total, Latitude, Longitude;

    public adapterCus(){

    }
    public adapterCus(String Name, String Phone, String Lokasi, String Jarak, String Total){
        this.Jarak = Jarak;
        this.Lokasi = Lokasi;
        this.Name = Name;
        this.Phone = Phone;
        this.Total = Total;
//        this.Latitude = Latitide;
//        this.Longitude = Longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getLokasi() {
        return Lokasi;
    }
    public void setLokasi(String Lokasi) {
        this.Lokasi = Lokasi;
    }

    public String getJarak() {
        return Jarak;
    }
    public void setJarak(String Jarak) {
        this.Jarak = Jarak;
    }

    public String getTotal() {
        return Total;
    }
    public void setTotal(String Total) {
        this.Total = Total;
    }

//    public String getLatitude() {
//        return Latitude;
//    }
//    public void setLatitude(String Total) {
//        this.Total = Latitude;
//    }
//
//    public String getLongitude() {
//        return Longitude;
//    }
//    public void setLongitude(String Total) {
//        this.Longitude = Longitude;
//    }
}
