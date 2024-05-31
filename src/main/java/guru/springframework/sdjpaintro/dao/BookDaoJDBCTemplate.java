package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BookDaoJDBCTemplate implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM bookdb.book LIMIT ? OFFSET ?", getBookMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT * FROM bookdb.book LIMIT ? OFFSET ?", getBookMapper(), pageSize, offset);
    }

    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query("SELECT * FROM bookdb.book", getBookMapper());
    }

    @Override
    public Book findByISBN(String isbn) {
        return null;
    }

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM bookdb.book WHERE id = ?", getBookMapper(), id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM bookdb.book WHERE title = ?", getBookMapper(), title);
    }

    @Override
    public Book saveNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO bookdb.book (isbn, publisher, title, author_id) VALUE (?, ?, ?, ?)",
                book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());
        Long id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);
        book.setId(id);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update("UPDATE bookdb.book SET isbn = ?, publisher = ?, title = ?, author_id = ? WHERE id = ?",
                book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId(), book.getId());
        return getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM bookdb.book WHERE id = ?", id);
    }

    private BookMapper getBookMapper() {
        return new BookMapper();
    }
}
