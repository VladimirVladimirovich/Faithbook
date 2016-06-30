package com.dtsey.inbeliefbackend.parsers;

import com.dtsey.inbeliefbackend.data.search.DatabaseFieldNameParsable;

public class CriteriaArrayParser<T extends DatabaseFieldNameParsable> {
    public String convertToString(T[] array) {
        StringBuilder criteriaNames = new StringBuilder();

        int count = 0;

        for (T t: array) {
            ++count;
            criteriaNames.append(covertToStringSingleObject(t));

            if (count < array.length)
                criteriaNames.append(" AND ");
        }

        return criteriaNames.toString();
    }

    public String convertToString(T t) {
        return covertToStringSingleObject(t);
    }

    public String covertToStringSingleObject(T t) {
        return t.toDatabaseFieldName() + " = ?";
    }
}
