package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByNameIgnoreCase(String customerName);
}
