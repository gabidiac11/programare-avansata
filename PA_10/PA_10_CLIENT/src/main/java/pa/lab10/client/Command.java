package pa.lab10.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Command {
    private ClientCommandType commandType;
    private List<String> arguments;
    private String originalCommandText;
    private String requestBody;

    public Command(ClientCommandType commandType, List<String> arguments, String originalCommandText) {
        this.commandType = commandType;
        this.arguments = arguments;
        this.originalCommandText = originalCommandText;
        this.requestBody = this.createBodyRequest();
    }

    private static Command parseLoginOrRegister(String commandText, ClientCommandType commandType) {
        String[] splitString = commandType.equals(ClientCommandType.LOGIN) ? commandText.split("login\\s") : commandText.split("register\\s");
        if(splitString.length < 2) {
            return generateError("Command text parse error.", commandText);
        }

        return new Command(commandType, Collections.singletonList(splitString[1]), commandText);
    }

    private static List<String> uniqueResults(List<String> list) {
        List<String> results = new ArrayList<>();
        for(String listItem : list) {
            if(results.stream().filter(listItem::equals).toArray().length == 0) {
                results.add(listItem);
            }
        }
        return results;
    }

    private static Command parseFriendCommand(String commandText) {
        String[] splitString = commandText.split("friend\\s") ;
        if(splitString.length < 2) {
            return generateError("Friend have no usernames.", commandText);
        }

        List<String> usernames = Arrays.stream(splitString[1].split("\\s"))
                .filter(item -> !item.isEmpty() && !item.isBlank())
                .collect(Collectors.toList());

        return new Command(ClientCommandType.SEND_FRIEND_REQUEST, uniqueResults(usernames), commandText);
    }

    private static Command parseSendMessageCommand(String commandText) {
        String[] splitString = commandText.split("send\\smessage\\s") ;
        if(splitString.length < 2) {
            return generateError("Empty message not permitted", commandText);
        }

        return new Command(ClientCommandType.SEND_MESSAGE_TO_FRIENDS, Collections.singletonList(splitString[1]), commandText);
    }

    public static Command commandFromText(String commandText) {
        if(commandText.matches(ClientCommandType.LOGIN.getRegex())) {
            return parseLoginOrRegister(commandText, ClientCommandType.LOGIN);
        }

        if(commandText.matches(ClientCommandType.REGISTER.getRegex())) {
            return parseLoginOrRegister(commandText, ClientCommandType.REGISTER);
        }

        if(commandText.matches(ClientCommandType.SEND_FRIEND_REQUEST.getRegex())) {
            return parseFriendCommand(commandText);
        }

        if(commandText.matches(ClientCommandType.READ_MESSAGES.getRegex())) {
            return new Command(ClientCommandType.READ_MESSAGES, new ArrayList<>(), commandText);
        }

        if(commandText.matches(ClientCommandType.EXIT.getRegex())) {
            return new Command(ClientCommandType.EXIT, new ArrayList<>(), commandText);
        }

        if(commandText.matches(ClientCommandType.SEND_MESSAGE_TO_FRIENDS.getRegex())) {
            return parseSendMessageCommand(commandText);
        }

        return generateError("Command unknown.", commandText);
    }

    private String createBodyRequest() {
        switch (this.commandType) {
            case LOGIN:
                return String.format("{\"command\": \"%s\", \"username\":\"%s\"}", ClientCommandType.LOGIN.getCommandCode(), this.arguments.get(0));

            case REGISTER:
                return String.format("{\"command\": \"%s\", \"username\":\"%s\"}", ClientCommandType.REGISTER.getCommandCode(), this.arguments.get(0));

            case SEND_FRIEND_REQUEST:
                return String.format("{\"command\": \"%s\", \"usernameList\": [%s]}",
                        ClientCommandType.SEND_FRIEND_REQUEST.getCommandCode(),
                        this.arguments.stream().map(userString -> String.format("\"%s\"", userString)).collect(Collectors.joining(","))
                );

            case SEND_MESSAGE_TO_FRIENDS:
                return String.format("{\"command\": \"%s\", \"message\":\"%s\"}", ClientCommandType.SEND_MESSAGE_TO_FRIENDS.getCommandCode(), this.arguments.get(0));

            case READ_MESSAGES:
                return String.format("{\"command\": \"%s\"}", ClientCommandType.READ_MESSAGES.getCommandCode());

            case EXIT:
                return String.format("{\"command\": \"%s\"}", ClientCommandType.EXIT.getCommandCode());
        }
        return "";
    }

    public static Command generateError(String message) {
        return new Command(ClientCommandType.ERROR, Collections.singletonList(message), "");
    }

    public static Command generateError(String message, String originalCommandText) {
        return new Command(ClientCommandType.ERROR, Collections.singletonList(message), originalCommandText);
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
