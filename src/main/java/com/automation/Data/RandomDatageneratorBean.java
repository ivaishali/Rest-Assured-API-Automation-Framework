package com.automation.Data;

import com.github.javafaker.Faker;

public class RandomDatageneratorBean {
    String name;
    String url;
    String address;
    String latitude;
    String longitude;
    String logoUrl;
    String locationName;
    String searchableLocationName;
    String description;
    String imageUrl;
    String email;
    String country;
    String currency;
    String currencyCode;
    String randomBool;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getSearchableLocationName() {
        return searchableLocationName;
    }

    public void setSearchableLocationName(String searchableLocationName) {
        this.searchableLocationName = searchableLocationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRandomBool() {
        return randomBool;
    }

    public void setRandomBool(String randomBool) {
        this.randomBool = randomBool;
    }

    public void generateRandomData() {
        Faker faker = new Faker();
        name = faker.name().name();
        url = faker.internet().url();
        address = faker.address().fullAddress();
        latitude = faker.address().latitude();
        longitude = faker.address().longitude();
        logoUrl = faker.avatar().image();
        locationName = faker.address().fullAddress();
        searchableLocationName = faker.address().streetName();
        description = faker.ancient().god();
        imageUrl = faker.avatar().image();
        email = faker.internet().emailAddress();
        country = faker.address().country();
        currency = faker.currency().name();
        currencyCode = faker.currency().code();
        randomBool = faker.bool().toString();
    }
}
