package pa.lab10.client;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * creates an instance mainly using the text typed from the keyboard
 *
 * is used:
 * - to validate a command text and extract its arguments and assigns a CommandType
 * - to create JSON body string using the arguments received, so it could be ready for it to be sent
 * - for the type CommandType.ERROR -> is used to return a special type of command with a diagnostic message as parameter (Ex. 'send message ' -> command has an empty argument)
 */
@Getter
public class Command {
    private CommandType commandType;
    private List<String> arguments;
    private String originalCommandText;
    private String requestBody;

    public Command(CommandType commandType, List<String> arguments, String originalCommandText) {
        this.commandType = commandType;
        this.arguments = arguments;
        this.originalCommandText = originalCommandText;
        this.requestBody = this.createBodyRequest();
    }

    /**
     * extracts arguments from a presumed correct login command ('login+space+letters and numbers')
     * @param commandText - the validated string obtained int the commandFromText() method
     * @param commandType - the presumed type obtained in the commandFromText() method
     * @return - a command instance with the request body ready
     */
    private static Command parseLoginOrRegister(String commandText, CommandType commandType) {
        String[] splitString = commandType.equals(CommandType.LOGIN) ? commandText.split("login\\s") : commandText.split("register\\s");
        if(splitString.length < 2) {
            return generateError("Command text parse error.", commandText);
        }

        return new Command(commandType, Collections.singletonList(splitString[1]), commandText);
    }

    /**
     * used for commands like 'friend a b c a a'
     * @param list
     * @return
     */
    private static List<String> uniqueResults(List<String> list) {
        List<String> results = new ArrayList<>();
        for(String listItem : list) {
            if(results.stream().filter(listItem::equals).toArray().length == 0) {
                results.add(listItem);
            }
        }
        return results;
    }

    /**
     * extracts arguments from a presumed correct friend command
     * @param commandText - the validated string obtained int the commandFromText() method
     * @return - a command instance with the request body ready
     */
    private static Command parseFriendCommand(String commandText) {
        String[] splitString = commandText.split("friend\\s") ;
        if(splitString.length < 2) {
            return generateError("Friend have no usernames.", commandText);
        }

        List<String> usernames = Arrays.stream(splitString[1].split("\\s"))
                .filter(item -> !item.isEmpty() && !item.isBlank())
                .collect(Collectors.toList());

        return new Command(CommandType.SEND_FRIEND_REQUEST, uniqueResults(usernames), commandText);
    }

    /**
     * extracts arguments from a presumed correct send command
     * @param commandText - the validated string obtained int the commandFromText() method
     * @return - a command instance with the request body ready
     */
    private static Command parseSendMessageCommand(String commandText) {
        String[] splitString = commandText.split("send\\smessage\\s") ;
        if(splitString.length < 2) {
            return generateError("Empty message not permitted", commandText);
        }

        return new Command(CommandType.SEND_MESSAGE_TO_FRIENDS, Collections.singletonList(splitString[1]), commandText);
    }

    /**
     * matches a text with a CommandType using regex
     * @param commandText - a string that is expected to be produced by keyboard input
     * @return - a ERROR command or a valid command instance with the request body ready
     */
    public static Command commandFromText(String commandText) {
        if(commandText.matches(CommandType.LOGIN.getRegex())) {
            return parseLoginOrRegister(commandText, CommandType.LOGIN);
        }

        if(commandText.matches(CommandType.REGISTER.getRegex())) {
            return parseLoginOrRegister(commandText, CommandType.REGISTER);
        }

        if(commandText.matches(CommandType.SEND_FRIEND_REQUEST.getRegex())) {
            return parseFriendCommand(commandText);
        }

        if(commandText.matches(CommandType.READ_MESSAGES.getRegex())) {
            return new Command(CommandType.READ_MESSAGES, new ArrayList<>(), commandText);
        }

        if(commandText.matches(CommandType.EXIT.getRegex())) {
            return new Command(CommandType.EXIT, new ArrayList<>(), commandText);
        }

        if(commandText.matches(CommandType.SEND_MESSAGE_TO_FRIENDS.getRegex())) {
            return parseSendMessageCommand(commandText);
        }

        return generateError("Command unknown.", commandText);
    }

    /**
     * creates the request body JSON based on the command type and arguments
     * @return
     */
    private String createBodyRequest() {
        switch (this.commandType) {
            case LOGIN:
                return String.format("{\"command\": \"%s\", \"username\":\"%s\"}", CommandType.LOGIN.getCommandCode(), this.arguments.get(0));

            case REGISTER:
                return String.format("{\"command\": \"%s\", \"username\":\"%s\"}", CommandType.REGISTER.getCommandCode(), this.arguments.get(0));

            case SEND_FRIEND_REQUEST:
                return String.format("{\"command\": \"%s\", \"usernameList\": [%s]}",
                        CommandType.SEND_FRIEND_REQUEST.getCommandCode(),
                        this.arguments.stream().map(userString -> String.format("\"%s\"", userString)).collect(Collectors.joining(","))
                );

            case SEND_MESSAGE_TO_FRIENDS:
                return String.format("{\"command\": \"%s\", \"message\":\"%s\"}", CommandType.SEND_MESSAGE_TO_FRIENDS.getCommandCode(), this.arguments.get(0));

            case READ_MESSAGES:
                return String.format("{\"command\": \"%s\"}", CommandType.READ_MESSAGES.getCommandCode());

            case EXIT:
                return String.format("{\"command\": \"%s\"}", CommandType.EXIT.getCommandCode());
        }
        return "";
    }

    public static Command generateError(String message) {
        return new Command(CommandType.ERROR, Collections.singletonList(message), "");
    }

    /**
     * creates a error command (helps make the code easier to read, by not copying method body everywhere)
     * @param message
     * @param originalCommandText
     * @return
     */
    public static Command generateError(String message, String originalCommandText) {
        return new Command(CommandType.ERROR, Collections.singletonList(message), originalCommandText);
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandType=" + commandType +
                ", originalCommandText='" + originalCommandText + '\'' +
                ", arguments=[" + String.join("|", arguments) +"]"+
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
