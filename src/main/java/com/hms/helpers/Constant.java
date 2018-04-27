package com.hms.helpers;

public class Constant {
    public static class ROOM_STATUS {
        public static String VERIFIED = "VERIFIED";
        public static String UNVERIFIED = "UNVERIFIED";
    }

    public static class BOOKING_STATUS {
        public static String PENDING = "PENDING";
        public static String CONFIRMED = "CONFIRMED";
        public static String COMPLETED = "COMPLETED";
    }

    public static class ROOM_TYPE {
        public static String FAMILY = "FAMILY";
        public static String DELUXE = "DELUXE";
        public static String EXECUTIVE = "EXECUTIVE";
    }

    public static class ROOM_TYPE_VALUE {
        public static Integer FAMILY = 1;
        public static Integer DELUXE = 2;
        public static Integer EXECUTIVE = 3;
    }

    public static class USER_ROLE {
        public static Integer VERIFIED = 1;
        public static Integer UNVERIFIED = 4;
        public static Integer ADMIN = 2;
        public static Integer MANAGER = 3;
    }
}
