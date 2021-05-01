package pa.lab10.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userName;
    private List<User> friends;
    private List<String> messages;

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

    public static User instanceFromJson(JSONObject jsonObject) {
        try {
            List<String> messages = new Vector<>();
            if(jsonObject.get("messages") != null) {
                JSONArray jsonArrayMessages = (JSONArray) jsonObject.get("messages");
                jsonArrayMessages.forEach(item -> messages.add((String) item));
            }

            List<User> friends = new Vector<>();
            if(jsonObject.get("friends") != null) {
                JSONArray jsonArrayUsers = (JSONArray) jsonObject.get("friends");
                jsonArrayUsers.forEach(item -> {
                    try {
                        friends.add(User.instanceFromJson((JSONObject) new JSONParser().parse(item.toString())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            }

            return new User(jsonObject.get("username").toString(), friends, messages);
        } catch (Exception e) {
            System.out.println("Exception while parsing request: ");
            e.printStackTrace();
        }

        return null;
    }

    public void printMessages() {
        System.out.println("My messages:");
        for(String message : getMessages()) {
            System.out.println(message);
        }
        System.out.println("------------");
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", friends=[" + friends.stream().map(User::toString).collect(Collectors.joining(",\n")) +
                "], messages=[" + String.join(",", messages) +
                "]}";
    }
}
