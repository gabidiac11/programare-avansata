package pa.lab10.server;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ThreadProvider {
    private List<User> allUsers;
    private List<ThreadRequest> threadRequests;
    private boolean isExiting = false;

    public ThreadProvider() {
        allUsers = new Vector<>();
        threadRequests = new Vector<>();
    }

    public synchronized List<User> getAllUsers() {
        return allUsers;
    }

    public synchronized void addUser(User user) {
        this.allUsers.add(user);
    }

    public synchronized void addThreadRequest(ThreadRequest threadRequest) {
        this.threadRequests.add(threadRequest);
    }

    public synchronized ThreadRequest getFulFilledRequestOfUser(User user) {
        for(ThreadRequest threadRequest : threadRequests) {
            if(threadRequest.getSender().getUserName().equals(user.getUserName())
            && threadRequest.getReceivers().size() == 0
            ) {
                return threadRequest;
            }
        }
        return null;
    }

    public synchronized void filterOutSenderRequests(User user) {
        this.threadRequests = threadRequests.stream().filter(item -> item.getSender().getUserName().equals(user.getUserName())).collect(Collectors.toList());
    }

    public synchronized ThreadRequest popThreadRequestOfReceiver(User receiver) {
        for(int i = 0; i < this.threadRequests.size(); i++) {
            for(int ii = 0; ii < this.threadRequests.get(i).getReceivers().size(); ii++) {
                if(threadRequests.get(i).getReceivers().get(ii).getUserName().equals(receiver.getUserName())) {
                    threadRequests.get(i).getReceivers().remove(ii);
                    return threadRequests.get(i);
                }
            }
        }

        return null;
    }
    public synchronized boolean getIsExiting() {
        return this.isExiting;
    }

    public synchronized void startExiting() {
        this.isExiting = true;
    }

    @Override
    public String toString() {
        return "ThreadProvider{" +
                "\n, threadRequests=" + threadRequests.stream().map(item -> item.toString()).collect(Collectors.joining("\n")) +
                "\n}";
    }
}
