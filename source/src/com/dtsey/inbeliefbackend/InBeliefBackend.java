package com.dtsey.inbeliefbackend;

import com.dtsey.inbeliefbackend.data.*;
import com.dtsey.inbeliefbackend.data.search.FindEventCriteria;
import com.dtsey.inbeliefbackend.data.search.FindUserCriteria;
import com.dtsey.inbeliefbackend.exception.NoSuchUserException;
import com.dtsey.inbeliefbackend.exception.UserAlreadyRegisteredException;
import com.dtsey.inbeliefbackend.utils.DatabaseConnectionManager;
import com.dtsey.inbeliefbackend.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InBeliefBackend {
    private static InBeliefBackend inBeliefBackend;

    private static int loggedUserId = -1;

    public static InBeliefBackend getInstance() {
        if (inBeliefBackend == null)
            inBeliefBackend = new InBeliefBackend();

        return inBeliefBackend;
    }

    private InBeliefBackend() {
        loggedUserId = 0;
    }

    public boolean login(UserLoginData userLoginData) throws NoSuchUserException {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            loggedUserId = DatabaseUtils.getUserIdByLoginData(connection, userLoginData);

            return (loggedUserId != 0);
        }
        catch (SQLException e) {
            throw  new IllegalStateException(e.getMessage());
        }
    }

    public int getLoggedUserId() {
        return loggedUserId;
    }

    public boolean registerNewUser(UserLoginData userLoginData, UserProfileData userProfileData) throws UserAlreadyRegisteredException {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.registerNewUser(connection, userLoginData, userProfileData);
        }
        catch (SQLException e) {
            throw  new IllegalStateException(e.getMessage());
        }
    }

    public boolean changeUserProfileData(int userId, UserProfileData userProfileData) throws NoSuchUserException {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.setUserProfileData(connection, userId, userProfileData);
        }
        catch (SQLException e) {
            throw  new IllegalStateException(e.getMessage());
        }
    }

    public List<UserProfileData> findUsersByCriteria(FindUserCriteria... userCriteriaArray) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.findUsersByCriteria(connection, userCriteriaArray);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public List<UserProfileData> findAllUsers(FindUserCriteria... userCriteriaArray) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.findUsers(connection);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public List<FriendshipRequest> getFriendshipRequestListForUser(int userId) {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            return DatabaseUtils.getFriendshipRequestList(connection, userId);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean acceptFriendshipRequest(FriendshipRequest friendshipRequest) {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            return DatabaseUtils.acceptFriendshipRequest(connection, friendshipRequest);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean cancelFriendshipRequest(FriendshipRequest friendshipRequest) {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            return DatabaseUtils.cancelFriendshipRequest(connection, friendshipRequest);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean addFriendshipRequest(FriendshipRequest friendshipRequest) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.addFriendshipRequest(connection, friendshipRequest);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public List<UserProfileData> getUserFriends(int userId) {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            return DatabaseUtils.getUserFriends(connection, userId);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public UserProfileData getUserProfileData(int userId) throws NoSuchUserException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            return DatabaseUtils.getUserProfileData(connection, userId);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean addNewEvent(int creatorId, Event event) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.addEvent(connection, creatorId, event);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean subscribeForEvent(EventSubscription eventSubscription) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.subscribeForEvent(connection, eventSubscription);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Event> getEventsByCriteria(FindEventCriteria... findEventCriteriaArray) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.getEventsByCriteria(connection, findEventCriteriaArray);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Event> getUserEventsList(int subscriberId) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.getUserEventsList(connection, subscriberId);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean deleteEvent(Event event, int creatorId) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.deleteEvent(connection, event, creatorId);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean unsubscribeFromEvent(Event event, int loggedUserId) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.unsubscribeFromEvent(connection, new EventSubscription(loggedUserId, event.getId()));
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean deleteFriendshipRelation(int userId, int targetId) {
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            return DatabaseUtils.removeFromFriends(connection, userId, targetId);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void logout() {
        loggedUserId = 0;
    }
}
