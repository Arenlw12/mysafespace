import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Dashboard extends JFrame {
    private List<String> notes;
    private JPanel notesDisplayPanel;
    private UserManager userManager;
    private User user;

    public Dashboard(UserManager userManager, User user) {
        this.userManager = userManager;
        this.user = user;
        notes = user.getNotes();

        setTitle("Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel notesPanel = createNotesPanel();

        JPanel todoPanel = new JPanel();

        JPanel personalPanel = new JPanel();

        tabbedPane.addTab("Notes", notesPanel);
        tabbedPane.addTab("Todo", todoPanel);
        tabbedPane.addTab("Personal", personalPanel);

        add(tabbedPane, BorderLayout.CENTER);

        loadNotes();

        setVisible(true);
    }

    private JPanel createNotesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        notesDisplayPanel = new JPanel();
        notesDisplayPanel.setLayout(new BoxLayout(notesDisplayPanel, BoxLayout.Y_AXIS));
        notesDisplayPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane notesScrollPane = new JScrollPane(notesDisplayPanel);

        notesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        notesScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));

        panel.add(notesScrollPane, BorderLayout.CENTER);

        JTextField noteTitleField = new JTextField();
        ((AbstractDocument) noteTitleField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (fb.getDocument().getLength() + text.length() - length <= 63) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= 63) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        });

        JTextArea newNoteArea = new JTextArea(3, 20);
        newNoteArea.setLineWrap(true);
        newNoteArea.setWrapStyleWord(true);
        JScrollPane newNoteScrollPane = new JScrollPane(newNoteArea);

        newNoteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newNoteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JButton addNoteButton = new JButton("Add Note");
        addNoteButton.addActionListener(e -> {
            String title = noteTitleField.getText().trim();
            String note = newNoteArea.getText().trim();
            if (!note.isEmpty()) {
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                notes.add(note);
                userManager.addNote(user.getName(), note);
                notesDisplayPanel.add(createNoteComponent(title, note, dateTime));
                noteTitleField.setText("");
                newNoteArea.setText("");
                notesDisplayPanel.revalidate();
                notesDisplayPanel.repaint();
            }
        });

        JPanel newNotePanel = new JPanel();
        newNotePanel.setLayout(new BorderLayout());
        newNotePanel.add(noteTitleField, BorderLayout.NORTH);
        newNotePanel.add(newNoteScrollPane, BorderLayout.CENTER);
        newNotePanel.add(addNoteButton, BorderLayout.EAST);

        panel.add(newNotePanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createNoteComponent(String title, String note, String dateTime) {
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        notePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleAbove = new JLabel(title.isEmpty() ? "Untitled" : title);
        titleAbove.setFont(new Font("Roboto", Font.BOLD, 20));

        titlePanel.add(titleAbove, BorderLayout.WEST);
        titlePanel.setBorder(new EmptyBorder(4, 0, 4, 0));

        JLabel date = new JLabel(dateTime);
        titlePanel.add(date, BorderLayout.EAST);
        date.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 30));

        JTextArea noteText = new JTextArea(note);
        noteText.setLineWrap(true);
        noteText.setWrapStyleWord(true);
        noteText.setEditable(false);
        noteText.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        notePanel.add(titlePanel, BorderLayout.NORTH);
        notePanel.add(noteText, BorderLayout.CENTER);

        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setPreferredSize(new Dimension(30, 30));
        deleteButton.setBorder(new LineBorder(Color.BLACK));
        deleteButton.addActionListener(e -> {
            notes.remove(note);
            userManager.removeNote(user.getName(), note);
            notesDisplayPanel.remove(notePanel);
            notesDisplayPanel.revalidate();
            notesDisplayPanel.repaint();
        });

        notePanel.add(deleteButton, BorderLayout.EAST);
        notesDisplayPanel.add(Box.createVerticalStrut(5));

        return notePanel;
    }

    private void loadNotes() {
        for (String note : notes) {
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            notesDisplayPanel.add(createNoteComponent("Untitled", note, dateTime));
        }
        notesDisplayPanel.revalidate();
        notesDisplayPanel.repaint();
    }
}
