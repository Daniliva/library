package com.example.demo;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.autorization.Role;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.user.RoleService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }



}
/*{ "email":"email",
"password": "1111",
"role": ["ADMIN"] ,
"username": "john.doe", "firstName": "John", "lastName": "Doe"}
security.oauth2.client.clientId=837564313444-gepn5pfkth4cin6qanep4mll0anq8q57.apps.googleusercontent.com
security.oauth2.client.clientSecret=iY4pHqtX35D5eNRAp93uTU6t
security.oauth2.client.clientAuthenticationScheme=form
security.oauth2.client.scope=openid,email,profile
security.oauth2.client.accessTokenUri=https://www.googleapis.com/oauth2/v4/token
security.oauth2.client.userAuthorizationUri=https://accounts.google.com/o/oauth2/v2/auth
security.oauth2.resource.userInfoUri=https://www.googleapis.com/oauth2/v3/userinfo
security.oauth2.resource.preferTokenInfo=true
server.port=9000

*/