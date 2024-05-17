package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

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
    public void testSaveAuthor() {
        // jdbc template rollbacks (transaction) while plain jdbc doesn't
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

        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(id));
    }
}
