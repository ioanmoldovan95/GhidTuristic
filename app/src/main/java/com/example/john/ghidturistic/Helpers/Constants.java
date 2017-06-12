package com.example.john.ghidturistic.Helpers;

/**
 * Created by John on 3/14/2017.
 */

public class Constants {

    public static class MapConstants {
        public static String API_KEY = "AIzaSyAoORdblrrOSx2J6Wv1s1BXbc9Xd1jnVOg";
    }

    public static class PermissionContacts {
        public static int ACCESS_COARSE_LOCATION = 1;
    }

    public static class Keys {
        public static String EMAIL_KEY = "email";
        public static String OBJECTIVES_KEY = "objectives";
        public static String POSITION_KEY = "position";
        public static String LAT = "latitude";
        public static String LONG = "longitude";
    }

    public static class FirebaseDBKeys {
        public static String USERS_DB = "users";
        public static String OBJECTIVES_DB = "objectives";
    }

    public static class Zoom {
        public static int MAX_ZOOM = 15;
        public static int MIN_ZOOM = 1;
        public static int MEDIUM_LOW_ZOOM = 4;
        public static int MEDIUM_HIGH_ZOOM = 12;
        public static int MEDIUM_ZOOM = 8;
    }

    public static class RequestCodes {
        public static int POSITION_CODE_MAP = 1;
        public static int POSITION_CODE_INPUT = 1;
    }

    public static class BusCodes {
        public static int LOGIN_USER_CODE = 1;
        public static int LOGOUT_USER_CODE = 2;
        public static int CLOSE_LOGIN_ACTIVITY_CODE = 3;
        public static int LOGIN_FAILED = 4;
        public static int OBJECTIVES_UPDATED = 5;

    }
}
