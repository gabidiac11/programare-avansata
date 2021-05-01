package pa.lab10.server;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyPair;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

class ClientThread implements Runnable {
    private Socket socket = null;
    private User user = null;
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

                if(threadProvider.getIsExiting()) {
                    this.sendResponse(generateResponse(410 , "Server unavailable."));
                    continue;
                }

                String response = generateResponse(400, "Parse error object: " + request);
                JSONObject jsonObject = parseRequest(request);
                if(jsonObject != null) {
                    this.sendResponse(this.user == null ? this.handleUnLoggedUserRequest(jsonObject) : this.handleLoggedUserRequest(jsonObject));
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

    private void sendResponse(String response) throws IOException {
        // Send the response to the output stream: server → client
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

    private String handleUnLoggedUserRequest(JSONObject jsonObject) {
        if(jsonObject.get("command").equals(ThreadCommandType.LOGIN.getCommandCode())) {
            return this.login(jsonObject);
        }
        if(jsonObject.get("command").equals(ThreadCommandType.REGISTER.getCommandCode())) {
            return this.register(jsonObject);
        }
        return generateResponse(400, "Unknown command.");
    }

    private synchronized String handleLoggedUserRequest(JSONObject jsonObject) {
        ThreadCommandType threadCommandType = commandFromRequest((String) jsonObject.get("command"));

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

    private synchronized String sendMessageToFriends(JSONObject jsonObject) {
        try {
            if(this.user.getFriends().size() == 0) {
                return generateResponse(400, "You have no friends. Go cry yourself to sleep.");
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

    private static ThreadCommandType commandFromRequest(String commandRequest) {
        try {
            for(ThreadCommandType command : ThreadCommandType.values()) {
                if(command.getCommandCode().equals(commandRequest)) {
                    return command;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while parsing request:2: " + e.getMessage());
        }

        return null;
    }

    private String login(JSONObject jsonObject) {
        if(jsonObject.get("username") != null) {
            try {
                String userName = (String) jsonObject.get("username");

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


    private static String generateResponse(int errorCode, String message) {
        return String.format("{ \"code\": %d, \"message\": \"%s\" }", errorCode, message);
    }

    private static String generateResponse(int errorCode) {
        return String.format("{ \"code\": %d }", errorCode);
    }

    private static String bodyResponse(String body) {
        return String.format("{ \"code\": 200, \"data\": %s}", body);
    }
}
