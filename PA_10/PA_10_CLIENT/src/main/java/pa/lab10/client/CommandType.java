package pa.lab10.client;

import lombok.Getter;

@Getter
public enum CommandType {
    LOGIN("0", "login\\s[\\w\\d]+"),
    REGISTER("1", "register\\s[\\w\\d]+"),
    SEND_FRIEND_REQUEST("2", "friend\\s[\\w\\d\\s]+"),
    SEND_MESSAGE_TO_FRIENDS("3", "send\\smessage\\s.*"),
    READ_MESSAGES("5", "read"),
    ERROR("6", "login\\s[\\w\\d]+"),
    EXIT("7", "exit");

    private final String commandCode;
    private final String regex;

    CommandType(String commandCode, String regex) {
        this.commandCode = commandCode;
        this.regex = regex;
    }
}
