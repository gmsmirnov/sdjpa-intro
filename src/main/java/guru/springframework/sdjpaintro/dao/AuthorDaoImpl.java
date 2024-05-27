package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        return null;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void updateAuthor(Author author) {
        Author fetched = authorRepository.getReferenceById(author.getId());
        fetched.setFirstName(author.getFirstName());
        fetched.setLastName(author.getLastName());
        authorRepository.save(fetched);
    }

    @Override
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }
}
