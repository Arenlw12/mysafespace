import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String, User> information = new HashMap<>();

    public void addUser(String username, String age, String email, String bio, String password) {
        if (!information.containsKey(username)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, age, email, bio, hashedPassword);
            information.put(username, user);
        } else {
            System.out.println("User with username " + username + " already exists.");
        }
    }

    public boolean verifyPassword(String username, String inputPassword) {
        String storedHash = information.get(username).getPassword();
        if (username == null)
            return false;
        return BCrypt.checkpw(inputPassword, storedHash);
    }

    public User getUserInformation(String username, String inputPassword){
        if (inputPassword != null && verifyPassword(username, inputPassword)) {
            try {
                return (User) information.get(username).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void removeUser(String username) {
        information.remove(username);
    }

    public boolean updateUserEmail(String username, String newEmail) {
        User user = information.get(username);
        if (user != null) {
            user.setEmail(newEmail);
            return true;
        }
        return false;
    }

    public boolean updateBio(String username, String newBio) {
        User user = information.get(username);
        if (user != null) {
            user.setBio(newBio);
            return true;
        }
        return false;
    }
    public boolean addNote(String username, String item) {
        User user = information.get(username);
        if (user != null) {
            user.addNoteItem(item);
            return true;
        }
        return false;
    }

    public boolean removeNote(String username, String item) {
        User user = information.get(username);
        if (user != null) {
            user.removeNoteItem(item);
            return true;
        }
        return false;
    }

    public boolean addTodo(String username, String item) {
        User user = information.get(username);
        if (user != null) {
            user.addTodoItem(item);
            return true;
        }
        return false;
    }

    public boolean removeTodo(String username, String item) {
        User user = information.get(username);
        if (user != null) {
            user.removeTodoItem(item);
            return true;
        }
        return false;
    }
}
