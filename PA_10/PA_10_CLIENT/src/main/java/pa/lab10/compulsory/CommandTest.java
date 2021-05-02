package pa.lab10.compulsory;

import pa.lab10.client.Command;

public class CommandTest {

    public static void main(String[] args) {
        System.out.println(Command.commandFromText("login ion2").toString());
        System.out.println(Command.commandFromText("friend ").toString());
        System.out.println(Command.commandFromText("friend salut salut salut3").toString());
        System.out.println(Command.commandFromText("read").toString());
        System.out.println(Command.commandFromText("send message ").toString());
        System.out.println(Command.commandFromText("send message send message").toString());
    }
}
