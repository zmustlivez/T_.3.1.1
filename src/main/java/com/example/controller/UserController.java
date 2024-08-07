package com.example.controller;

import com.example.Service.UserService;
import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/add")
    public String showAddUserForm(@ModelAttribute("user") User user) {
        return "save-user-form";
    }

    @GetMapping("/find")
    public String searchUser(@RequestParam("name") String name, Model model) {
        if (name != null && !name.isEmpty()) {
            User user = userService.getUserByName(name);
            if (user != null) {
                model.addAttribute("user", user);
                return "redirect:/find-result?name=" + name;
            }
        }
        return "redirect:/user-not-found";
    }

    @GetMapping("/find-result")
    public String showUserResult(@RequestParam("name") String name, Model model) {
        User user = userService.getUserByName(name);
        if (user != null) {
            model.addAttribute("user", user);
            return "find-result";
        }
        return "redirect:/user-not-found";
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

        // Проверяем, есть ли результат поиска
        if (model.containsAttribute("searchedUser")) {
            model.addAttribute("showSearchResult", true);
        }

        return "users";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("name") String name, @RequestParam("password") String password, Model
            model) {
        return "users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("name") String name, Model model) {
        userService.deleteUser(name);
        return "users";
    }
}
