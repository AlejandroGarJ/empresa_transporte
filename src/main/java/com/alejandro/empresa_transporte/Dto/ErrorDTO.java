package com.alejandro.empresa_transporte.Dto;

public class ErrorDTO {
  private String code;
  private String error;
  public String getCode() {
    return code;
  }
  public ErrorDTO(String code, String error) {
    this.code = code;
    this.error = error;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }
}
