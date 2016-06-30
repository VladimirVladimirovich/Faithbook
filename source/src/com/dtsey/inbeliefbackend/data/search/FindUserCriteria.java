package com.dtsey.inbeliefbackend.data.search;

import com.dtsey.inbeliefbackend.data.Sex;

public enum FindUserCriteria implements DatabaseFieldNameParsable {
    NAME {
        private String name;

        @Override
        public FindUserCriteria setName(String name) {
            this.name = name;

            return this;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String toDatabaseFieldName() {
            return "name";
        }
    },
    LASTNAME {
        private String lastname;

        @Override
        public FindUserCriteria setLastname(String lastname) {
            this.lastname = lastname;

            return this;
        }

        @Override
        public String getLastname() {
            return this.lastname;
        }

        @Override
        public String toDatabaseFieldName() {
            return "lastname";
        }
    },
    RELIGION {
        private int religion;

        @Override
        public FindUserCriteria setReligion(int religionId) {
            this.religion = religionId;

            return this;
        }

        @Override
        public int getReligion() {
            return this.religion;
        }

        @Override
        public String toDatabaseFieldName() {
            return "religion";
        }
    },
    AGE {
        private int age;

        @Override
        public FindUserCriteria setAge(int age) {
            this.age = age;

            return this;
        }

        @Override
        public int getAge() {
            return this.age;
        }

        @Override
        public String toDatabaseFieldName() {
            return "age";
        }
    },
    SEX {
        private Sex sex;

        @Override
        public FindUserCriteria setSex(Sex sex) {
            this.sex = sex;

            return this;
        }

        @Override
        public Sex getSex() {
            return this.sex;
        }

        @Override
        public String toDatabaseFieldName() {
            return "sex";
        }
    },
    TOWN {
        private String town;

        @Override
        public FindUserCriteria setTown(String town) {
            this.town = town;

            return this;
        }

        @Override
        public String getTown() {
            return this.town;
        }

        @Override
        public String toDatabaseFieldName() {
            return "town";
        }
    };

    public String toDatabaseFieldName() {
        throw new UnsupportedOperationException();
    }

    public FindUserCriteria setName(String name) { throw new UnsupportedOperationException(); }
    public FindUserCriteria setLastname(String lastname) { throw new UnsupportedOperationException(); }
    public FindUserCriteria setReligion(int religionId){ throw new UnsupportedOperationException(); }
    public FindUserCriteria setAge(int age) { throw new UnsupportedOperationException(); }
    public FindUserCriteria setSex(Sex sex) { throw new UnsupportedOperationException(); }
    public FindUserCriteria setTown(String town) { throw new UnsupportedOperationException(); }

    public String getName() { throw new UnsupportedOperationException(); }
    public String getLastname() { throw new UnsupportedOperationException(); }
    public int getReligion() { throw new UnsupportedOperationException(); }
    public int getAge() { throw new UnsupportedOperationException(); }
    public Sex getSex() { throw new UnsupportedOperationException(); }
    public String getTown() { throw new UnsupportedOperationException(); }
}
