package com.bridgelabz.usermicroservice.service;


import com.bridgelabz.usermicroservice.dto.UserInformation;
import com.bridgelabz.usermicroservice.dto.UserLoginInformation;
import com.bridgelabz.usermicroservice.dto.UserUpdatePassword;
import com.bridgelabz.usermicroservice.entity.User;
import com.bridgelabz.usermicroservice.exception.UserException;
public interface UserServices 
{
	User userRegistration(UserInformation informationdto) throws UserException, InterruptedException;
	User userLogin(UserLoginInformation information) throws UserException;
    boolean verify(String token) throws Exception;
    boolean updatePassword(UserUpdatePassword password, String token) throws UserException;
    public boolean isUserExist(String email) throws UserException;
    User getUserById(String token) throws UserException;
    User getUserByEmail(String email) throws UserException;
     void sampleTest();
     void mail(String email)throws UserException, InterruptedException;
     User getuserbyId(Long userId) throws UserException;
}
