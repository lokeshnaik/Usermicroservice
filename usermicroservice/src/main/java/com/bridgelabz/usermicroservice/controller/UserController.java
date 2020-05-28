package com.bridgelabz.usermicroservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.usermicroservice.dto.UserInformation;
import com.bridgelabz.usermicroservice.dto.UserLoginInformation;
import com.bridgelabz.usermicroservice.dto.UserUpdatePassword;
import com.bridgelabz.usermicroservice.entity.User;
import com.bridgelabz.usermicroservice.exception.UserException;
import com.bridgelabz.usermicroservice.response.Response;
import com.bridgelabz.usermicroservice.response.UserResponse;
import com.bridgelabz.usermicroservice.service.UserServices;
import com.bridgelabz.usermicroservice.serviceimplementation.UserServiceImplementation;
@RestController
public class UserController
{
	@Autowired
	private UserServices services;
	@Autowired
	private UserServiceImplementation implementation;
//	@PostMapping(path="/user/register",consumes = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping("/user/register")
     ResponseEntity<Response> userRegister(@Valid@RequestBody UserInformation informationdto,BindingResult result) throws UserException, InterruptedException {
		if(result.hasErrors())
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response(result.getAllErrors().get(0).getDefaultMessage(), 200,"null"));
           User information = services.userRegistration(informationdto);
           return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration Successful", 200, information));
	}
	
	@PostMapping("/user/mail/{email}")
   public void mailing(@PathVariable("email") String email) throws UserException, InterruptedException
	{
		services.mail(email);
	}
   @PostMapping("/user/forgotpassword")
  public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws UserException
 	{

 		boolean result = services.isUserExist(email);		
 		return ResponseEntity.status(HttpStatus.ACCEPTED).body( new Response("User Exist",200,result));
 			}
 	@PostMapping("/user/login")
    ResponseEntity<Response> userLogin(@Valid@RequestBody UserLoginInformation information,BindingResult result) throws UserException {
 		if(result.hasErrors())
 			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
 					.body(new Response(result.getAllErrors().get(0).getDefaultMessage(), 200,"null"));
     User loginResponse = new User();
     loginResponse = services.userLogin(information);
   	return ResponseEntity.status(HttpStatus.ACCEPTED)
 				.body(new Response("login successfull", 200, loginResponse));

 	}
@PutMapping("/user/updatepassword/{token}")
    ResponseEntity<UserResponse> updatePassword(@RequestBody UserUpdatePassword updatePassword,@PathVariable("token") String token) throws UserException {
 		boolean updatePasswordFlag = services.updatePassword(updatePassword, token);

 			return ResponseEntity.status(HttpStatus.ACCEPTED)
 					.body(new UserResponse("password updated successfully", 200, updatePassword));

 	}
    @GetMapping("/verify/{token}")
 	public ResponseEntity<UserResponse> verficationUser(@PathVariable("token") String token) throws Exception 
 	{
 		boolean update = services.verify(token);
 		if (update) 
 		{
 			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(token, 200, "VERIFIED"));

 		}
 		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(token, 401, "Not Verified"));
 	}
 	@GetMapping("/user")
 	ResponseEntity<UserResponse> getUserById(@RequestHeader("token") String token) throws UserException
 	{
 		User user = services.getUserById(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("user present", 200, user));
    }
 	
 	@GetMapping("/user/gettokenbyemail/{email}")
	public String getIdByEmail(@PathVariable("email") String email) throws UserException
	{
		return implementation.getIdByEmail(email);
	}
 	
 	@GetMapping("/user/username")
 	@ResponseBody
     public String getUser()
 	{
 		services.sampleTest();
 		return "Hey Lokesh.How are you?";
    }
 	@GetMapping("/user/getuserbyid/{userId}")
	public ResponseEntity<UserResponse> getuserbyId(@PathVariable("userId") Long userId) throws UserException
	{
		User user=services.getuserbyId(userId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("Welcome to user", 200,user));
	}
 	
}
