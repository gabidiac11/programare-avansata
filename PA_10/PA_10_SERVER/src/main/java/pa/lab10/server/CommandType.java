package pa.lab10.server;

import lombok.Getter;

/**
 * identify what type of request the client is sending
 */
@Getter
public enum CommandType {
    LOGIN("0"),
    REGISTER("1"),
    SEND_FRIEND_REQUEST("2"),
    SEND_MESSAGE_TO_FRIENDS("3"),
    UNKNOWN_PROBLEM("4"),
    READ_MESSAGES("5"),
    EXIT("7");

    private final String commandCode;

    CommandType(String commandCode) {
        this.commandCode = commandCode;
    }
}
