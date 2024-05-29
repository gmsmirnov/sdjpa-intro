package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitle(String title);

    Book readByTitle(String title);

    @Nullable
    Book getByTitle(@Nullable String title);

    Stream<Book> findAllByTitleNotNull();

    Future<Book> queryByTitle(String title);

    @Query(value = "SELECT b FROM Book b WHERE b.title = :title")
    Book findBookByTitleWithQuery(@Param(value = "title") String title);

    @Query(value = "SELECT * FROM bookdb.book WHERE title = :title", nativeQuery = true)
    Book findBookByTitleNativeQuery(@Param("title") String title);

    Book jpaNamed(@Param("title") String title);
}
