package com.bridgelabz.usermicroservice.repository;

import java.util.Optional;

import com.bridgelabz.usermicroservice.dto.UserLoginInformation;
import com.bridgelabz.usermicroservice.dto.UserUpdatePassword;
import com.bridgelabz.usermicroservice.entity.User;

public interface UserRepository 
{
	User registrationSave(User information);
	Optional<User> loginValidate(UserLoginInformation information);
	boolean verify(Long id);
    public boolean updatePassword(UserUpdatePassword updatePassword, Long id);
	Optional<User> getUser(String email); 
    boolean updateRandomPassword(String password,long l);
	Optional<User> getUserById(long id);
}
