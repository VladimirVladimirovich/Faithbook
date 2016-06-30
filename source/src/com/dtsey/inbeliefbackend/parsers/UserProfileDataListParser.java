package com.dtsey.inbeliefbackend.parsers;

import com.dtsey.inbeliefbackend.data.UserProfileData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDataListParser implements DatabaseDataParserGeneric<List<UserProfileData>>{
    @Override
    public List<UserProfileData> parse(ResultSet resultSet) throws SQLException {
        List<UserProfileData> userProfileDataList = new ArrayList<>();

        while (resultSet.next())
            userProfileDataList.add(new UserProfileDataParser().parse(resultSet));

        return userProfileDataList;
    }
}
