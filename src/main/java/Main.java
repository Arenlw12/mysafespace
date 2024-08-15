public class Main {
    public static void main(String[] args) {
        StartPanel st = new StartPanel();
//        st.setVisible(true);
        UserManager userManager = st.getUserManager();
        userManager.addUser("Af",19,"lkjflasfla@gmail.com", "lkadjsflkasdjflkasdlfsla", "believer123");
        Dashboard d = new Dashboard(userManager,userManager.getUserInformation("Af","believer123"));
        d.setVisible(true);
        userManager.printAllUsers();
    }
}
