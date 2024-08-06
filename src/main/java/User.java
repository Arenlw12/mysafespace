import java.util.*;

public class User implements Cloneable {
    private String name;
    private int age;
    private String email;
    private String bio;
    private String password;
    private List<String> notes;
    private List<String> todo;

    public User(String name, int age, String email, String bio, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.notes = new ArrayList<>();
        this.todo = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getNotes() {
        return new ArrayList<>(notes);
    }

    public void addNoteItem(String item) {
        this.notes.add(item);
    }

    public void removeNoteItem(String item) {
        this.notes.remove(item);
    }

    public List<String> getTodoList() {
        return new ArrayList<>(todo);
    }

    public void addTodoItem(String item) {
        this.todo.add(item);
    }

    public void removeTodoItem(String item) {
        this.todo.remove(item);
    }

    protected Object clone() throws CloneNotSupportedException {
        User cloned = (User) super.clone();
        cloned.notes = new ArrayList<>(this.notes);
        cloned.todo = new ArrayList<>(this.todo);
        return cloned;
    }
}
