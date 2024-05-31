package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Book;
import org.h2.mvstore.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoJDBCTemplateTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    BookDao bookDao;

    @BeforeEach
    public void setup() {
        bookDao = new BookDaoJDBCTemplate(jdbcTemplate);
    }

    @Test
    public void findAllBooksSorted() {
        Pageable pageable = PageRequest.of(2, 2, Sort.by(Sort.Order.desc("title")));
        List<Book> books = bookDao.findAllBooksSortByTitle(pageable);

        assertThat(books).size().isEqualTo(2);
    }

    @Test
    public void testFindAllBooksWithPageable() {
        PageRequest pageable = PageRequest.of(2, 2);
        List<Book> books = bookDao.findAllBooks(pageable);

        assertThat(books).size().isEqualTo(2);
    }

    @Test
    public void testFindAllBooksByPage() {
        List<Book> books = bookDao.findAllBooks(2, 3);

        assertThat(books).size().isEqualTo(2);
    }

    @Test
    public void testFindAllEmptyList() {
        List<Book> books = bookDao.findAllBooks(100, 100);

        assertThat(books).isEmpty();
    }

    @Test
    public void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks();

        assertThat(books).size().isGreaterThan(0);
    }

    @Test
    public void getById() {
        Book book = bookDao.getById(1L);

        assertThat(book).isNotNull();
    }

    @Test
    public void findBookByTitle() {
        Book book = bookDao.findBookByTitle("effective java");

        assertThat(book).isNotNull();
    }

    @Test
    public void saveNewBook() {
        Book book = Book.builder()
                .isbn("123456789")
                .title("test book")
                .publisher("apress")
                .build();

        Book saved = bookDao.saveNewBook(book);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void updateBook() {
        Book book = Book.builder()
                .isbn("123456789")
                .title("test book")
                .publisher("apress")
                .build();

        Book saved = bookDao.saveNewBook(book);
        saved.setPublisher("piter");
        Book updated = bookDao.updateBook(saved);

        assertThat(updated.getPublisher()).isEqualTo("piter");
    }

    @Test
    public void deleteBook() {
        Book book = Book.builder()
                .isbn("123456789")
                .title("test book")
                .publisher("apress")
                .build();

        Book saved = bookDao.saveNewBook(book);
        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched).isNotNull();

        bookDao.deleteBookById(saved.getId());

        assertThrows(DataAccessException.class, () -> bookDao.getById(saved.getId()));
    }
}