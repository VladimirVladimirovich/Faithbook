package com.dtsey.inbeliefbackend.parsers;

import com.dtsey.inbeliefbackend.data.FriendshipRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendshipRequestListParser implements DatabaseDataParserGeneric<List<FriendshipRequest>> {
    @Override
    public List<FriendshipRequest> parse(ResultSet resultSet) throws SQLException {
        List<FriendshipRequest> friendshipRequestList = new ArrayList<>();

        while (resultSet.next())
            friendshipRequestList.add(new FriendshipRequestParser().parse(resultSet));

        return friendshipRequestList;
    }
}
