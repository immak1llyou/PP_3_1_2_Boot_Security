package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPanel(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("authUser", userService.getAuthUser());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("activeTable", "usersTable");
        return "admin/adminPanel";
    }

//    @GetMapping("/show/edit")
//    public String editUser(Model model, @RequestParam("userName") String userName) {
//        model.addAttribute("user", userService.getUserByUserName(userName));
//        model.addAttribute("roles", roleService.getRoles());
//        return "admin/edit_users_page";
//    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userName") String userName) {
        userService.deleteUserByUserName(userName);
        return "redirect:/admin";
    }

    @GetMapping("/show")
    public String showInfo(@RequestParam("userName") String userName, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("authUser", userService.getAuthUser());
        model.addAttribute("userName", userService.getUserByUserName(userName));
        return "admin/show_user_info_page";
    }

//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("roles", roleService.getRoles());
//        return "admin/new_user_page";
//    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin";
    }
}
