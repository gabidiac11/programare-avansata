package pa.lab10.server;

import lombok.Getter;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * holds user info and identification
 * can create JSON string representation of a instance
 */
@Getter
public class User {
    private String userName;
    private List<User> friends;
    private List<String> messages;

    public User(String userName) {
        this.userName = userName;
        this.messages = new Vector<>();
        this.friends = new Vector<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }


    /**
     * instance to JSON string
     * avoids creating nested structure (for the friends instances)
     * @return
     */
    @Override
    public String toString() {
        JSONObject jo = new JSONObject();
        jo.put("messages", this.messages);
        jo.put("friends", Arrays.asList(this.friends.stream().map(User::toStringAvoidNesting).toArray()));
        jo.put("username", this.userName);

        return JSONObject.toJSONString(jo);
    }

    /**
     * helps avoid un-necessary deep structure
     * @return
     */
    public String toStringAvoidNesting() {
        JSONObject jo = new JSONObject();
        jo.put("username", this.userName);

        return JSONObject.toJSONString(jo);
    }
}
