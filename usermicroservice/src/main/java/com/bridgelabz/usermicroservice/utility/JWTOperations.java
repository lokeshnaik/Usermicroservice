package com.bridgelabz.usermicroservice.utility;

import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
@Component
public class JWTOperations {
	private static final String SECRET = "6360803337";

	public String JwtToken(long userId) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", userId).sign(Algorithm.HMAC256(SECRET));
		} catch (IllegalArgumentException | JWTCreationException e)
		{
			 e.printStackTrace();
		}
		return token;
	}


	public long parseJWT(String jwt) {
		Long userId = (long) 0;
		if (jwt != null) {
			userId = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(jwt).getClaim("id").asLong();
		}
		return userId;

	}
}
