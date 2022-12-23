package com.springdto.utility;

import com.springdto.dto.LoginDTO;
import com.springdto.dto.UserDTO;
import com.springdto.model.User;
import com.springdto.repository.UserRepository;
import com.springdto.userException.Exception;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTToken implements Serializable {
    @Autowired
    private LoginDTO loginData;

    @Autowired
    private UserRepository userRepository;
    private String SECRET_KEY = "login";

    public String generateToken(LoginDTO loginData) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("eMail", loginData.getEmail());
        claims.put("Password", loginData.getPassword());
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    public LoginDTO decodeToken(String Token) {
        Map<String, Object> claims;
        claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(Token).getBody();
        loginData.setEmail((String) claims.get("eMail"));
        loginData.setPassword((String)(claims.get("Password")));
        return loginData;
    }

}
//Generation of Token and Decoding of Token