package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
        return authorRepository.findAllByLastName(lastName, pageable).getContent();
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
        return authorRepository.findFirstByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
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
