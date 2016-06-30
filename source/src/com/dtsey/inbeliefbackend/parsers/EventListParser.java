package com.dtsey.inbeliefbackend.parsers;

import com.dtsey.inbeliefbackend.data.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventListParser implements DatabaseDataParserGeneric<List<Event>> {
    @Override
    public List<Event> parse(ResultSet resultSet) throws SQLException {
        List<Event> events = new ArrayList<>();

        while (resultSet.next()) {
            events.add(new EventParser().parse(resultSet));
        }

        return events;
    }
}
