import org.mindrot.jbcrypt.BCrypt;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {
    private static final String USERS_FILE_PATH = "database/users/users.dat";
    private static final String NOTES_FILE_EXTENSION = ".notes";
    private static final String TODO_FILE_EXTENSION = ".todo";
    private Map<String, User> information = new HashMap<>();

    public UserManager() {
        loadUsers();
    }

    public boolean addUser(String username, int age, String email, String bio, String password) {
        if (!information.containsKey(username)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, age, email, bio, hashedPassword);
            information.put(username, user);
            saveUsers();
            saveUserData(username);
            return true;
        }
        return false;
    }

    public boolean verifyPassword(String username, String inputPassword) {
        if (information.get(username) == null) return false;
        String storedHash = information.get(username).getPassword();
        return storedHash != null && BCrypt.checkpw(inputPassword, storedHash);
    }

    public User getUserInformation(String username, String inputPassword) {
        if (inputPassword != null && verifyPassword(username, inputPassword)) {
            try {
                User clonedUser = (User) information.get(username).clone();
                loadUserData(username, clonedUser);
                return clonedUser;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void removeUser(String username) {
        information.remove(username);
        deleteUserFiles(username);
        saveUsers();
    }

    public boolean updateUserEmail(String username, String newEmail) {
        User user = information.get(username);
        if (user != null) {
            user.setEmail(newEmail);
            saveUsers();
            return true;
        }
        return false;
    }

    public boolean updateBio(String username, String newBio) {
        User user = information.get(username);
        if (user != null) {
            user.setBio(newBio);
            saveUsers();
            return true;
        }
        return false;
    }

    public boolean addNote(String username, Note item) {
        User user = information.get(username);
        if (user != null) {
            user.addNoteItem(item);
            saveUsers();
            return true;
        }
        return false;
    }

    public boolean removeNote(String username, Note item) {
        User user = information.get(username);
        if (user != null) {
            user.removeNoteItem(item);
            saveUsers();
            return true;
        }
        return false;
    }

    public boolean addTodo(String username, String item) {
        User user = information.get(username);
        if (user != null) {
            user.addTodoItem(item);
            saveUserData(username);
            return true;
        }
        return false;
    }

    public boolean removeTodo(String username, String item) {
        User user = information.get(username);
        if (user != null) {
            user.removeTodoItem(item);
            saveUserData(username);
            return true;
        }
        return false;
    }

    private void saveUsers() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(USERS_FILE_PATH))) {
            outputStream.writeObject(information);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(USERS_FILE_PATH))) {
            information = (Map<String, User>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            information = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveUserData(String username) {
        saveListToFile(information.get(username).getNotes(), "database/" + username + NOTES_FILE_EXTENSION);
        saveListToFile(information.get(username).getTodoList(), "database/" + username + TODO_FILE_EXTENSION);
    }

    private void loadUserData(String username, User user) {
        user.getNotes().clear();
        user.getTodoList().clear();
        user.getNotes().addAll(loadListFromFile("database/" + username + NOTES_FILE_EXTENSION));
        user.getTodoList().addAll(loadListFromFile("database/" + username + TODO_FILE_EXTENSION));
    }


    private <T> void saveListToFile(List<T> list, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> List<T> loadListFromFile(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void deleteUserFiles(String username) {
        new File(username + NOTES_FILE_EXTENSION).delete();
        new File(username + TODO_FILE_EXTENSION).delete();
    }

    public void printAllUsers() {
        if (information.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : information.values()) {
                System.out.println("Username: " + user.getName() + ", Email: " + user.getEmail() + ", To-do List: " + user.getTodoList() + ", Notes: " + user.getNotes());
            }
        }
    }
}
