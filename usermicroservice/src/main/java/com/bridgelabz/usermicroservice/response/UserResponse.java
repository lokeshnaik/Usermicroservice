package com.bridgelabz.usermicroservice.response;
import lombok.Data;
@Data
public class UserResponse {

	public UserResponse() {
		// TODO Auto-generated constructor stub
	}
	String message;
	int statusCode;
	Object data;
	public UserResponse(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	public UserResponse(String message, int statusCode, Object data) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}

}
