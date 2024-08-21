package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.OrderHeader;
import guru.springframework.sdjpaintro.repository.OrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BootstrapOrderService {
    private final OrderHeaderRepository orderHeaderRepository;

    @Transactional
    public void readOrderData() {
        OrderHeader orderHeader = orderHeaderRepository.findById(135L).get();

        orderHeader.getOrderLines().forEach(ol -> {
            log.info(ol.getProduct().getDescription());
            ol.getProduct().getCategories().forEach(category -> {
                log.info(category.getDescription());
            });
        });
    }
}
