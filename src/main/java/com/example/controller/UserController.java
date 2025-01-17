package com.example.controller;

import com.example.service.UserService;
import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String showAddUserForm(@ModelAttribute("user") User user) {
        return "save-user-form";
    }


    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        boolean userAdded = userService.addUser(user);
        if (userAdded) {
            return "redirect:/users";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User with username '" + user.getUsername() + "' already exists.");
            return "redirect:/users/add";
        }
    }

    @GetMapping("/find")
    public String searchUser(@RequestParam("username") String name, Model model) {
        if (name != null && !name.isEmpty()) {
            User user;
            if ((user = userService.getUserByName(name)) != null) {
                model.addAttribute("user", user);
                return "find-result";
            }
        }
        return "user-not-found";
    }

    @GetMapping("/user-not-found")
    public String showUserNotFound() {
        return "user-not-found";
    }

    @GetMapping("/getInfo")
    public String getUserByName(@RequestParam("name") String name, Model model) {
        model.addAttribute("user", userService.getUserByName(name));
        return "user-page";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("usersList", userService.getUsers());
        if (model.containsAttribute("searchedUser")) {
            model.addAttribute("showSearchResult", true);
        }

        return "users";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("password") String password, @ModelAttribute("user") User user) {
        if (user != null) {
            User existingUser = userService.getUserByName(user.getUsername());
            existingUser.setPassword(password);
            userService.updateUser(existingUser);
            return "redirect:/users/getInfo?name=" + existingUser.getUsername();
        } else {
            return "user-not-found";
        }
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("name") String name) {
        userService.deleteUser(name);
        return "redirect:/users";
    }
}
