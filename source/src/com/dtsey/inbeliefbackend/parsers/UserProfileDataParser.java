package com.dtsey.inbeliefbackend.parsers;

import com.dtsey.inbeliefbackend.data.Sex;
import com.dtsey.inbeliefbackend.data.UserProfileData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileDataParser implements DatabaseDataParserGeneric<UserProfileData> {
    @Override
    public UserProfileData parse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("userId");
        String name = resultSet.getString("name");
        String lastName = resultSet.getString("lastname");
        Sex sex = Sex.values()[resultSet.getInt("sex")];
        int religion = resultSet.getInt("religion");
        int age = resultSet.getInt("age");
        String phone = resultSet.getString("phone");
        String town = resultSet.getString("town");
        String email = resultSet.getString("email");
        String description = resultSet.getString("description");

        return new UserProfileData(id, name, lastName, sex, religion, age, phone, town, email, description);
    }
}
