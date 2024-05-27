package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
