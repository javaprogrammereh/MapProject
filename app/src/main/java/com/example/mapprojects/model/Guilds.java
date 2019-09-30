package com.example.mapprojects.model;

/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/
public class Guilds {
    private int id;
    private String guildtype;

    public Guilds() {
    }

    public Guilds(String guildtype) {
        this.guildtype = guildtype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuildtype() {
        return guildtype;
    }

    public void setGuildtype(String guildtype) {
        this.guildtype = guildtype;
    }
}
