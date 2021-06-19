package com.example.covidtracker.api;

public class CountryData {

    private String updated;
    private String cases;
    private String country;
    private String deaths;
    private String recovered;
    private String active;
    private String tests;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CountryData(String updated, String cases, String updated1, String deaths,
                       String recovered, String active, String tests, String country) {
        this.updated = updated;
        this.cases = cases;
        this.updated = updated1;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.tests = tests;
        this.country = country;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }
}
