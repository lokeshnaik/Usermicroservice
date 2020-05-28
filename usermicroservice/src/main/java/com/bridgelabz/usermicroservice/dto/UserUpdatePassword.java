package com.bridgelabz.usermicroservice.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserUpdatePassword
{
	String oldPassword;
	@NotBlank(message="NewPassword is mandatory")
	String newPassword;
	@NotBlank(message="ConfirmPassword is mandatory")
	String confirmPassword;
	public UserUpdatePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

}
