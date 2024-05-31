package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Book;
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
class BookDaoJPAImplTest {

    @Autowired
    @Qualifier("book_dao_jpa")
    BookDao bookDao;

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
}