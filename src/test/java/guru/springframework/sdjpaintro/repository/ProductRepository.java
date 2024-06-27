package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
