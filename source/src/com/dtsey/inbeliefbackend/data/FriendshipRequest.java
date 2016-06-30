package com.dtsey.inbeliefbackend.data;

/**
 * Created by dtsey on 5/15/15.
 */
public class FriendshipRequest {
    private int id;
    private int initiatorId;
    private int targetId;

    public FriendshipRequest(int id, int initiatorId, int targetId) {
        this.id = id;
        this.initiatorId = initiatorId;
        this.targetId = targetId;
    }

    public FriendshipRequest(int initiatorId, int targetId) {
        this.initiatorId = initiatorId;
        this.targetId = targetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(int initiatorId) {
        this.initiatorId = initiatorId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "FriendshipRequest{" +
                "id=" + id +
                ", initiatorId=" + initiatorId +
                ", targetId=" + targetId +
                '}';
    }
}
