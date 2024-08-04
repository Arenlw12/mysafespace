import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.addUser("user1", "19", "user1@example.com", "OMG SO COOl", "believer123");
        userManager.addUser("user1", "19", "user1@example.com", "OMG SO COOl", "abad");
        userManager.addUser("user2", "19", "user4@example.com", "OMG SO COO24l", "believer1213133");

        userManager.getUserInformation("user1", "believer123");

        userManager.removeUser("user1");
        userManager.removeUser("user2");
    }
}
