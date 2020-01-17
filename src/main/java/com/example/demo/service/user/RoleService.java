package com.example.demo.service.user;

import com.example.demo.model.autorization.Role;
import com.example.demo.model.autorization.User;
import com.example.demo.repository.user.RoleRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public RoleService(){
    }

    public Boolean getRoleAdmin(Long id) {
        User user = userService.findById(id);
        Role role = roleRepository.getByRoleName("ROLE_ADMIN");
        if (role == null) {
            role = new Role();
            role.setName("ROLE_ADMIN");
            role.setDescription("Admin role");
            roleRepository.save(role);
        }
        Set<Role> setRole = new HashSet<Role>();
        setRole.add(role);
        user.setRoles(setRole);
        userRepository.save(user);
        return true;
    }

    public Boolean getRoleUser(Long id) {
        User user = userService.findById(id);
        Role role = roleRepository.getByRoleName("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
            role.setDescription("User role");
            roleRepository.save(role);
        }
        Set<Role> setRole = new HashSet<Role>();
        setRole.add(role);
        user.setRoles(setRole);
        userRepository.save(user);
        return true;
    }

    public Boolean getRoleSuperAdmin(Long id) {
        User user = userService.findById(id);
        Role role = roleRepository.getByRoleName("ROLE_SUPER_ADMIN");
        if (role == null) {
            role = new Role();
            role.setName("ROLE_SUPER_ADMIN");
            role.setDescription("Admin super role");
            roleRepository.save(role);
        }
        Set<Role> setRole = new HashSet<Role>();
        setRole.add(role);
        user.setRoles(setRole);
        userRepository.save(user);
        return true;
    }
}
