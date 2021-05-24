package br.com.compasso.productMongoDB.controller.model;

public class MensagensErro {
	Integer status_code;
	String message;
	
	public MensagensErro(Integer status_code, String message) {
		this.status_code = status_code;
		this.message = message;
	}
	public Integer getStatus_code() {
		return status_code;
	}
	public void setStatus_code(Integer status_code) {
		this.status_code = status_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
