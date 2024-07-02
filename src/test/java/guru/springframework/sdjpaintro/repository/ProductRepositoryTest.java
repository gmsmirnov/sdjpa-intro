package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = {"local"})
@ComponentScan(basePackages = "guru.springframework.sdjpaintro.dao")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testGetCategory() {
        Product product = productRepository.findByDescription("PRODUCT1").orElseThrow();

        assertNotNull(product);
        assertThat(product.getCategories()).size().isGreaterThan(1);
    }
}