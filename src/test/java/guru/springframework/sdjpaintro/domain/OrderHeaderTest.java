package guru.springframework.sdjpaintro.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderHeaderTest {
    @Test
    public void testEquals() {
        OrderHeader oh1 = OrderHeader.builder().id(1L).build();
        OrderHeader oh2 = OrderHeader.builder().id(1L).build();

        assertThat(oh1).isEqualTo(oh2);
    }

    @Test
    public void testNotEquals() {
        OrderHeader oh1 = OrderHeader.builder().id(1L).build();
        OrderHeader oh2 = OrderHeader.builder().id(2L).build();

        assertThat(oh1).isNotEqualTo(oh2);
    }
}