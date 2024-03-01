public class Book {
    private static int nextId = 1;

    private int id;
    private String titleBook;
    private String author;
    private String publishedYear;
    private String authorActive;
    private boolean status;

    public Book(String titleBook, String author, String publishedYear, String authorActive, boolean status) {
        this.id = nextId++;
        this.titleBook = titleBook;
        this.author = author;
        this.publishedYear = publishedYear;
        this.authorActive = authorActive;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public String getAuthorActive() {
        return authorActive;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
