package pa.lab10.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client {
    private User user = null;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start () throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        socket = null;

        try {
            Socket socket = new Socket(serverAddress, PORT);

            while(true) {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader (new InputStreamReader(socket.getInputStream()));

                Command commandError;
                do {
                    System.out.println("\nPlease type a command:");

                    commandError = this.receiveAndExecuteCommands();
                    if(commandError.getCommandType() == ClientCommandType.ERROR) {
                        System.out.println("Command error: " + commandError.getArguments().get(0));
                    }

                } while(commandError.getCommandType() == ClientCommandType.ERROR);

            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }

    private static JSONObject parseResponse(String request) {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(request);
            if(jsonObject.get("code") != null) {

                if(jsonObject.get("code").toString().equals("410")) {
                    System.out.println("Server unavailable");
                    System.exit(1);
                }

                return jsonObject;
            }
        } catch (Exception e) {
            System.out.println("Exception while parsing response:2: ");
            e.printStackTrace();
        }

        return null;
    }

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
            System.out.println("Bad response from server: " + response + "\nRequestBody: " + command.getRequestBody() + "\n");
        }

        return command;
    }

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
            System.out.println("Bad response from server: " + response + "\nRequestBody: " + command.getRequestBody() + "\n");
        }

        return command;
    }



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

            System.out.println("Friend requests sent. User:\n" + user.toString());
        } else {
            System.out.println("Bad response from server: " + response + "\nRequestBody: " + command.getRequestBody() + "\n");
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


    private Command receiveAndExecuteCommands() throws IOException {
        Command command = Command.commandFromText(this.readFromKeyboard());

        if(command.getCommandType() == ClientCommandType.LOGIN) {
            return this.login(command);
        }

        if(command.getCommandType() == ClientCommandType.REGISTER) {
            return this.register(command);
        }

        if( command.getCommandType() == ClientCommandType.SEND_FRIEND_REQUEST ||
            command.getCommandType() == ClientCommandType.SEND_MESSAGE_TO_FRIENDS ||
            command.getCommandType() == ClientCommandType.READ_MESSAGES ||
            command.getCommandType() == ClientCommandType.EXIT
        ) {
            Command commandStatus = this.sendRequestAndUpdateUser(command);

            if(commandStatus.getCommandType() != ClientCommandType.ERROR && command.getCommandType() == ClientCommandType.READ_MESSAGES) {
                this.user.printMessages();
            }

            return commandStatus;
        }

        return command;
    }
}
