package pa.lab10.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * binds to a server
 * reads command from keyboard:
 *  -> sends requests as JSON string to the server
 *  -> displays errors on the screen if the commands are invalid (the validation is done by the Command class)
 *
 * receives responses from the server:
 * -> if status code is 200 OK -> based on the request the user is logged in or updated using data parsed from JSON, from the server
 * -> else displays the error response from the server
 */
public class Client {
    private static final int PORT = 8100;
    private static final String serverAddress = "127.0.0.1"; // The server's IP address

    private User user = null;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start () throws IOException {
        socket = null;
        try {
            Socket socket = new Socket(serverAddress, PORT);

            while(true) {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader (new InputStreamReader(socket.getInputStream()));

                /*
                 * read from keyboard while the typed command text is NOT valid (meaning no request was sent to the server)
                 * (a command is invalid if is not matched by a regex or tries to do things that a un-logged user can not do)
                 */
                Command commandError;
                do {
                    System.out.println("\nPlease type a command:");

                    commandError = this.receiveAndExecuteCommands();
                    if(commandError.getCommandType() == CommandType.ERROR) {
                        System.out.println("Command error: " + commandError.getArguments().get(0));
                    }

                } while(commandError.getCommandType() == CommandType.ERROR);

            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }

    /**
     * @param request - the string received from the server
     * @return - a JSON object
     */
    private static JSONObject parseResponse(String request) {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(request);
            if(jsonObject.get("code") != null) {
                return jsonObject;
            }
        } catch (Exception e) {
            System.out.println("Exception while parsing response:2: ");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * sends a pre-cooked request body JSON (made in Command constructor)
     * displays the response
     *
     * if is OK, it parses the response and assigns the logged user
     * @param command
     * @return
     * @throws IOException
     */
    private Command login(Command command) throws IOException {
        if(user != null) {
            return Command.generateError("An user is already logged in.", command.getOriginalCommandText());
        }

        // Send a request to the server
        out.println(command.getRequestBody());

        // Wait the response from the server ("Hello World!")
        String response = in.readLine();
        JSONObject jsonObject = parseResponse(response);
        if(jsonObject != null && jsonObject.get("code").toString().equals("200")) {
            this.user = userInstanceFromResponse(jsonObject);
            if(this.user == null) {
                return Command.generateError("Failed to parse response from server: " + response, command.getOriginalCommandText());
            }

            System.out.println("User logged in");
        } else {
            printBadResponse(jsonObject, response, command);
            handleEventualExitResponse(jsonObject);
        }

        return command;
    }

    /**
     * exits if the server says is in the process of exiting
     * @param jsonObject
     */
    private void handleEventualExitResponse(JSONObject jsonObject) {
        if(jsonObject != null && jsonObject.get("code").toString().equals("410")) {
            System.out.println("Server unavailable");
            System.exit(1);
        }
    }

    /**
     * prints a bad response from the server (as a consequence of a refused or unrecognised request)
     * it also shows the payload sent
     * @param jsonObject - JSON object of the response
     * @param response - the actual string received (based on which the above parameter is obtained)
     * @param command - the command used for sending this request
     */
    private void printBadResponse(JSONObject jsonObject, String response, Command command) {
        if(jsonObject == null) {
            System.out.println("Bad response from server: 'null'");
        } else {
            String message = "";
            if(jsonObject.get("message") != null) {
                message = jsonObject.get("message").toString();
            }
            System.out.printf("Bad response from server: '%s'\n", message);
        }

        System.out.println("Raw: " + response);
        System.out.println("RequestBody: " + command.getRequestBody() + "\n");
    }

    /**
     * sends a pre-cooked request body JSON (made in Command constructor)
     * displays the response from the server
     *
     * @param command
     * @return
     * @throws IOException
     */
    private Command register(Command command) throws IOException {
        if(user != null) {
            return Command.generateError("An user is already logged in.", command.getOriginalCommandText());
        }

        // Send a request to the server
        out.println(command.getRequestBody());

        // Wait the response from the server ("Hello World!")
        String response = in.readLine();
        JSONObject jsonObject = parseResponse(response);
        if(jsonObject != null && jsonObject.get("code").toString().equals("200")) {
            System.out.println("User registered.");
        } else {
            printBadResponse(jsonObject, response, command);
        }

        return command;
    }

    /**
     * sends a pre-cooked request body JSON (made in Command constructor)
     * expects a JSON string with the user data if the response is OK, which uses to update the current instance
     * @param command
     * @return
     * @throws IOException
     */
    private Command sendRequestAndUpdateUser(Command command) throws IOException {
        if(user == null) {
            return Command.generateError("You must be logged in.", command.getOriginalCommandText());
        }

        // Send a request to the server
        out.println(command.getRequestBody());

        // Wait the response from the server ("Hello World!")
        String response = in.readLine();

        JSONObject jsonObject = parseResponse(response);
        if(jsonObject != null && jsonObject.get("code").toString().equals("200")) {
            User updatedUser = userInstanceFromResponse(jsonObject);
            if(updatedUser == null) {
                return Command.generateError("Failed to parse response from server.", command.getOriginalCommandText());
            }

            user.setFriends(updatedUser.getFriends());
            user.setMessages(updatedUser.getMessages());
        } else {
            printBadResponse(jsonObject, response, command);
            handleEventualExitResponse(jsonObject);
        }

        return command;
    }

    private User userInstanceFromResponse(JSONObject jsonObject) {
        try {
            User newUser = User.instanceFromJson((JSONObject) jsonObject.get("data"));

            return newUser;
        } catch (Exception exception) {
            System.out.println("Error while parsing response:1:");
            exception.printStackTrace();
        }

        return null;
    }

    private String readFromKeyboard() throws IOException {
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        return reader.readLine();
    }

    /**
     * validates a typed command
     * sends a JSON string request to the server if command is valid
     * returns a ERROR type command instance (with a message attached) if no request was made because of an invalid command text
     * (a command is invalid if is not matched by a regex or tries to do things that a un-logged user can not do)
     * @return - the Command instance used for the sent request, or a ERROR command signaling a invalid command
     * @throws IOException
     */
    private Command receiveAndExecuteCommands() throws IOException {
        Command command = Command.commandFromText(this.readFromKeyboard());

        if(command.getCommandType() == CommandType.LOGIN) {
            return this.login(command);
        }

        if(command.getCommandType() == CommandType.REGISTER) {
            return this.register(command);
        }

        /*
         * these actions implies that the response from the server will include a JSON string used for the updating the current user (with messages, friends)
         */
        if( command.getCommandType() == CommandType.SEND_FRIEND_REQUEST ||
            command.getCommandType() == CommandType.SEND_MESSAGE_TO_FRIENDS ||
            command.getCommandType() == CommandType.READ_MESSAGES ||
            command.getCommandType() == CommandType.EXIT
        ) {
            Command commandStatus = this.sendRequestAndUpdateUser(command);

            if(commandStatus.getCommandType() != CommandType.ERROR) {
                System.out.println("Request fulfilled.");

                if(command.getCommandType() == CommandType.READ_MESSAGES) {
                    this.user.printMessages();
                }
            }

            return commandStatus;
        }

        return command;
    }
}
