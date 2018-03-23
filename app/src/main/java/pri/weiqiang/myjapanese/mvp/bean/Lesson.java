package pri.weiqiang.myjapanese.mvp.bean;

/**
 * Created by weiqiang on 2018/3/18.
 */

public class Lesson {

    private String book;
    private String title;

    @Override
    public String toString() {
        return "Lesson{" +
                ", book=" + book +
                ", title=" + title +
                '}';
    }

    public Lesson(String book, String title) {
        this.book = book;
        this.title = title;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
