package com.example.jobs.controller;

import com.example.jobs.config.JwtProvider;
import com.example.jobs.model.USER_ROLE;
import com.example.jobs.model.User;
import com.example.jobs.repository.UserRepository;
import com.example.jobs.request.LoginRequest;
import com.example.jobs.response.AuthResponse;
import com.example.jobs.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(
            @RequestBody User user
    ) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if(isEmailExist!=null){
            throw new Exception("Email is already used with another account");
        }

        User createdUser=new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getPhoneNumber() != null) {
            createdUser.setPhoneNumber(user.getPhoneNumber());
        }

        User savedUser=userRepository.save(createdUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());
        authResponse.setUserId(savedUser.getId());  // Set userId from saved user
        authResponse.setPhoneNumber(savedUser.getPhoneNumber());  // Set phoneNumber from saved user
        authResponse.setFullName(savedUser.getFullName());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest req){

        String username=req.getEmail();
        String password=req.getPassword();

        Authentication authentication=authenticate(username, password);
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt= jwtProvider.generateToken(authentication);

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(username));
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("login success");
        authResponse.setRole(USER_ROLE.valueOf(role));
        authResponse.setUserId(user.getId());
        authResponse.setPhoneNumber(user.getPhoneNumber());  // Assuming the User entity has a getPhoneNumber() method
        authResponse.setFullName(user.getFullName());

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails=customerUserDetailsService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
