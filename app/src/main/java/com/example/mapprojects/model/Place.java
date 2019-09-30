package com.example.mapprojects.model;

/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/
public class Place {
    private int id;
    private String placeName;
    private Long phoneNumber;
    private String details;
    private byte[] pic;
    private Double latitude;
    private Double longitud;
    private String guilds;

    public Place() {
    }

    public Place(int id, String placeName, Long phoneNumber, String details, byte[] pic, Double latitude, Double longitud, String guilds) {
        this.id = id;
        this.placeName = placeName;
        this.phoneNumber = phoneNumber;
        this.details = details;
        this.pic = pic;
        this.latitude = latitude;
        this.longitud = longitud;
        this.guilds = guilds;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getGuilds() {
        return guilds;
    }

    public void setGuilds(String guilds) {
        this.guilds = guilds;
    }
}
