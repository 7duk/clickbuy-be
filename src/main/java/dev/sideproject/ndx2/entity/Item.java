package dev.sideproject.ndx2.entity;

import dev.sideproject.ndx2.entity.auditor.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "item")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false, length = 250)
    String name;

    @Column(name = "sell_price", nullable = false)
    Double sellPrice;

    @Column(name = "buy_price", nullable = false)
    Double buyPrice;

    @Column(name = "public_price")
    Double publicPrice;

    @Column(name = "discount")
    Integer discount;

    @Column(name = "amount", nullable = false)
    Integer amount;

    @Column(name = "rating", nullable = false)
    Double rating;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "item")
    List<ItemImage> imageItems;
}
