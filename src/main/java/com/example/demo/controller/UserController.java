package com.example.demo.controller;


import com.example.demo.dto.UserDTO;
import com.example.demo.model.autorization.User;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repository.journal.JournalUserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.service.string.StringService;
import com.example.demo.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {


    @Autowired
    JournalUserRepository journalUserRepository;

    private UserServiceImpl userServiceImpl;
    private RoleService roleService;

    public UserController() {
        userServiceImpl = new UserServiceImpl();
        roleService = new RoleService();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> listUser() {
        return userServiceImpl.findAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id) {
        return userServiceImpl.findById(id);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Boolean saveUser(@RequestBody UserDTO user) {
        if (userServiceImpl.findOne(user.getUsername()) == null) {
            StringService stringService = new StringService();
            if (stringService.validate(user.getUsername())) {
                User saveUser = userServiceImpl.save(user);
                JournalUser journalUser = new JournalUser();
                journalUserRepository.save(journalUser);
                journalUser.setUserId(saveUser);
                journalUserRepository.save(journalUser);
                return true;
            }
        }
        return false;
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
