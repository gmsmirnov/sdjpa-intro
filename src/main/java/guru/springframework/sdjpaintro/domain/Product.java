package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseEntity {
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", columnDefinition = "varchar")
    private ProductStatus productStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Builder.Default
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(getDescription(), product.getDescription()) && getProductStatus() == product.getProductStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription(), getProductStatus());
    }
}
