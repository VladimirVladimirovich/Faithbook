package com.dtsey;

import com.dtsey.inbeliefbackend.InBeliefBackend;
import com.dtsey.inbeliefbackend.data.*;
import com.dtsey.inbeliefbackend.data.search.FindEventCriteria;
import com.dtsey.inbeliefbackend.data.search.FindUserCriteria;
import com.dtsey.inbeliefbackend.exception.UserAlreadyRegisteredException;
import com.dtsey.inbeliefbackend.parsers.CriteriaArrayParser;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        registerNewUserTest();

        loginTest();

        //testAddFriendshipRequest();
        //getFriendshipRequestList();

        //testGetUserFriends();

        //testAddEvent();

        //testGetUserEventList();

        testGetEventsListByReligion();
        System.out.println("");

        testGetEventsListByDate();
        System.out.println("");

        testGetEventListByTown();
        System.out.println("");

        /*updateUserProfileData();

        findUsersByAgeAndSex();
        findUsersByReligion();
        findUsersByName();

        badLoginTestWithIncorrectPassword();
        badLoginTestWithNonExistingUser();*/
    }

    private static void testGetEventListByTown() {
        FindEventCriteria byTown = FindEventCriteria.TOWN.setTown("Kiev");

        List<Event> eventList = InBeliefBackend.getInstance().getEventsByCriteria(byTown);
        System.out.println("Events: ");
        for (Event event: eventList)
            System.out.println(event);
    }

    private static void testGetEventsListByDate() {
        FindEventCriteria byDate = FindEventCriteria.DATE.setDate(new Date(System.currentTimeMillis()));

        List<Event> eventList = InBeliefBackend.getInstance().getEventsByCriteria(byDate);
        System.out.println("Events: ");
        for (Event event: eventList)
            System.out.println(event);
    }

    private static void testGetEventsListByReligion() {
        FindEventCriteria byReligion = FindEventCriteria.RELIGION.setReligion(2);

        List<Event> eventList = InBeliefBackend.getInstance().getEventsByCriteria(byReligion);
        System.out.println("Events: ");
        for (Event event: eventList)
            System.out.println(event);
    }

    private static void testGetUserEventList() {
        int userId = InBeliefBackend.getInstance().getLoggedUserId();

        List<Event> eventList = InBeliefBackend.getInstance().getUserEventsList(userId);
        System.out.println("Events: ");
        for (Event event: eventList)
            System.out.println(event);
    }

    private static void testSubscribeForEvent() {
        int eventId = 3;
        int subscriberId = InBeliefBackend.getInstance().getLoggedUserId();
        EventSubscription eventSubscription = new EventSubscription(subscriberId, eventId);

        boolean subscribeResult = InBeliefBackend.getInstance().subscribeForEvent(eventSubscription);
        System.out.println("subscribeResult: " + subscribeResult);
    }

    private static void testAddEvent() {
        int creatorId = InBeliefBackend.getInstance().getLoggedUserId();

        String title = "S";
        Date date = new Date(System.currentTimeMillis());
        int religionId = 3;
        String town = "Paris";
        String place = "Tower";
        String description = "Pray for someone";

        Event event = new Event(title, date, religionId, town, place, description);

        boolean addEventResult = InBeliefBackend.getInstance().addNewEvent(creatorId, event);
        System.out.println("addNewEvent: " + addEventResult);
    }

    private static void testGetUserFriends() {
        try {
            int userId = InBeliefBackend.getInstance().getLoggedUserId();

            List<UserProfileData> userProfileDataList = InBeliefBackend.getInstance().getUserFriends(userId);
            System.out.println("User friends list: ");
            for (UserProfileData userProfileData: userProfileDataList)
                System.out.println(userProfileData);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void testAddFriendshipRequest() {
        try {
            boolean createFriendshipResult = InBeliefBackend.getInstance().addFriendshipRequest(new FriendshipRequest(19, 1));

            System.out.println("createFriendshipResult: " + createFriendshipResult) ;
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getFriendshipRequestList() {
        try {
            int userId = InBeliefBackend.getInstance().getLoggedUserId();
            List<FriendshipRequest> friendshipRequestList = InBeliefBackend.getInstance().getFriendshipRequestListForUser(userId);

            System.out.println("FriendshipRequest list: ");

            for (FriendshipRequest friendshipRequest: friendshipRequestList)
                System.out.println(friendshipRequest);

            testAcceptFriendship(friendshipRequestList.get(0));
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void testAcceptFriendship(FriendshipRequest friendshipRequest) {
        try {
            boolean acceptFriendship = InBeliefBackend.getInstance().acceptFriendshipRequest(friendshipRequest);
            System.out.println("acceptFriendshipRequest: " + acceptFriendship);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findUsersByReligion() {
        try {
            int religionId = 1;
            FindUserCriteria byReligion = FindUserCriteria.RELIGION.setReligion(religionId);

            List<UserProfileData> userProfileDataList = InBeliefBackend.getInstance().findUsersByCriteria(byReligion);

            System.out.println("Found user result: (" + new CriteriaArrayParser<FindUserCriteria>().convertToString(byReligion) + ")" );
            for (UserProfileData userProfileData: userProfileDataList)
                System.out.println(userProfileData);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findUsersByName() {
        try {
            String name = "Toma";
            FindUserCriteria byName = FindUserCriteria.NAME.setName(name);

            List<UserProfileData> userProfileDataList = InBeliefBackend.getInstance().findUsersByCriteria(byName);

            System.out.println("Found user result: (" + new CriteriaArrayParser<FindUserCriteria>().convertToString(byName) + ")" );
            for (UserProfileData userProfileData: userProfileDataList)
                System.out.println(userProfileData);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findUsersByAgeAndSex() {
        try {
            // TODO: look at the generic:
            // FindUserCriteria bySomething = FindUserCriteria.SOMETHING.setSomething(somethingValue);
            
            int age = 19;
            FindUserCriteria byAge = FindUserCriteria.AGE.setAge(age);

            Sex sex = Sex.FEMALE;
            FindUserCriteria bySex = FindUserCriteria.SEX.setSex(sex);

            FindUserCriteria[] findUserCriteria = {byAge, bySex};

            // TODO: READ THIS, BRO!
            // you can do like this too, bro
            //List<UserProfileData> userProfileDataList = InBeliefBackend.getInstance().findUsersByCriteria(byAge, bySex);

            // TODO: AND THIS, BRO!
            // or so
            //List<UserProfileData> userProfileDataList = InBeliefBackend.getInstance().findUsersByCriteria(byAge, bySex, byName, byLastname);
            // and so on. Enjoy! ;)


            List<UserProfileData> userProfileDataList = InBeliefBackend.getInstance().findUsersByCriteria(findUserCriteria);

            System.out.println("Found user result: (" + new CriteriaArrayParser<FindUserCriteria>().convertToString(findUserCriteria) + ")");
            for (UserProfileData userProfileData: userProfileDataList)
                System.out.println(userProfileData);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateUserProfileData() {
        try {
            String name = new String("НовоеИмя".getBytes(), "UTF-8");
            String lastname = "Новоеимя";
            Sex sex = Sex.MALE;
            int religionId = 1; // number of religion from drop down list. It's your problem to map it.
            int age = 22;
            String phone = "38064364356";
            String town = "Харьков";
            String email = "тест@doamin.com";
            String description = "Бога нет";

            UserProfileData userProfileData = new UserProfileData(name, lastname, sex, religionId, age, phone, town, email, description);

            int loggedUserId = InBeliefBackend.getInstance().getLoggedUserId();

            boolean isProfileDataUpdated = InBeliefBackend.getInstance().changeUserProfileData(loggedUserId, userProfileData);

            System.out.println("Change user profile data: " + isProfileDataUpdated);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerNewUserTest() {
        try {
            String name = "Name";
            String lastname = "Lastname";
            Sex sex = Sex.MALE;
            int religionId = 1; // number of religion from drop down list. It's your problem to map it.
            int age = 21;
            String phone = "380643646346";
            String town = "Lvov";
            String email = "sometext@doamin.com";
            String description = "Love our God";

            UserProfileData userProfileData = new UserProfileData(name, lastname, sex, religionId, age, phone, town, email, description);

            boolean isRegisteredSuccessfully = InBeliefBackend.getInstance().registerNewUser(new UserLoginData("dtsey", "12345"), userProfileData);

            System.out.println("isRegisteredSuccessfully: " + isRegisteredSuccessfully);
        }
        catch (UserAlreadyRegisteredException e) {
            System.out.println("UserAlreadyExistsException: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Exception while execute backend method: " + e.getMessage());
        }
    }

    private static void loginTest() {
        try {
            UserLoginData userLoginData = new UserLoginData("admin", "admin");

            boolean isLogged = InBeliefBackend.getInstance().login(userLoginData);

            System.out.println(userLoginData + " isLogged: " + isLogged);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void badLoginTestWithIncorrectPassword() {
        try {
            UserLoginData userLoginData = new UserLoginData("dtsey", "incorrectPassword");

            boolean isLogged = InBeliefBackend.getInstance().login(userLoginData);

            System.out.println(userLoginData + " isLogged: " + isLogged);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void badLoginTestWithNonExistingUser() {
        try {
            UserLoginData userLoginData = new UserLoginData("someoneWhoDoesNotExist", "anyPassword");

            boolean isLogged = InBeliefBackend.getInstance().login(userLoginData);

            System.out.println(userLoginData + " isLogged: " + isLogged);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
