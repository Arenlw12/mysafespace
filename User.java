import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class User implements Cloneable {
    private String name;
    private String age;
    private String email;
    private String bio;
    private String password;

    public User(String name, String age, String email, String bio, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.bio = bio;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
