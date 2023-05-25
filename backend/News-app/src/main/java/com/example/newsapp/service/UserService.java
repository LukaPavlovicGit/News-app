package com.example.newsapp.service;

import com.example.newsapp.entities.User;
import com.example.newsapp.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserService {
    @Inject
    private UserRepository userRepository;

    public String login(String email, String password){
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = this.userRepository.findByEmail(email);
        if (user == null || !user.getHashedPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withClaim("email", user.getEmail())
                .withClaim("firstname", user.getFirstname())
                .withClaim("lastname", user.getLastname())
                .withClaim("status", user.getStatus())
                .sign(algorithm);
    }

    public User register(String role, String firstname, String lastname, String email, String password, boolean status){
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setRole(role);
        user.setEmail(email);
        user.setHashedPassword(DigestUtils.sha256Hex(password));
        user.setStatus(status);
        return userRepository.insert(user);
    }

    public User update(Integer id, String role, String firstname, String lastname, String email, String password, Boolean status){
        User user = new User(id, role, firstname, lastname, email, password == null ? null : DigestUtils.sha256Hex(password), status);
        return userRepository.update(user);
    }

    public void statusActivation(Integer userId){
        userRepository.statusActivation(userId);
    }

    public void statusDeactivation(Integer userId){
        userRepository.statusDeactivation(userId);
    }

    public List<User> getAll(){ return userRepository.getAll(); }

    public boolean isAuthenticated(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();

        User user = this.userRepository.findByEmail(email);

        if (user == null){
            return false;
        }

        return true;
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        String role = jwt.getClaim("role").asString();

        User user = this.userRepository.findByEmail(email);

        if (user == null || !Objects.equals(role, "admin")){
            return false;
        }

        return true;
    }
}
