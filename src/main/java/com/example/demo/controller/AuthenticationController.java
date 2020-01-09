package com.example.demo.controller;


import com.example.demo.config.TokenProvider;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.autorization.AuthToken;
import com.example.demo.model.autorization.LoginUser;
import com.example.demo.model.autorization.User;
import com.example.demo.repository.user.UserRegistrationRepository;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.service.user.UserRegistrationService;
import com.example.demo.service.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserService userService;
    private UserServiceImpl userServiceImpl;

    public AuthenticationController() {
        userServiceImpl = new UserServiceImpl();
    }

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (userRegistrationRepository.getByUsername(loginUser.getUsername()).getEmailReal()) {
                final String token = jwtTokenUtil.generateToken(authentication);
                return ResponseEntity.ok(new AuthToken(token));
            } else {
                userRegistrationService.modificationUserRegistration(loginUser.getUsername());
                return ResponseEntity.ok("You don`t activate account, please check email");
            }
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "don't" + loginUser.getUsername() + loginUser.getPassword()
            );
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/usersname", method = RequestMethod.POST)
    public User User() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userService.findOne(userDetails.getUsername());//userService.findOne(jwtTokenUtil.getUsernameFromToken(token));
    }

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public ResponseEntity<?> Activate(@PathVariable String code) {
        return userRegistrationService.activationUser(code);
    }

    @RequestMapping(value = "/deactivate/{code}", method = RequestMethod.GET)
    public Boolean Deactivate(@PathVariable String code) {
        return userRegistrationService.deactivationUser(code);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/activate_email/{name}", method = RequestMethod.GET)
    public Boolean ActivateEmail(@PathVariable String name) {
        return userRegistrationService.activationUsername(name);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/deactivate_email/{name}", method = RequestMethod.GET)
    public Boolean DeactivateEmail(@PathVariable String name) {
        return userRegistrationService.deactivationUsername(name);
    }

    @RequestMapping(value = "/modification/{code}", method = RequestMethod.POST)
    public User Deactivate(@PathVariable String code, @RequestBody UserDTO user) {
        return userRegistrationService.modificationUser(code, user);
    }
}
