package dev.sideproject.ndx2.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationError extends Response{
    Map<String,String> errors;
}
