public class Main {
    public static void main(String[] args) {
        StartPanel st = new StartPanel();
//        st.setVisible(true);
        UserManager userManager = st.getUserManager();
        Dashboard d = new Dashboard(userManager,userManager.getUserInformation("Aren","believer123"));
        d.setVisible(true);
        userManager.printAllUsers();
    }
}
