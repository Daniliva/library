package com.example.demo.service.impl;


import com.example.demo.dto.UserDTO;
import com.example.demo.model.autorization.Role;
import com.example.demo.model.autorization.User;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repository.journal.JournalUserRepository;
import com.example.demo.repository.user.RoleRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.string.StringService;
import com.example.demo.service.user.UserRegistrationService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder cryptEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    JournalUserRepository journalUserRepository;

    public UserServiceImpl() {

    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

    @Override
    public User save(UserDTO user) {
        Role role;
        try {
            role = roleRepository.getByRoleName("ROLE_USER");
        } catch (NullPointerException e) {
            role = new Role();
            role.setName("ROLE_USER");
            role.setDescription("User role");
            roleRepository.save(role);
        }
        role = roleRepository.getByRoleName("ROLE_USER");
        User newUser = createUser(user);
        Set<Role> setRole = new HashSet<Role>();
        setRole.add(role);
        newUser.setRoles(setRole);
        userRepository.save(newUser);
        userRegistrationService.createUserRegistration(newUser);
        return newUser;
    }
    @Override
    public boolean create(UserDTO user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            StringService stringService = new StringService();
            if (stringService.validate(user.getUsername())) {
                User saveUser = save(user);
                JournalUser journalUser = new JournalUser();
                journalUserRepository.save(journalUser);
                journalUser.setUserId(saveUser);
                journalUserRepository.save(journalUser);
                return true;
            }
        }
        return false;
    }

    private User createUser(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(cryptEncoder.encode(user.getPassword()));
        newUser.setAge(user.getAge());
        newUser.setSalary(user.getSalary());
        newUser.setDateRegistration(LocalDate.now());
        return newUser;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
}
