import javax.swing.*;
import java.awt.*;

public class StartPanel extends JFrame {
    private boolean switchRegistration = true;
    private final JPanel loginPanel;
    private JTextField userField;
    private JPasswordField passField;

    private JTextField ageField;
    private JTextField emailField;
    private JTextArea bioField;
    private final UserManager userManager = new UserManager();

    public UserManager getUserManager() {
        return userManager;
    }

    public StartPanel() {

        setTitle("MySafeSpace");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        JLabel appNameLabel = new JLabel("MySafeSpace", SwingConstants.CENTER);
        appNameLabel.setFont(new Font("Roboto", Font.BOLD, 26));
        titlePanel.add(appNameLabel);
        add(titlePanel, BorderLayout.NORTH);

        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        loginPanel.add(Box.createVerticalGlue(), gbc);

        add(loginPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            switchRegistration = true;
            validateLoginFields();
            showLoginFields();
        });

        registerButton.addActionListener(e -> {
            if (switchRegistration) {
                showRegistrationFields();
                switchRegistration = false;
            }
            else {
                validateRegistrationFields();
            }
        });
    }

    private void validateLoginFields() {
        boolean valid = true;
        switchRegistration = false;

        userField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        passField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("PasswordField.border"));

        if (userField.getText().isEmpty()) {
            userField.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        if (passField.getPassword().length == 0) {
            passField.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }

        if (valid) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (userManager.verifyPassword(username, password))
                JOptionPane.showMessageDialog(StartPanel.this, "Login successful!");

            else
                JOptionPane.showMessageDialog(StartPanel.this, "Wrong username, or password.", "Error", JOptionPane.ERROR_MESSAGE);

            revalidate();
            repaint();
        } else if (!switchRegistration) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            switchRegistration = true;
        }

    }

    private void validateRegistrationFields() {
        boolean valid = true;

        userField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        passField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("PasswordField.border"));
        ageField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        emailField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        bioField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextArea.border"));

        if (userField.getText().isEmpty()) {
            userField.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        if (passField.getPassword().length == 0) {
            passField.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        if (ageField.getText().isEmpty()) {
            ageField.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        if (emailField.getText().isEmpty()) {
            emailField.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        if (bioField.getText().isEmpty()) {
            bioField.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }

        if (valid) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            int age = Integer.parseInt(ageField.getText());
            String email = emailField.getText();
            String bio = bioField.getText();

            userManager.addUser(username, age, email, bio, password);
            JOptionPane.showMessageDialog(this, "Registration successful!");

            revalidate();
            repaint();
        } else
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);

    }


    private void showRegistrationFields() {
        loginPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel bioLabel = new JLabel("Bio:");

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        ageField = new JTextField(15);
        emailField = new JTextField(15);
        bioField = new JTextArea(3, 15);
        bioField.setLineWrap(true);
        bioField.setWrapStyleWord(true);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(bioLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(new JScrollPane(bioField), gbc);

        loginPanel.revalidate();
        loginPanel.repaint();
    }

    private void showLoginFields() {
        loginPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        loginPanel.add(Box.createVerticalGlue(), gbc);

        loginPanel.revalidate();
        loginPanel.repaint();

    }
}
