package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.*;
import guru.springframework.sdjpaintro.repository.CustomerRepository;
import guru.springframework.sdjpaintro.repository.OrderHeaderRepository;
import guru.springframework.sdjpaintro.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@ActiveProfiles(profiles = {"local"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataLoadTest {
    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";

    final String TEST_CUSTOMER = "TEST CUSTOMER";

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testDBLock() {
        Long id = 135L;
        OrderHeader orderHeader = orderHeaderRepository.findById(id).get();

        Address billTo = Address.builder()
                .address("Bill me")
                .build();
        orderHeader.setBillToAddress(billTo);
        // если откл autocommit в datagrip (workbench) и выполнить select for update для того же id,
        // то зависнем в этом месте до того момента пока datagrip не отпустит запись (commit/rollback)
        orderHeaderRepository.saveAndFlush(orderHeader);

        log.info("Order was updated");
    }

    @Test
    public void testNPlusOneProblem() {
        Customer customer = customerRepository.findCustomerByNameIgnoreCase(TEST_CUSTOMER).get();

        IntSummaryStatistics totalOrdered = orderHeaderRepository.findAllByCustomer(customer).stream()
                .flatMap(oh -> oh.getOrderLines().stream())
                .collect(Collectors.summarizingInt(OrderLine::getQuantityOrdered));

        log.info("Total ordered: {}", totalOrdered);
    }

    @Test
    public void testLazyVsEager() {
        OrderHeader oh = orderHeaderRepository.getReferenceById(135L);

        log.info("Order id is: {}", oh.getId());
        log.info("Customer name is: {}", oh.getCustomer().getName());

    }

    @Test
    @Disabled
    @Rollback(value = false)
    public void testDataLoader() {
        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 10;

        for (int i = 0; i < ordersToCreate; i++) {
            log.info("Creating order #: {}", i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();
    }

    private OrderHeader saveOrder(Customer customer, List<Product> products) {
        Random random = new Random();

        OrderHeader oh = OrderHeader.builder()
                .customer(customer)
                .build();

        products.forEach(product -> {
            OrderLine ol = OrderLine.builder()
                    .product(product)
                    .quantityOrdered(random.nextInt(20))
                    .build();
            oh.addOrderLine(ol);
        });

        return orderHeaderRepository.save(oh);
    }

    private Customer loadCustomers() {
        return getOrSaveCustomer(TEST_CUSTOMER);
    }

    private Customer getOrSaveCustomer(String customerName) {
        return customerRepository.findCustomerByNameIgnoreCase(customerName)
                .orElseGet(() -> {
                    Customer c1 = Customer.builder()
                            .name(customerName)
                            .email("test@example.com")
                            .address(Address.builder()
                                    .address("123 Main")
                                    .city("New Orleans")
                                    .state("LA")
                                    .build())
                            .build();
                    return customerRepository.save(c1);
                });
    }

    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));

        return products;
    }

    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                    Product p1 = Product.builder()
                            .description(description)
                            .productStatus(ProductStatus.NEW)
                            .build();
                    return productRepository.save(p1);
                });
    }
}
