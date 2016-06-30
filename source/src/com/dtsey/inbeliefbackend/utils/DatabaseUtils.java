package com.dtsey.inbeliefbackend.utils;

import com.dtsey.inbeliefbackend.data.*;
import com.dtsey.inbeliefbackend.data.search.FindEventCriteria;
import com.dtsey.inbeliefbackend.data.search.FindUserCriteria;
import com.dtsey.inbeliefbackend.exception.NoSuchUserException;
import com.dtsey.inbeliefbackend.exception.UserAlreadyRegisteredException;
import com.dtsey.inbeliefbackend.parsers.*;

import java.sql.*;
import java.util.List;

public class DatabaseUtils {
    private static final int ACCEPT_FRIENDSHIP_REQUEST_CODE = 0;

    public static boolean registerNewUser(Connection connection, UserLoginData userLoginData, UserProfileData userProfileData) throws SQLException, UserAlreadyRegisteredException {
        if (isUserRegistered(connection, userLoginData))
            throw new UserAlreadyRegisteredException("User with login: '" + userLoginData.getLogin() + "' has been already registered");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RegistrationData (login, password) VALUES(?, ?)");
        preparedStatement.setString(1, userLoginData.getLogin());
        preparedStatement.setString(2, userLoginData.getPassword());

        int added = preparedStatement.executeUpdate();

        if (added > 0) {
            try {
                int userId = getUserIdByLoginData(connection, userLoginData);

                return addUserProfileData(connection, userId, userProfileData);
            } catch (NoSuchUserException e) {
                e.printStackTrace();
                return false;
            }
        }
        else
            return false;
    }

    private static boolean addUserProfileData(Connection connection, int userId, UserProfileData userProfileData) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO UserProfileData " +
                "(userId, name, lastname, religion, age, sex, phone, town, email, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, userProfileData.getName());
        preparedStatement.setString(3, userProfileData.getLastName());
        preparedStatement.setInt(4, userProfileData.getReligion());
        preparedStatement.setInt(5, userProfileData.getAge());
        preparedStatement.setInt(6, userProfileData.getSex().ordinal());
        preparedStatement.setString(7, userProfileData.getPhone());
        preparedStatement.setString(8, userProfileData.getTown());
        preparedStatement.setString(9, userProfileData.getEmail());
        preparedStatement.setString(10, userProfileData.getDescription());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean setUserProfileData(Connection connection, int userId, UserProfileData userProfileData) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE UserProfileData SET " +
                "name=?, lastname=?, religion=?, age=?, sex=?, phone=?, town=?, email=?, description=? " +
                "WHERE userId=?");
        preparedStatement.setString(1, userProfileData.getName());
        preparedStatement.setString(2, userProfileData.getLastName());
        preparedStatement.setInt(3, userProfileData.getReligion());
        preparedStatement.setInt(4, userProfileData.getAge());
        preparedStatement.setInt(5, userProfileData.getSex().ordinal());
        preparedStatement.setString(6, userProfileData.getPhone());
        preparedStatement.setString(7, userProfileData.getTown());
        preparedStatement.setString(8, userProfileData.getEmail());
        preparedStatement.setString(9, userProfileData.getDescription());
        preparedStatement.setInt(10, userId);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean isUserRegistered(Connection connection, UserLoginData userLoginData) throws SQLException {
        try {
            return (getUserIdByLoginData(connection, userLoginData) > 0);
        }
        catch (NoSuchUserException e) {
            return false;
        }
    }

    public static int getUserIdByLoginData(Connection connection, UserLoginData userLoginData) throws SQLException, NoSuchUserException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM RegistrationData WHERE login LIKE ? AND password LIKE ?");
        preparedStatement.setString(1, userLoginData.getLogin());
        preparedStatement.setString(2, userLoginData.getPassword());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            return resultSet.getInt("id");
        else
            throw new NoSuchUserException("No user with LoginData " + userLoginData);
    }

    public static UserProfileData getUserProfileData(Connection connection, int userId) throws SQLException, NoSuchUserException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserProfileData WHERE userId=? LIMIT 1");
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new UserProfileDataParser().parse(resultSet);
        }
        else
            throw new NoSuchUserException("No user with id: " + userId);
    }

    public static List<UserProfileData> findUsersByCriteria(Connection connection, FindUserCriteria... findUserCriteriaArray) throws SQLException {
        String criteriaNames = new CriteriaArrayParser<FindUserCriteria>().convertToString(findUserCriteriaArray);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserProfileData WHERE (" + criteriaNames + ")");

        int i = 0;

        for (FindUserCriteria findUserCriteria: findUserCriteriaArray) {
            switch (findUserCriteria) {
                case NAME:
                    preparedStatement.setString(++i, findUserCriteria.getName());
                    break;

                case LASTNAME:
                    preparedStatement.setString(++i, findUserCriteria.getLastname());
                    break;

                case RELIGION:
                    preparedStatement.setInt(++i, findUserCriteria.getReligion());
                    break;

                case AGE:
                    preparedStatement.setInt(++i, findUserCriteria.getAge());
                    break;

                case SEX:
                    preparedStatement.setInt(++i, findUserCriteria.getSex().ordinal());
                    break;

                case TOWN:
                    preparedStatement.setString(++i, findUserCriteria.getTown());
                    break;
            }
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        return new UserProfileDataListParser().parse(resultSet);
    }

    public static List<UserProfileData> findUsers(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserProfileData");
        ResultSet resultSet = preparedStatement.executeQuery();

        return new UserProfileDataListParser().parse(resultSet);
    }

    public static List<FriendshipRequest> getFriendshipRequestList(Connection connection, int targetId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FriendshipRequests WHERE targetId=? AND status=1");
        preparedStatement.setInt(1, targetId);

        ResultSet resultSet = preparedStatement.executeQuery();

        return new FriendshipRequestListParser().parse(resultSet);
    }

    public static boolean acceptFriendshipRequest(Connection connection, FriendshipRequest friendshipRequest) throws SQLException {
        return changeFriendshipRequestStatus(connection, friendshipRequest.getId(), ACCEPT_FRIENDSHIP_REQUEST_CODE)
                && addAcceptedFriendshipRequest(connection, new FriendshipRequest(friendshipRequest.getTargetId(), friendshipRequest.getInitiatorId()));
    }

    private static boolean addAcceptedFriendshipRequest(Connection connection, FriendshipRequest friendshipRequest) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FriendshipRequests (initiatorId, targetId, status) VALUES(?, ?, 0)");
        preparedStatement.setInt(1, friendshipRequest.getInitiatorId());
        preparedStatement.setInt(2, friendshipRequest.getTargetId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean cancelFriendshipRequest(Connection connection, FriendshipRequest friendshipRequest) throws SQLException {
        return deleteFriendshipRequestStatus(connection, friendshipRequest.getId());
    }

    private static boolean deleteFriendshipRequestStatus(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM FriendshipRequests WHERE id=?");
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

    private static boolean changeFriendshipRequestStatus(Connection connection, int requestId, int status) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FriendshipRequests SET status=? WHERE id=?");
        preparedStatement.setInt(1, status);
        preparedStatement.setInt(2, requestId);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean addFriendshipRequest(Connection connection, FriendshipRequest friendshipRequest) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FriendshipRequests (initiatorId, targetId) VALUES(?, ?)");
        preparedStatement.setInt(1, friendshipRequest.getInitiatorId());
        preparedStatement.setInt(2, friendshipRequest.getTargetId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static List<UserProfileData> getUserFriends(Connection connection, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserProfileData, FriendshipRequests WHERE FriendshipRequests.status=0 AND FriendshipRequests.initiatorId = ? AND UserProfileData.userId=FriendshipRequests.targetId");
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        return new UserProfileDataListParser().parse(resultSet);
    }

    public static boolean addEvent(Connection connection, int creatorId, Event event) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Events " +
                "(creatorId, title, date, religionId, town, place, description) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, creatorId);
        preparedStatement.setString(2, event.getTitle());
        preparedStatement.setDate(3, new Date(event.getDate().getTime()));
        preparedStatement.setInt(4, event.getReligionId());
        preparedStatement.setString(5, event.getTown());
        preparedStatement.setString(6, event.getPlace());
        preparedStatement.setString(7, event.getDescription());

        int added = preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            int lastAddedEventId = -1;

            lastAddedEventId = resultSet.getInt(1);

            return subscribeForEvent(connection, new EventSubscription(creatorId, lastAddedEventId));
        }
        else
            return false;
    }

    public static boolean subscribeForEvent(Connection connection, EventSubscription eventSubscription) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EventsVisitsRecords " +
                "(eventId, visitorId) " +
                "VALUES(?, ?)");
        preparedStatement.setInt(1, eventSubscription.getEventId());
        preparedStatement.setInt(2, eventSubscription.getVisitorId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static List<Event> getEventsByCriteria(Connection connection, FindEventCriteria... findEventCriteriaArray) throws SQLException {
        String criteriaNames = new CriteriaArrayParser<FindEventCriteria>().convertToString(findEventCriteriaArray);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Events WHERE (" + criteriaNames + ")");

        int i = 0;

        for (FindEventCriteria findEventCriteria: findEventCriteriaArray) {
            switch (findEventCriteria) {
                case CREATOR_ID:
                    preparedStatement.setInt(++i, findEventCriteria.getCreatorId());
                    break;

                case DATE:
                    preparedStatement.setDate(++i, new Date(findEventCriteria.getDate().getTime()));
                    break;

                case RELIGION:
                    preparedStatement.setInt(++i, findEventCriteria.getReligion());
                    break;

                case TITLE:
                    preparedStatement.setString(++i, findEventCriteria.getTitle());
                    break;

                case TOWN:
                    preparedStatement.setString(++i, findEventCriteria.getTown());
                    break;
            }
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        return new EventListParser().parse(resultSet);
    }

    public static List<Event> getUserEventsList(Connection connection, int subscriberId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Events, EventsVisitsRecords " +
                "WHERE EventsVisitsRecords.visitorId=? AND Events.id=EventsVisitsRecords.eventId");
        preparedStatement.setInt(1, subscriberId);

        ResultSet resultSet = preparedStatement.executeQuery();

        return new EventListParser().parse(resultSet);
    }

    public static boolean deleteEvent(Connection connection, Event event, int creatorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Events WHERE id=? AND creatorId=? LIMIT 1");
        preparedStatement.setInt(1, event.getId());
        preparedStatement.setInt(2, creatorId);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean unsubscribeFromEvent(Connection connection, EventSubscription eventSubscription) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EventsVisitsRecords " +
                "WHERE eventId=? AND visitorId=?");
        preparedStatement.setInt(1, eventSubscription.getEventId());
        preparedStatement.setInt(2, eventSubscription.getVisitorId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean removeFromFriends(Connection connection, int userId, int targetId) throws SQLException {
        return deleteFriendshipRelation(connection, userId, targetId)
                && deleteFriendshipRelation(connection, targetId, userId);
    }

    private static boolean deleteFriendshipRelation(Connection connection, int userId, int targetId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM FriendshipRequests " +
                "WHERE initiatorId=? AND targetId=? LIMIT 1");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, targetId);

        return preparedStatement.executeUpdate() > 0;
    }
}
