package literatura.literatura.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.stereotype.Service;

import literatura.literatura.models.Book;

@Service
public class BookRepository {
    @Autowired
    private JdbcTemplateAutoConfiguration jdbcTemplate;
    public void insertBook(Book book) {
        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
        ((Object) jdbcTemplate).update(sql, book.getTitle(), book.getAuthor());
    }
}