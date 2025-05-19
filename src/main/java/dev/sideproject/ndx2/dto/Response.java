package dev.sideproject.ndx2.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class Response {
    LocalDateTime time;
    int code;
}
