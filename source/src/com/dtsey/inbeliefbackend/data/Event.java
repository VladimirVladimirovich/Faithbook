package com.dtsey.inbeliefbackend.data;

import java.util.Date;

/**
 * Created by dtsey on 5/16/15.
 */
public class Event {
    private int id;
    private int creatorId;
    private String title;
    private Date date;
    private int religionId;
    private String town;
    private String place;
    private String description;

    public Event(int id, int creatorId, String title, Date date, int religionId, String town, String place, String description) {
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.date = date;
        this.religionId = religionId;
        this.town = town;
        this.place = place;
        this.description = description;
    }

    public Event(String title, Date date, int religionId, String town, String place, String description) {
        this.title = title;
        this.date = date;
        this.religionId = religionId;
        this.town = town;
        this.place = place;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getReligionId() {
        return religionId;
    }

    public void setReligionId(int religionId) {
        this.religionId = religionId;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", religionId=" + religionId +
                ", town='" + town + '\'' +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
