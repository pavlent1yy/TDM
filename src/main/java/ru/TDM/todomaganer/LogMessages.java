package ru.TDM.todomaganer;

public class LogMessages {

    public static final String EMOJI_INFO = "⚙️ ";
    public static final String EMOJI_CREATE = "➕ ";
    public static final String EMOJI_UPDATE = "🔄 ";
    public static final String EMOJI_DELETE = "🗑️";
    public static final String EMOJI_LOGIN = "🔐";
    public static final String EMOJI_LOGOUT = "🚪";
    public static final String EMOJI_SUCCESS = "✅";
    public static final String EMOJI_ERROR = "❌";
    public static final String EMOJI_BANG = "💥";
    public static final String EMOJI_WARN = "⚠️ ";
    public static final String EMOJI_DEBUG = "🐞 ";

    public static class INFO {
        public static final String USER_CREATED = EMOJI_CREATE + "User:{} has been created successfully. Name={}, Email={}";
        public static final String TASK_CREATED = EMOJI_CREATE + "User:{} successfully has created a task with id = {} ";

        public static final String USER_DELETED = EMOJI_DELETE + "User id={} has been deleted successfully";
        public static final String TASK_DELETED = EMOJI_DELETE + "Task id={} has been deleted successfully";

        public static final String TASK_IS_COMPLETE_CHANGING = EMOJI_UPDATE + "Task id={} has isComplete status={} ";
    }

    public static class ERROR {
        public static final String USER_DUBLICATE_KEY_VALUE_ERROR = EMOJI_ERROR + "{}";
    }

    public static class WARNING {

    }

    public static class DEDUG {

    }

}
