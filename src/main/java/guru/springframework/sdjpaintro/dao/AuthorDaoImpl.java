package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    @Autowired
    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        try (EntityManager em = getEntityManager()) {
            Query nativeQuery = em.createNativeQuery("SELECT * FROM author WHERE first_name = ? AND last_name = ?", Author.class);
            nativeQuery.setParameter(1, firstName);
            nativeQuery.setParameter(2, lastName);
            return (Author) nativeQuery.getSingleResult();
        }
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Author> cq = cb.createQuery(Author.class);

            Root<Author> root = cq.from(Author.class);

            ParameterExpression<String> firstNameParam = cb.parameter(String.class);
            ParameterExpression<String> lastNameParam = cb.parameter(String.class);

            Predicate firstNamePred = cb.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = cb.equal(root.get("lastName"), lastNameParam);

            cq.select(root).where(cb.and(firstNamePred, lastNamePred));

            TypedQuery<Author> typedQuery = em.createQuery(cq);
            typedQuery.setParameter(firstNameParam, firstName);
            typedQuery.setParameter(lastNameParam, lastName);
            return typedQuery.getSingleResult();
        }
    }

    @Override
    public List<Author> findAll() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Author> typedQuery = em.createNamedQuery("author_find_all", Author.class);
            return typedQuery.getResultList();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :lastName");
            query.setParameter("lastName", "%" + lastName + "%");
            List<Author> authors = query.getResultList();
            return authors;
        }
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Author> typedQuery = em.createNamedQuery("find_by_name", Author.class);
            typedQuery.setParameter("firstName", firstName);
            typedQuery.setParameter("lastName", lastName);
            return typedQuery.getSingleResult();
        }
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.getTransaction().commit();
        return author;
    }

    @Override
    public void updateAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(author);
        em.getTransaction().commit();
    }

    @Override
    public void deleteAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        author = em.find(Author.class, author.getId());
        em.remove(author);
        em.getTransaction().commit();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
