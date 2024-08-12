import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String FILE_PATH = "users.dat";
    private Map<String, User> information = new HashMap<>();

    public UserManager() {
        loadUsers();
    }

    public void addUser(String username, int age, String email, String bio, String password) {
        if (!information.containsKey(username)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, age, email, bio, hashedPassword);
            information.put(username, user);
            saveUsers();
        } else {
            System.out.println("User with username " + username + " already exists.");
        }
    }

    public boolean verifyPassword(String username, String inputPassword) {
        if (information.get(username) == null)
            return false;
        String storedHash = information.get(username).getPassword();
        if (username == null)
            return false;
        return BCrypt.checkpw(inputPassword, storedHash);
    }

    public User getUserInformation(String username, String inputPassword) {
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
        saveUsers();
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

    private void saveUsers() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(information);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            information = (Map<String, User>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            information = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printAllUsers() {
        if (information.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : information.values()) {
                System.out.println("Username: " + user.getName() + ", Email: " + user.getEmail() + ", To-do List: " + user.getTodoList());
            }
        }
    }
}
