package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.dao.BookDao;
import guru.springframework.sdjpaintro.domain.Book;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.dao"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {
    @Autowired
    @Qualifier("book_dao_impl")
    BookDao bookDao;

    @Test
    public void testFindAllBooksSorted() {
        Pageable pageable = PageRequest.of(2, 2, Sort.by(Sort.Order.desc("title")));
        List<Book> books = bookDao.findAllBooksSortByTitle(pageable);

        assertThat(books).size().isEqualTo(2);
    }

    @Test
    public void testFindAllWithPageable() {
        Pageable pageable = PageRequest.of(2, 2);
        List<Book> books = bookDao.findAllBooks(pageable);

        assertThat(books).size().isEqualTo(2);
    }

    @Test
    public void testFindAllByPage() {
        List<Book> books = bookDao.findAllBooks(2, 3);

        assertThat(books).size().isEqualTo(2);
    }

    @Test
    public void testFindAll() {
        List<Book> allBooks = bookDao.findAllBooks();

        assertThat(allBooks).size().isGreaterThan(0);
    }

    @Test
    public void testFindBookByISBN() {
        Book book = Book.builder()
                .isbn("1234" + RandomString.make())
                .title("ISBN TEST")
                .build();

        Book saved = bookDao.saveNewBook(book);

        Book fetched = bookDao.findByISBN(book.getIsbn());
        assertThat(fetched.getIsbn()).isEqualTo(saved.getIsbn());
    }

    @Test
    public void testFindById() {
        Book book = bookDao.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    public void testFindBookByTitle() {
        Book book = bookDao.findBookByTitle("java");
        assertThat(book).isNotNull();
    }

    @Test
    public void testSaveBook() {
        Book book = Book.builder()
                .isbn("123-456-789")
                .title("concurrency")
                .publisher("piter")
                .build();

        Book saved = bookDao.saveNewBook(book);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void testUpdateBook() {
        Book book = Book.builder()
                .isbn("222-333-444")
                .title("concurrency")
                .publisher("piter")
                .build();

        Book saved = bookDao.saveNewBook(book);
        saved.setTitle("concurrency in practice");
        Book updated = bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo(updated.getTitle());
    }

    @Test
    public void testDeleteBook() {
        Book book = Book.builder()
                .isbn("222-333-444")
                .title("concurrency")
                .publisher("piter")
                .build();
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());
        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched).isNull();
    }
}
