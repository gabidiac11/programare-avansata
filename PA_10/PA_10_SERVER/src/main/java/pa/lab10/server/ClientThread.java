package pa.lab10.server;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * - has a threadProvider that holds all users and signals the threads to stop their activity
 * - receives requests from a connection
 * - requests are handled and expected as JSON strings
 *
 * - responds with the JSON string:
 *      -> register a user (adds new instance to threadProvider)
 *      -> login a user (attach one of these instances to this thread)
 *      -> for the rest of requests it responds with the User instance as a JSON string, that will be used to update or instantiate the clients own User instance
 *          (this happens for the following requests: send messages, friends requests, read messages)
 *
 */
class ClientThread implements Runnable {
    private final Socket socket;

    /**
     * a non-null user means a logged user attached to this thread
     * a user is logged if at least one thread has the user instance with the given username
     */
    private User user = null;

    /**
     * holds all users and signals the threads to stop their activity
     */
    private final ThreadProvider threadProvider;

    public ClientThread (Socket socket, ThreadProvider threadProvider) throws SocketException {
        this.socket = socket;
        this.socket.setSoTimeout(1000 * 60 * 5); //5 minute timeout
        this.threadProvider = threadProvider;
    }

    @SneakyThrows
    public void run () {
        try {
            while (true) {
                // Get the request from the input stream: client → server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();

                /*
                 * if the server is in the process of exiting -> respond with 410 code so the client will stop, thus triggering this server to stop
                 */
                if(threadProvider.getIsExiting()) {
                    this.sendResponse(generateResponse(410 , "Server unavailable."));
                    continue;
                }

                /*
                 * parse request from client, which is expected to be a JSON string
                 * if the parse fails, signal that
                 * if not, this responds with JSON string based on the authentication status of the user attached to this thread
                 */
                String response = generateResponse(400, "Parse error object: " + request);
                JSONObject jsonObject = parseRequest(request);
                if(jsonObject != null) {
                    this.sendResponse(this.user == null
                            ? this.handleUnLoggedUserRequest(jsonObject)
                            : this.handleLoggedUserRequest(jsonObject));
                } else {
                    this.sendResponse(response);
                }
            }

        } catch (IOException e) {

            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }

    /**
     * Send the response to the output stream: server → client
     * @param response - JSON string
     * @throws IOException
     */
    private void sendResponse(String response) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(response);

        System.out.printf("\nResponse sent to %s:\n%s\n\n", this.user != null ? this.user.getUserName() : "unlogged", response);
        out.flush();
    }

    private static JSONObject parseRequest(String request) {
        try {
            Object obj = new JSONParser().parse(request);
            JSONObject jsonObject = (JSONObject) obj;

            if(jsonObject.get("command") != null) {
                return jsonObject;
            }
        } catch (Exception e) {
            System.out.println("Exception while parsing request: " + e.getMessage());
        }

        return null;
    }

    /**
     * handle requests permitted only if the user is NOT logged in
     * if request is not permitted send the default response with a 'BAD REQUEST' html inspired code
     *
     * @param jsonObject - the request from client (a parsed JSON)
     * @return - JSON string -> containing the status
     */
    private String handleUnLoggedUserRequest(JSONObject jsonObject) {
        if(jsonObject.get("command").equals(CommandType.LOGIN.getCommandCode())) {
            return this.login(jsonObject);
        }
        if(jsonObject.get("command").equals(CommandType.REGISTER.getCommandCode())) {
            return this.register(jsonObject);
        }
        return generateResponse(400, "Unknown command.");
    }

    /**
     * chooses what actions to go about, based on te request body
     *
     * @param jsonObject - the request from client (a parsed JSON)
     * @return - JSON string, the response
     */
    private synchronized String handleLoggedUserRequest(JSONObject jsonObject) {
        CommandType threadCommandType = commandFromRequest((String) jsonObject.get("command"));

        if(threadCommandType != null) {
            switch (threadCommandType) {
                case SEND_FRIEND_REQUEST:
                    return this.sendFriendRequest(jsonObject);
                case SEND_MESSAGE_TO_FRIENDS:
                    return this.sendMessageToFriends(jsonObject);
                case READ_MESSAGES:
                    return bodyResponse(this.user.toString());
                case EXIT:
                    this.threadProvider.startExiting();
                    return generateResponse(410 , "Server unavailable.");
            }
        }

        return generateResponse(400, "Unknown command.");
    }

    private synchronized String sendFriendRequest(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("usernameList");

            List<User> userList = new Vector<>();
            jsonArray.forEach(userName -> {
                for(User userItem : threadProvider.getAllUsers()) {
                    if(userItem.getUserName().equals(userName) && !this.user.getUserName().equals(userItem.getUserName())
                    && this.user.getFriends().stream().filter(item -> item.getUserName().equals(userName)).toArray().length == 0
                    ) {
                        userList.add(userItem);
                        userItem.getFriends().add(this.user);
                        this.user.getFriends().add(userItem);
                        break;
                    }
                }
            });

            if(userList.size() == 0) {
                return generateResponse(400, "No user from your list exists.");
            }

            return bodyResponse(this.user.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return generateResponse(400, "Bad user list.");
        }

    }


    /**
     * adds message to an user matched by username
     * the client is notified is no user is found
     *
     * @param jsonObject - the request from client (a parsed JSON)
     * @return - JSON string, containing a JSON representation of the user, so it could be parsed by the client (serves to sync the user instance from the server with the one that is hold by the client)
     */
    private synchronized String sendMessageToFriends(JSONObject jsonObject) {
        try {
            if(this.user.getFriends().size() == 0) {
                return generateResponse(400, "You have no friends.");
            }

            if(jsonObject.get("message") == null) {
                return generateResponse(400, "Message can not be empty.");
            }

            String message = jsonObject.get("message").toString();

            for(User userItem : user.getFriends()) {
                userItem.getMessages().add(String.format("<<From '%s'>>: %s | At %s", this.user.getUserName(), message, new Date().toString()));
            }

            return bodyResponse(this.user.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return generateResponse(400, "Bad user list.");
        }
    }

    /**
     * matches a pre-established command codes (known by the server and the client respectively)
     * these codes signals what the client is requesting from the server (login, register, send message)
     * these codes are mapped to CommandType enum
     *
     * @param commandRequest - a numeric string
     * @return - CommandType enum
     */
    private static CommandType commandFromRequest(String commandRequest) {
        try {
            for(CommandType command : CommandType.values()) {
                if(command.getCommandCode().equals(commandRequest)) {
                    return command;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while parsing request:2: " + e.getMessage());
        }

        return null;
    }

    /**
     * matches the username send by the client with an exiting one from the ThreadProvider instance
     * if the instance is found -> this ClientThread instance gets attached an user
     *
     * @param jsonObject - the request from client (a parsed JSON)
     * @return - JSON string, containing a JSON representation of the user, so it could be parsed by the client
     */
    private String login(JSONObject jsonObject) {
        if(jsonObject.get("username") != null) {
            try {
                String userName = (String) jsonObject.get("username");

                /* verify if it exists an user with the same username */
                List<User> results = threadProvider.getAllUsers().stream().filter(item -> item.getUserName().equals(userName)).collect(Collectors.toList());
                if(results.size() == 0) {
                    return generateResponse(400, "User with this name does not exits");
                }

                this.user = results.get(0);

                return bodyResponse(this.user.toString());
            } catch (Exception e) {
                System.out.println("Exception at parsing:1:" + e.getMessage());

                return generateResponse(500, "Internal error");
            }
        } else {
            return generateResponse(400, "Empty username");
        }
    }

    /**
     * creates the username send by the client by pushing a new one to the list from ThreadProvider instance
     * the client is notified if is trying to add an user with a taken username
     *
     * @param jsonObject - the request from client (a parsed JSON)
     * @return - JSON string, html like
     */
    private String register(JSONObject jsonObject) {
        if(jsonObject.get("username") != null) {
            try {
                String userName = (String) jsonObject.get("username");

                if(threadProvider.getAllUsers().stream().anyMatch(item -> item.getUserName().equals(userName))) {
                    return generateResponse(400, "User name already in use");
                }

                threadProvider.addUser(new User(userName));

                return generateResponse(200, "The user has been created.");
            } catch (Exception e) {
                System.out.println("Exception at parsing:1:" + e.getMessage());

                return generateResponse(500, "Internal error");
            }
        } else {
            return generateResponse(400, "Empty username");
        }
    }

    /**
     * @param errorCode - a HTML like code -> 200 OK, 400 bad request, etc.
     * @param message - a additional message. Because this function is mainly used for bad request, is serving for informing the user what's the problem
     * @return - JSON string used for generating responses, that can be parsed by the client
     */
    private static String generateResponse(int errorCode, String message) {
        return String.format("{ \"code\": %d, \"message\": \"%s\" }", errorCode, message);
    }

    /**
     * creates OK responses, having the 200 code prefilled
     * @param body - a JSON string
     * @return - JSON string used for generating responses, that can be parsed by the client
     */
    private static String bodyResponse(String body) {
        return String.format("{ \"code\": 200, \"data\": %s}", body);
    }
}
