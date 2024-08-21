package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.OrderHeader;
import guru.springframework.sdjpaintro.repository.OrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Profile("local")
@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final OrderHeaderRepository orderHeaderRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        OrderHeader orderHeader = orderHeaderRepository.findById(135L).get();

        orderHeader.getOrderLines().forEach(ol -> {
            log.info(ol.getProduct().getDescription());
            ol.getProduct().getCategories().forEach(category -> {
                log.info(category.getDescription());
            });
        });
    }
}
