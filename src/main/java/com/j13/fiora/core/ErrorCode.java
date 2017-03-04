package com.j13.fiora.core;

public class ErrorCode {

    public static class Common {
        public static int INPUT_PARAMETER_ERROR = 4;
        public static int NEED_T = 5;
    }

    public static class User {
        public static int PASSWORD_NOT_RIGHT = 1001;
        public static int MOBILE_EXISTED = 1002;
        public static int NICKNAME_EXISTED = 1003;
        public static int NEED_LOGIN = 1004;

    }

    public static class System {
        public static int SYSTEM_ERROR = 1;
        public static int NOT_FOUND_ACTION = 2;
        public static int ACTION_REFLECT_ERROR = 3;
        public static int REFLECT_ERROR = 4;
    }

    public static class Comment {
        public static int MACHINE_COMMENT_EXISTED = 2001;
    }


}
