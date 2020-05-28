package com.bridgelabz.usermicroservice.serviceimplementation;

import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.PostPersist;
import javax.validation.constraints.Email;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.usermicroservice.dto.UserInformation;
import com.bridgelabz.usermicroservice.dto.UserLoginInformation;
import com.bridgelabz.usermicroservice.dto.UserUpdatePassword;
import com.bridgelabz.usermicroservice.entity.User;
import com.bridgelabz.usermicroservice.exception.UserException;
import com.bridgelabz.usermicroservice.repository.UserRepository;
import com.bridgelabz.usermicroservice.service.UserServices;
import com.bridgelabz.usermicroservice.utility.JWTOperations;
@Service
public class UserServiceImplementation implements UserServices
{
	@Autowired
	UserRepository repository;
     @Autowired
	JWTOperations jwtop;
    @Autowired
	private RestTemplate restTemplate;
     private BCryptPasswordEncoder encryption=new BCryptPasswordEncoder();
     @Autowired
	@PersistenceContext
    EntityManager entitymanager;
	
	@Override
    @Transactional
    public User userRegistration(UserInformation informationdto) throws UserException, InterruptedException 
    {
		//entitymanager.flush();
		User user = new User();
		BeanUtils.copyProperties(informationdto, user);
      	String password = user.getPassword();
		String encrptPassword = encryption.encode(password);
        user.setCreatedDate(LocalDateTime.now());
        user.setPassword(encrptPassword);
		user.setVerified(false);
		User user1=repository.registrationSave(user);
		System.out.println(user+"-------------------------------------");
		//Thread.sleep(20000);
		//restTemplate.getForEntity("http://localhost:8096/notification/regVerification/"+user1.getEmail(), String.class);
		//restTemplate.exchange("http://localhost:8096/notification/regVerification/"+user.getEmail(),HttpMethod.GET,null,String.class);
		//sendNotificaion(user.getEmail());
        return user1;
	}
	@Override
	public void mail(String email) throws UserException, InterruptedException
	{
		// TODO Auto-generated method stub
		User user=getUserByEmail(email);
    	System.out.println(user+"---------------------------------------");
        restTemplate.exchange("http://localhost:8096/notification/regVerification/"+user.getEmail(),HttpMethod.GET,null,String.class);
         System.out.println("Hey Lokesh.It is after calling resttemplate");
		
		
	}
	
    public void sendNotificaion(String email) throws UserException {
    	User user=getUserByEmail(email);
    	System.out.println(user+"---------------------------------------");
        restTemplate.exchange("http://localhost:8096/notification/regVerification/"+user.getEmail(),HttpMethod.POST,null,String.class);
         System.out.println("Hey Lokesh.It is after calling resttemplate");
		
	}
	@Override
	@Transactional
	public User userLogin(UserLoginInformation information) throws UserException {

		User info = repository.loginValidate(information)
				.orElseThrow(() -> new UserException("Email not registered", HttpStatus.NOT_FOUND));
       restTemplate.exchange("http://localhost:8096/notification/regVerification/"+info.getEmail(),HttpMethod.POST,null,String.class);


		if (encryption.matches(information.getPassword(), info.getPassword())) {

			return info;

		} else 
		{
			throw new UserException("Password not matched", HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	@Transactional
	public boolean verify(String token) throws Exception 
	{

		Long id = (long) jwtop.parseJWT(token);
		repository.verify(id);
		return true;
	}

	@Override
	@Transactional
	public boolean updatePassword(UserUpdatePassword updatePassword, String token) throws UserException {
		Long id = (long) 0;
		boolean updatePasswordFlag = false;

		id = (Long) jwtop.parseJWT(token);
		User user;
         user = repository.getUserById(id).orElseThrow(() -> new UserException("User not registered", HttpStatus.NOT_FOUND));

		if (encryption.matches(updatePassword.getOldPassword(), user.getPassword())) {
			String epassword = encryption.encode(updatePassword.getConfirmPassword());
			updatePassword.setConfirmPassword(epassword);
			updatePasswordFlag = repository.updatePassword(updatePassword, id);
		} else {
			throw new UserException("Old password is invalid", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}

		return updatePasswordFlag;
	}

	@Override
	@Transactional
	public boolean isUserExist(String email) throws UserException {
		boolean userExist = false;
            User user = repository.getUser(email)
					.orElseThrow(() -> new UserException("User not registered", HttpStatus.NOT_FOUND));
			if (user.isVerified() == true && user != null) {
				Random random = new Random();

				String password = String.valueOf(random.nextInt());
				String encodedPassword = encryption.encode(password);
				repository.updateRandomPassword(encodedPassword, user.getUserId());
				System.out.println("---------" + user.getUserId());
              userExist = true;
			}
          return userExist;
	}

	@Override
	@Transactional
	public User getUserById(String token) throws UserException {
		User user=new User();
		long id = (long) 0;
         id = jwtop.parseJWT(token);
        user = repository.getUserById(id).orElseThrow(() -> new UserException("User not present", HttpStatus.NOT_FOUND));
		return user;
	}
	public String getIdByEmail(String email) throws UserException 
	{
		User user=getUserByEmail(email);
		return jwtop.JwtToken(user.getUserId());
	}
	
	@Override
	public User getUserByEmail(String email) throws UserException
	 {
		System.out.println(email);
	User user=repository.getUser(email).orElseThrow(() -> new UserException("email not exists",HttpStatus.NOT_FOUND));
	return user;
	}
	@Override
	public void sampleTest() 
	{
		System.out.println("Hey Lokesh.Hardwork,will power and dedication brings success");
	}
	@Override
	public User getuserbyId(Long userId) throws UserException 
	{
		System.out.println(userId);
		User user = repository.getUserById(userId).orElseThrow(() -> new UserException("User not present", HttpStatus.NOT_FOUND));
		return user;
		
	}
	

	
		
}
