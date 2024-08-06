public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.addUser("user1", 19, "user1@example.com", "OMG SO COOl", "believer123");
        User user = userManager.getUserInformation("user1","believer123");
        System.out.println(user.getAge() + " " + user.getBio() + user.getPassword() + userManager.verifyPassword("user1","believer123"));
    }
}
