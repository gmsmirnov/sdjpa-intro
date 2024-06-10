package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
