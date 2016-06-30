package com.dtsey.inbeliefbackend.data;

/**
 * Created by dtsey on 5/16/15.
 */
public class EventSubscription {
    private int id;
    private int visitorId;
    private int eventId;

    public EventSubscription(int id, int visitorId, int eventId) {
        this.id = id;
        this.visitorId = visitorId;
        this.eventId = eventId;
    }

    public EventSubscription(int visitorId, int eventId) {
        this.visitorId = visitorId;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "EventSubscription{" +
                "id=" + id +
                ", visitorId=" + visitorId +
                ", eventId=" + eventId +
                '}';
    }
}
