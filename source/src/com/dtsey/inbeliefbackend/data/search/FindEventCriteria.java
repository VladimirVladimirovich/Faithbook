package com.dtsey.inbeliefbackend.data.search;

import java.util.Date;

public enum FindEventCriteria implements DatabaseFieldNameParsable {
    CREATOR_ID {
        private int creatorId;

        @Override
        public String toDatabaseFieldName() {
            return "creatorId";
        }

        @Override
        public FindEventCriteria setCreatorId(int creatorId) {
            this.creatorId = creatorId;

            return this;
        }

        @Override
        public int getCreatorId() {
            return this.creatorId;
        }
    },

    DATE {
        private Date date;

        @Override
        public String toDatabaseFieldName() {
            return "date";
        }

        @Override
        public FindEventCriteria setDate(Date date) {
            this.date = date;

            return this;
        }

        @Override
        public Date getDate() {
            return this.date;
        }
    },

    RELIGION {
        private int religion;

        @Override
        public FindEventCriteria setReligion(int religionId) {
            this.religion = religionId;

            return this;
        }

        @Override
        public String toDatabaseFieldName() {
            return "religionId";
        }

        @Override
        public int getReligion() {
            return this.religion;
        }
    },

    TITLE {
        private String title;

        @Override
        public FindEventCriteria setTitle(String title) {
            this.title = title;

            return this;
        }

        @Override
        public String toDatabaseFieldName() {
            return "title";
        }

        @Override
        public String getTitle() {
            return this.title;
        }
    },

    TOWN {
        private String town;

        @Override
        public String toDatabaseFieldName() {
            return "town";
        }

        @Override
        public FindEventCriteria setTown(String town) {
            this.town = town;

            return this;
        }

        @Override
        public String getTown() {
            return this.town;
        }
    };

    public String toDatabaseFieldName() {
        throw new UnsupportedOperationException();
    }

    public FindEventCriteria setCreatorId(int creatorId) { throw new UnsupportedOperationException(); }
    public FindEventCriteria setDate(Date date) { throw new UnsupportedOperationException(); }
    public FindEventCriteria setReligion(int religionId){ throw new UnsupportedOperationException(); }
    public FindEventCriteria setTown(String town) { throw new UnsupportedOperationException(); }
    public FindEventCriteria setTitle(String title) { throw new UnsupportedOperationException(); }

    public int getCreatorId() { throw new UnsupportedOperationException(); }
    public Date getDate() { throw new UnsupportedOperationException(); }
    public int getReligion() { throw new UnsupportedOperationException(); }
    public String getTown() { throw new UnsupportedOperationException(); }
    public String getTitle() { throw new UnsupportedOperationException(); }
}
