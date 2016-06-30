package com.dtsey.inbeliefbackend.parsers;

import com.dtsey.inbeliefbackend.data.FriendshipRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipRequestParser implements DatabaseDataParserGeneric<FriendshipRequest> {
    @Override
    public FriendshipRequest parse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int initiatorId = resultSet.getInt("initiatorId");
        int targetId = resultSet.getInt("targetId");

        return new FriendshipRequest(id, initiatorId, targetId);
    }
}
