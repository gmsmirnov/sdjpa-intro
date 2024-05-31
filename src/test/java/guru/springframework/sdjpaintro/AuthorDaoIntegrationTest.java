package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.dao"})
//@Import(AuthorDaoImpl.class) // если @ComponentScan не работает в тесте (не работал в старой версии SpringBoot, теперь исправили)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    public void findAllAuthorsByLastName() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("martin", PageRequest.of(0, 3));

        assertThat(authors).isNotNull();
        assertThat(authors).size().isEqualTo(3);
    }

//    @Test
//    public void testGetAuthorByNameNative() {
//        Author author = authorDao.findAuthorByNameNative("josh", "bloch");
//
//        assertThat(author).isNotNull();
//    }
//
//    @Test
//    public void testGetAuthorByNameCriteria() {
//        Author author = authorDao.findAuthorByNameCriteria("josh", "bloch");
//
//        assertThat(author).isNotNull();
//    }

    @Test
    public void testFindAllAuthors() {
        List<Author> authors = authorDao.findAll();

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

//    @Test
//    public void testListAuthorByLastNameLike() {
//        List<Author> authors = authorDao.listAuthorByLastNameLike("loc");
//
//        assertThat(authors).isNotNull();
//        assertThat(authors).size().isGreaterThan(0);
//    }

    @Test
    public void testGetAuthor() {
        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    public void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("josh", "bloch");

        assertThat(author).isNotNull();
    }

    @Test
    public void testGetAuthorByName2() {
        assertThrows(EntityNotFoundException.class, () -> authorDao.findAuthorByName("jo", "blo"));
    }

    @Test
    public void testSaveAuthor() {
        Author author = Author.builder()
                .firstName("Rob")
                .lastName("Martin")
                .build();
        author = authorDao.saveNewAuthor(author);

        assertThat(author.getId()).isNotNull();
    }

    @Test
    public void testUpdateAuthor() {
        Author author = Author.builder()
                .firstName("rob")
                .lastName("m")
                .build();

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("martin");
        authorDao.updateAuthor(saved);

        Author updated = authorDao.getById(saved.getId());

        assertThat(updated.getLastName()).isEqualTo("martin");
    }

    @Test
    public void testDeleteAuthor() {
        Author author = Author.builder()
                .firstName("rob")
                .lastName("martin")
                .build();
        author = authorDao.saveNewAuthor(author);
        assertThat(author.getId()).isNotNull();

        authorDao.deleteAuthor(author);
        Long id = author.getId();

        assertThrows(JpaObjectRetrievalFailureException.class, () -> authorDao.getById(id));
    }
}
