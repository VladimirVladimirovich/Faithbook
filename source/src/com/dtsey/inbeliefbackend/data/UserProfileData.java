package com.dtsey.inbeliefbackend.data;

public class UserProfileData {
    private int id;
    private String name;
    private String lastName;
    private Sex sex;
    private int religion;
    private int age;
    private String phone;
    private String town;
    private String email;
    private String description;

    public UserProfileData(String name, String lastName, Sex sex, int religion, int age, String phone, String town, String email, String description) {
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.religion = religion;
        this.age = age;
        this.phone = phone;
        this.town = town;
        this.email = email;
        this.description = description;
    }

    public UserProfileData(int id, String name, String lastName, Sex sex, int religion, int age, String phone, String town, String email, String description) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.religion = religion;
        this.age = age;
        this.phone = phone;
        this.town = town;
        this.email = email;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getReligion() {
        return religion;
    }

    public void setReligion(int religion) {
        this.religion = religion;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserProfileData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex +
                ", religion=" + religion +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", town='" + town + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
