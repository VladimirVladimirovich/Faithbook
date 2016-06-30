package com.dtsey.inbeliefbackend.parsers;

import com.dtsey.inbeliefbackend.data.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EventParser implements DatabaseDataParserGeneric<Event> {
    @Override
    public Event parse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int creatorId = resultSet.getInt("creatorId");
        String title = resultSet.getString("title");
        Date date = resultSet.getDate("date");
        int religionId = resultSet.getInt("religionId");
        String town = resultSet.getString("town");
        String place = resultSet.getString("place");
        String description = resultSet.getString("description");

        return new Event(id, creatorId, title, date, religionId, town, place, description);
    }
}
