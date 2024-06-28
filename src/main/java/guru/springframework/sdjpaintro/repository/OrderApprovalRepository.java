package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
