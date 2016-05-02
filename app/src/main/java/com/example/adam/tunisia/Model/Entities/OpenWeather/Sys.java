
package com.example.adam.tunisia.Model.Entities.OpenWeather;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sys {

    @SerializedName("type")
    @Expose
    private Double type;
    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Double sunrise;
    @SerializedName("sunset")
    @Expose
    private Double sunset;

    /**
     * 
     * @return
     *     The type
     */
    public Double getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(Double type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Double getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Double id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The sunrise
     */
    public Double getSunrise() {
        return sunrise;
    }

    /**
     * 
     * @param sunrise
     *     The sunrise
     */
    public void setSunrise(Double sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * 
     * @return
     *     The sunset
     */
    public Double getSunset() {
        return sunset;
    }

    /**
     * 
     * @param sunset
     *     The sunset
     */
    public void setSunset(Double sunset) {
        this.sunset = sunset;
    }

}
