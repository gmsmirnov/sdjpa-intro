package guru.springframework.sdjpaintro.repository;

import guru.springframework.sdjpaintro.domain.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = Product.builder()
                .description("test product")
                .productStatus(ProductStatus.NEW)
                .build();

        product = productRepository.saveAndFlush(newProduct);
    }

    @Test
    public void testDeleteCascade() {
        OrderHeader oh = OrderHeader.builder()
                .customer("New customer")
                .build();

        OrderLine ol = OrderLine.builder()
                .quantityOrdered(5)
                .build();

        oh.addOrderLine(ol);

        OrderHeader saved = orderHeaderRepository.saveAndFlush(oh);

        orderHeaderRepository.deleteById(saved.getId());
        orderHeaderRepository.flush();

        assertThrows(EntityNotFoundException.class, () -> {
            OrderHeader fetched = orderHeaderRepository.getReferenceById(saved.getId());
            fetched.getOrderLines(); // to throw EntityNotFoundException.class
        });
    }

    @Test
    public void testSaveOrderWithProduct() {
        OrderHeader oh = OrderHeader.builder()
                .customer("New customer")
                .shippingAddress(Address.builder()
                        .address("shipping address")
                        .build())
                .billToAddress(Address.builder()
                        .address("bill to address")
                        .build())
                .build();

        OrderLine ol = OrderLine.builder()
                .quantityOrdered(5)
                .product(product)
                .build();

        OrderApproval approval = OrderApproval.builder()
                .approvedBy("me")
                .build();

        OrderApproval savedApproval = orderApprovalRepository.save(approval);

        oh.setOrderApproval(savedApproval);

        oh.addOrderLine(ol);

        OrderHeader saved = orderHeaderRepository.save(oh);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertThat(saved.getOrderLines()).size().isEqualTo(1);
    }

    @Test
    public void testSaveOrderWithLine() {
        OrderHeader oh = OrderHeader.builder()
                .customer("New customer")
                .shippingAddress(Address.builder()
                        .address("shipping address")
                        .build())
                .billToAddress(Address.builder()
                        .address("bill to address")
                        .build())
                .build();

        OrderLine ol = OrderLine.builder()
                .quantityOrdered(5)
                .build();

        oh.addOrderLine(ol);

        OrderHeader saved = orderHeaderRepository.save(oh);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertThat(saved.getOrderLines()).size().isEqualTo(1);
    }

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