package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderLine extends BaseEntity {
    private Integer quantityOrdered;

    @CreationTimestamp
    private Instant createdDate;

    @UpdateTimestamp
    private Instant lastModifiedDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderHeader orderHeader;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderLine orderLine = (OrderLine) o;
        return Objects.equals(getQuantityOrdered(), orderLine.getQuantityOrdered()) && Objects.equals(getCreatedDate(), orderLine.getCreatedDate()) && Objects.equals(getLastModifiedDate(), orderLine.getLastModifiedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getQuantityOrdered(), getCreatedDate(), getLastModifiedDate());
    }
}
