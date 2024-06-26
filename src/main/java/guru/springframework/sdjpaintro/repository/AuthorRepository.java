package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findFirstByFirstNameAndLastName(String firstName, String lastName);

    Page<Author> findAllByLastName(String lastName, Pageable pageable);
}
