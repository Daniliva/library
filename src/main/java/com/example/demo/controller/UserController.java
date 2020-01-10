package com.example.demo.controller;


import com.example.demo.dto.UserDTO;
import com.example.demo.model.autorization.User;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repository.journal.JournalUserRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.service.string.StringService;
import com.example.demo.service.user.RoleService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    JournalUserRepository journalUserRepository;
    @Autowired
    private UserService userService;
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;

    public UserController() {
        roleService = new RoleService();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> listUser() {
        return userService.findAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getOne(@RequestParam(value = "username") String username) {
        return userService.findOne(username);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Boolean saveUser(@RequestBody UserDTO user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            StringService stringService = new StringService();
            if (stringService.validate(user.getUsername())) {
                User saveUser = userService.save(user);
                JournalUser journalUser = new JournalUser();
                journalUser.setUserId(saveUser);
                journalUserRepository.save(journalUser);
                return true;
            }
        }
        return false;
    }
    @RequestMapping(value = "/signup1", method = RequestMethod.POST)
    public Boolean saveUser1(@RequestBody UserDTO user) {

            StringService stringService = new StringService();
        return stringService.validate(user.getUsername());

    }


    @RequestMapping(value = "/get_admin_role/{id}", method = RequestMethod.GET)
    public Boolean getRoleAdmin(@PathVariable(value = "id") Long id) {
        return roleService.getRoleAdmin(id);
    }

    @RequestMapping(value = "/get_user_role/{id}", method = RequestMethod.GET)
    public Boolean getRoleUser(@PathVariable(value = "id") Long id) {
        return roleService.getRoleUser(id);
    }

    @RequestMapping(value = "/get_super_admin_role/{id}", method = RequestMethod.GET)
    public Boolean getRoleSuperAdmin(@PathVariable(value = "id") Long id) {
        return roleService.getRoleSuperAdmin(id);
    }

}
