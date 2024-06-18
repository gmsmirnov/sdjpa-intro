package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.Address;
import guru.springframework.sdjpaintro.domain.OrderHeader;
import guru.springframework.sdjpaintro.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static guru.springframework.sdjpaintro.domain.OrderStatus.NEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"local"})
class OrderHeaderRepositoryTest {
    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Test
    public void testSaveEmbeddable() {
        OrderHeader oh = OrderHeader.builder()
                .customer("New customer")
                .shippingAddress(Address.builder()
                        .address("shipping address")
                        .build())
                .billToAddress(Address.builder()
                        .address("bill to address")
                        .build())
                .build();

        OrderHeader saved = orderHeaderRepository.save(oh);

        Optional<OrderHeader> maybeFetched = orderHeaderRepository.findById(saved.getId());

        assertThat(maybeFetched.isPresent()).isTrue();
    }

    @Test
    public void testSaveOrderHeader() {
        OrderHeader oh = OrderHeader.builder()
                .customer("New customer")
                .orderStatus(NEW)
                .shippingAddress(Address.builder()
                        .address("shipping address")
                        .build())
                .billToAddress(Address.builder()
                        .address("bill to address")
                        .build())
                .build();

        OrderHeader saved = orderHeaderRepository.save(oh);

        Optional<OrderHeader> maybeFetched = orderHeaderRepository.findById(saved.getId());

        assertThat(maybeFetched.isPresent()).isTrue();
        OrderHeader fetched = maybeFetched.get();
        assertThat(fetched.getOrderStatus()).isEqualTo(NEW);
        assertThat(fetched.getCreatedDate()).isNotNull();
        assertThat(fetched.getLastModifiedDate()).isNotNull();
    }
}