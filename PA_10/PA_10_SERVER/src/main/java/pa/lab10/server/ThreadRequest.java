package pa.lab10.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ThreadRequest {
    private ThreadCommandType commandType;
    private User sender;
    private List<User> receivers;
    private String message;

    public ThreadRequest(ThreadCommandType commandType, User sender, List<User> receivers, String message) {
        this.commandType = commandType;
        this.sender = sender;
        this.receivers = receivers;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ThreadRequest{" +
                "commandType=" + commandType +
                ", sender=" + sender +
                ", receivers=" + (receivers != null ? receivers.stream().map(User::toString).collect(Collectors.joining(",")) + "--->" + receivers.size() : "") +
                ", message='" + message + '\'' +
                '}';
    }
}
