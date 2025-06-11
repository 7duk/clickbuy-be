package dev.sideproject.ndx2.specifications;

import dev.sideproject.ndx2.entity.Item;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemSpecs {
    public static Specification<Item> getItemSpecification(Map<String, Object> filters, String comparison, Long price) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (comparison != null && price != null) {
                    switch (comparison.toLowerCase()) {
                        case "less_than":
                            predicates.add(criteriaBuilder.lessThan(root.get("publicPrice"), price));
                             break;
                        case "greater_than":
                            predicates.add(criteriaBuilder.greaterThan(root.get("publicPrice"), price));
                             break;
                    }
                }

                filters.forEach((k, v) -> {
                    if (k.equals("category")) {
                        if (!v.toString().contains(",")) {
                            predicates.add(criteriaBuilder.equal(root.get(k).get("id"), v));
                        } else {
                            List<Integer> categoryIds = Arrays.stream(v.toString().split(","))
                                    .map(String::trim)
                                    .map(Integer::valueOf)
                                    .toList();
                            predicates.add(root.get(k).get("id").in(categoryIds));
                        }
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(k), v));
                    }
                });
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
