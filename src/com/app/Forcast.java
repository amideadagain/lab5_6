package com.app;

import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "data",
        "city_name",
        "lon",
        "timezone",
        "lat",
        "country_code",
        "state_code"
})
public class Forcast {

    @JsonProperty("data")
    private List<Datum> data = null;
    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("state_code")
    private String stateCode;

    @JsonProperty("data")
    public List<Datum> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Datum> data) {
        this.data = data;
    }

    @JsonProperty("city_name")
    public String getCityName() {
        return cityName;
    }

    @JsonProperty("city_name")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonProperty("lon")
    public double getLon() {
        return lon;
    }

    @JsonProperty("lon")
    public void setLon(double lon) {
        this.lon = lon;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("lat")
    public double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(double lat) {
        this.lat = lat;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("state_code")
    public String getStateCode() {
        return stateCode;
    }

    @JsonProperty("state_code")
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("data", data).append("cityName", cityName).append("lon", lon).append("timezone", timezone).append("lat", lat).append("countryCode", countryCode).append("stateCode", stateCode).toString();
    }

}