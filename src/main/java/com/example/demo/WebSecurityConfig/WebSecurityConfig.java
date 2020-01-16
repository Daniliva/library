package com.example.demo.WebSecurityConfig;

import com.example.demo.JwtAuthentication.JwtAuthenticationEntryPoint;
import com.example.demo.JwtAuthentication.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public WebSecurityConfig() {
    }

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/token/generate-token","/token/usersname",
                        "/signup","/signup1", "/signup/*", "/Book", "/Book/author_and_genre","/Book/author","/Book/genre",
                        "/token/activate/*","/token/deactivate/*","/token/modification/*","/History/*")
                .permitAll()
                .antMatchers("JournalUser/takeAll","/user",
                        "/Book/create","/Book/update/*","/Book/delete/*","/Book/takeABook/*","/Book/passBook/*",
                        "/token/activate_email/*","/token/deactivate_email/*","/users" ,"/user/*")
                .hasAnyRole("ROLE_ADMIN")
                .antMatchers("/token/usersname",
                        "JournalBook/info/*",
                        "/Book/passAReservation","/Book/takeAReservation/*","JournalBook/info/*","JournalUser")
                .hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/get_super_admin_role/*", " /get_user_role/*", "/get_admin_role/*"
                        )
                .hasAnyRole("ROLE_SUPER_ADMIN")
                .antMatchers("JournalUser","/modification","/deactivate")
                .hasAnyRole("ROLE_SUPER_ADMIN","ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
