package pa.lab10.client;

import junit.framework.TestCase;
import org.junit.Test;

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