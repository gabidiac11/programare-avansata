package pa.lab10.server;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ThreadProvider {
    private List<User> allUsers;
    private boolean isExiting = false;

    public ThreadProvider() {
        allUsers = new Vector<>();
    }

    public synchronized List<User> getAllUsers() {
        return allUsers;
    }

    public synchronized void addUser(User user) {
        this.allUsers.add(user);
    }

    public synchronized boolean getIsExiting() {
        return this.isExiting;
    }

    public synchronized void startExiting() {
        this.isExiting = true;
    }
}
