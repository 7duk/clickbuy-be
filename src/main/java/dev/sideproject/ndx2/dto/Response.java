package dev.sideproject.ndx2.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.SuperBuilder;


@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@SuperBuilder
public class Response {
    @Builder.Default
    String timestamp = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'GMT'XXX"));
    int code;
}
