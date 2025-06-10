package dev.sideproject.ndx2.entity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Document(collection = "review")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
     @Id
     @Field(name = "id")
     UUID id;
     @Field(name = "content")
     String content;
     @Field(name = "rating")
     Integer rating;
     @Field(name = "item_id")
     Integer itemId;
     @Field(name = "last_modified_at")
     LocalDateTime lastModifiedAt;
     @Field(name = "last_modified_by")
     Integer lastModifiedBy;
}
