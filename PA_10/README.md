
# Lab 10
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

### Compusory

#### - Create the project for the server application.
#### - Implement the class responsible with the creation of a ServerSocket running at a specified port. The server will receive requests (commands) from clients and it will execute them.
#### - Create a class that will be responsible with communicating with a client Socket. The communication will be on a separate thread. If the server receives the command stop it will stop and will return to the client the respons "Server stopped", otherwise it return: "Server received the request ... ".
#### - Create the project for the client application.
#### - A client will read commands from the keyboard and it will send them to the server. The client stops when it reads from the keyboard the string "exit".


   I've created separated projects, one for the server and one for the client. 
##### SERVER 
   The connections are expected in a while loop. When a connection is made a new thread is created, of ClientThread. Each thread receives the ThreadProvider instance which is shared with all other threads that are created when a connection is accepted. The ThreadProvider instance holds all the instances of User class, represeting the users that were created by clients so far (using register command). 
   
This ThereadProvider instance also signals other threads (and the Server class itself) that the server is in the proccess of exiting, so at that point each client waiting will receive a response which signals that the server is unavailable. The client reads the response and stops its execution if is evaluated as such. By  exiting, the client stops its conterpart thread in the server part. 

The while loop where new connections are accepted runs as long as the ThreadProvider doesn't signals an exit. The accept method has a timeout in order to frequently engage with the loop condition, to check if an exit is needed (the while needs to stop).

Each thread has associated a User instance, meaning the client connected to that thread is logged in with that specific user. 

The server and client comunicates usign JSON strings. The server thread always includes a status code, with the same values and meaning as normal HTTP request model. The client parses the response and always checks for a 200 OK response before going forward with an action. 

The server is design to always accompany a bad response with a message of what's wrong (Ex. username already used, a parse error has occured, etc). 
The flow for almost all requests implies:

  1. an update done to instances from ThreadProvider's list of users (Ex. an user gets a new friend) 
  
  3. a response containing the updated user instance in the form of a JSON string. This string is used by the client to update it's own User instance, then go ahead with the action disired (for example: display messages)
 
##### CLIENT
The client, after establishing the connection, reads in a while loop a command from keyboad. This proccess relies havily on Command class, which is task with parsing the text.

Each command (send message, read, friend, login, register) has a regex associated and a numeric-string code recognised by the server. All of this is put together in the CommandType enum. 
Command class has a method that receives a text and matches that to one of the enum types, using the regex. Then, it extracts the expected arguments and pre-creates a JSON string to be used for the future request body.

If the text doesn't match to the expected enum types or has wrong arguments, a special kind of Command instance is made to signal an error. 
This type of command has attached an error message that will be visible to the actual user typing, in the console. In that case no request is made to the server and the user is advised to type again a correct command text.

Because of the delicate nature of this class, I wrote a small unit test to have an overview of most of the scenarios:

````java
public class CommandTest extends TestCase {

    private Command create(String text) {
        return Command.commandFromText(text);
    }

    @Test
    public void testCommandFromText() {
        assertEquals(create("login ion2").getCommandType(), CommandType.LOGIN);

        assertEquals(create("friend ").getCommandType(), CommandType.ERROR);

        assertEquals(create("friend salut salut salut3").getCommandType(), CommandType.SEND_FRIEND_REQUEST);

        /* test if the usernames are uniquely pushed to the arguments list ('salut' must occur just once) */
        assertEquals(create("friend salut salut salut3").getArguments().size(), 2);

        assertEquals(create("send message ").getCommandType(), CommandType.ERROR);
        assertEquals(create("send message ").getArguments().get(0).equals("Empty message not permitted"), true);

        assertEquals(create("send message send message").getCommandType(), CommandType.SEND_MESSAGE_TO_FRIENDS);

        /*
         * test if the contents extracted form this command are correct ("send message send message" -> the content of the message should be 'send message')
         */
        assertEquals(create("send message send message").getArguments().get(0).equals("send message"), true);


        assertEquals(create("read").getCommandType(), CommandType.READ_MESSAGES);
    }
}
````

##### EXEMPLE VIDEO

![https://github.com/gabidiac11/programare-avansata/blob/main/PA_10/exemple-console.mp4](https://github.com/gabidiac11/programare-avansata/blob/main/PA_10/exemple-console.mp4)

##### Server verbose
[![N|Solid](https://github.com/gabidiac11/programare-avansata/blob/main/PA_10/server-output.PNG)](https://github.com/gabidiac11/programare-avansata/blob/main/PA_10/server-output.PNG)
