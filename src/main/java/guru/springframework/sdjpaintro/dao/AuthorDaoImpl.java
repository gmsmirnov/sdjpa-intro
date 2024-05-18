package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    @Autowired
    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = getEntityManager().createQuery(
                "SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName",
                Author.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public void updateAuthor(Author author) {
    }

    @Override
    public void deleteAuthor(Author author) {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
