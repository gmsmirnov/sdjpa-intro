package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorDao {
    List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable);

    Author findAuthorByNameNative(String firstName, String lastName);

    Author findAuthorByNameCriteria(String firstName, String lastName);

    List<Author> findAll();

    List<Author> listAuthorByLastNameLike(String lastName);

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Author author);
}
