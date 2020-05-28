package com.bridgelabz.usermicroservice.response;
import lombok.Data;
@Data
public class Response
{
	public Response() 
	{

	}
	String message;
	int statusCode;
	Object data;
	public Response(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	public Response(String message, int statusCode, Object data) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}
}
