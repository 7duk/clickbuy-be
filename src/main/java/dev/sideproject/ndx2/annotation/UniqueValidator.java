package dev.sideproject.ndx2.annotation;

import dev.sideproject.ndx2.SpringContextHolder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    EntityManager entityManager;

    Class<?> entityClass;
    String fieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.fieldName();
        this.entityManager = SpringContextHolder.getBean(EntityManager.class);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        String queryStr = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value",
                entityClass.getSimpleName(), fieldName);

        Long count = entityManager.createQuery(queryStr, Long.class)
                .setFlushMode(FlushModeType.COMMIT)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0;
    }
}

