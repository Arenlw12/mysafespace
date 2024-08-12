public class Main {
    public static void main(String[] args) {

        StartPanel st = new StartPanel();
        st.setVisible(true);
        UserManager userManager = st.getUserManager();
        userManager.printAllUsers();
    }
}
