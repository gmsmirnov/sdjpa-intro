package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {
    private final EntityManagerFactory emf;

    @Autowired
    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        try (EntityManager em = getEntityManager()) {
            String hql = "SELECT b FROM Book b ORDER BY b.title "
                    + pageable.getSort().getOrderFor("title").getDirection().name();
            TypedQuery<Book> query = em.createQuery(hql, Book.class);
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize);
            return query.getResultList();
        }
    }

    @Override
    public List<Book> findAllBooks() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        }
    }

    @Override
    public Book findByISBN(String isbn) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.isbn LIKE :isbn ORDER BY b.id LIMIT 1", Book.class);
            query.setParameter("isbn", "%" + isbn + "%");
            return query.getSingleResult();
        }
    }

    @Override
    public Book getById(Long id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Book.class, id);
        }
    }

    @Override
    public Book findBookByTitle(String title) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title ORDER BY b.id LIMIT 1", Book.class);
            query.setParameter("title", "%" + title + "%");
            return query.getSingleResult();
        }
    }

    @Override
    public Book saveNewBook(Book book) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
        }
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();
            return book;
        }
    }

    @Override
    public void deleteBookById(Long id) {
        try (EntityManager em = getEntityManager()) {
            Book book = em.find(Book.class, id);
            em.getTransaction().begin();
            em.remove(book);
            em.getTransaction().commit();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
