package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequestDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final AuthService authService;


    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        System.out.println("Checked\n");
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect email or password.");
        }
        System.out.println("Passed\n");
        System.out.println(authenticationRequest.getUsername());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        System.out.println("userDetails\n");
        User optionalUser = userRepository.findByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        System.out.println("jwt\n");
        if(optionalUser != null){
            response.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.getId())
                    .put("role",optionalUser.getRole())
                    .toString()
            );

            response.addHeader("Access-Control-Expose-Headers","Authorization");
            response.addHeader("Access-Control-Allow-Headers","Authorization, X-PINGOTHER, Origin, " +
                    "X-Requested-With, Content-Type, Accept, X-Custom-header");
            response.addHeader(HEADER_STRING,TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequestDto signupRequest){

        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exists.", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

}
