package dev.sideproject.ndx.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse extends Response {
    String message;
}
