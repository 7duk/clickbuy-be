package dev.sideproject.ndx2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@SuperBuilder
public class ValidationError extends Response{
    Map<String,String> errors;
}
