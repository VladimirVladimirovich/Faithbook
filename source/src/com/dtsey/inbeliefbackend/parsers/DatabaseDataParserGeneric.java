package com.dtsey.inbeliefbackend.parsers;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface DatabaseDataParserGeneric<T> {
    T parse(ResultSet resultSet) throws SQLException;
}
