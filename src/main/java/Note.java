import java.io.Serializable;

public class Note implements Serializable {
    private final String title;
    private final String content;
    private final String timestamp;

    public Note(String title, String content, String timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
