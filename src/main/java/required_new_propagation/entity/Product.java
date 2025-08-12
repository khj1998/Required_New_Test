package required_new_propagation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name",nullable = false,columnDefinition = "varchar(32)")
    private String name;

    @Column(name= "price",nullable = false)
    private BigDecimal price;

    private int stock;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getProductId() {
        return this.id;
    }

    public boolean isStockEnough(int quantity) {
        return stock >= quantity;
    }

    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }

    public BigDecimal getTotalAmount(int quantity,BigDecimal requestedPoint) {
        return this.price.multiply(BigDecimal.valueOf(quantity)).subtract(requestedPoint);
    }
}
