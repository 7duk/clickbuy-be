package dev.sideproject.ndx2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@SuperBuilder
public class SuccessResponse extends Response{
    Object data;
    String message;
}
