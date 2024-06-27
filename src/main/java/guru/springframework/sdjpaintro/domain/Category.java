package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
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
public class Category extends BaseEntity {
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @Builder.Default
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();

    @CreationTimestamp
    private Instant createdDate;

    @UpdateTimestamp
    private Instant lastModifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(getDescription(), category.getDescription()) && Objects.equals(getCreatedDate(), category.getCreatedDate()) && Objects.equals(getLastModifiedDate(), category.getLastModifiedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription(), getCreatedDate(), getLastModifiedDate());
    }
}
