package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@AttributeOverrides({
        @AttributeOverride(
                name = "shippingAddress.address",
                column = @Column(name = "shipping_address")
        ),
        @AttributeOverride(
                name = "shippingAddress.city",
                column = @Column(name = "shipping_city")
        ),
        @AttributeOverride(
                name = "shippingAddress.state",
                column = @Column(name = "shipping_state")
        ),
        @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")
        ),
        @AttributeOverride(
                name = "billToAddress.address",
                column = @Column(name = "bill_to_address")
        ),
        @AttributeOverride(
                name = "billToAddress.city",
                column = @Column(name = "bill_to_city")
        ),
        @AttributeOverride(
                name = "billToAddress.state",
                column = @Column(name = "bill_to_state")
        ),
        @AttributeOverride(
                name = "billToAddress.zipCode",
                column = @Column(name = "bill_to_zip_code")
        )

})
public class OrderHeader extends BaseEntity {
    private String customer;

    @Embedded
    private Address shippingAddress;

    @Embedded
    private Address billToAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", columnDefinition = "varchar")
    private OrderStatus orderStatus;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "orderHeader",
            fetch = FetchType.LAZY)
    private List<OrderLine> orderLines = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdDate;

    @UpdateTimestamp
    private Instant lastModifiedDate;

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.setOrderHeader(null);
    }

    public void removeAllOrderLines() {
        Iterator<OrderLine> iterator = orderLines.iterator();
        while (iterator.hasNext()) {
            OrderLine next = iterator.next();
            next.setOrderHeader(null);
            iterator.remove();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderHeader that = (OrderHeader) o;
        return Objects.equals(getCustomer(), that.getCustomer()) && Objects.equals(getShippingAddress(), that.getShippingAddress()) && Objects.equals(getBillToAddress(), that.getBillToAddress()) && getOrderStatus() == that.getOrderStatus() && Objects.equals(getCreatedDate(), that.getCreatedDate()) && Objects.equals(getLastModifiedDate(), that.getLastModifiedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomer(), getShippingAddress(), getBillToAddress(), getOrderStatus(), getCreatedDate(), getLastModifiedDate());
    }
}
