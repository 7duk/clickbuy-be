package dev.sideproject.ndx2.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuccessResponse extends Response{
    Object data;
    String message;
}
