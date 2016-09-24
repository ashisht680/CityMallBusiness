package com.javinindia.citymalls.apiparsing;

public class CountryModel {

    String name;
    String isocode;


    public CountryModel(String name, String isocode){
        this.name=name;
        this.isocode=isocode;
    }

    public String getName() {
        return name;
    }

    public String getisoCode() {
        return isocode;
    }
}
