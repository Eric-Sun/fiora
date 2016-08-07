package com.j13.fiora.core;


public class HDConstants {
    public static int NOT_DELETED = 0;
    public static int DELETED = 1;

    public static int NO_USER_ID = -1;


    public static int USER_IS_MACHINE = 1;
    public static int USER_IS_REAL_USER = 0;

    public static String MACHINE_MOBILE = "0";
    public static String MACHINE_PASSWORD = "0";

    public static int MACHINE_USER_ID = -2;
    public static int JAX_DEFAULT_USER_ID = -3;

    public static int DEFAULT_IMG_ID = 0;

    public static class User {
        public static int COMMON_USER = 0;
        public static int MACHINE_USER = 1;
    }

    public static class FetchSource {
        public static int UGC = 0;
        public static int QSBK = 1;
        public static int NHDZ = 2;
    }


    public static class ResponseStatus {
        public static int SUCCESS = 0;
        public static int FAILURE = 1;
    }


}
