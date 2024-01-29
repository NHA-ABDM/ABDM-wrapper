/* (C) 2024 */
package com.nha.abdm.wrapper.common.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacadeResponse {
  private int code;
  private String message;
  private ErrorResponse error;
}