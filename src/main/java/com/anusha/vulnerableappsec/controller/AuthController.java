package com.anusha.vulnerableappsec.controller;

import com.anusha.vulnerableappsec.model.User;
import com.anusha.vulnerableappsec.repository.UserRepository;
import com.anusha.vulnerableappsec.repository.VulnerableUserSearchRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VulnerableUserSearchRepository vulnerableUserSearchRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        User user = userRepository.findByUsername(username);
        System.out.println("LOGIN ATTEMPT -> username=" + username + ", password=" + password);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }
        System.out.println("LOGIN FAILED");
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return "dashboard";
    }
    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(required = false) String username,
            org.springframework.ui.Model model) {

        if (username != null) {
            model.addAttribute("users", vulnerableUserSearchRepository.searchByUsername(username));
        }

        return "search";
    }
    @GetMapping("/deserialize")
    public String deserializePage() {
        return "deserialize";
    }

    @PostMapping("/deserialize")
    public String deserializeObject(
            @RequestParam String data,
            org.springframework.ui.Model model) {
        System.out.println("DESERIALIZE ENDPOINT HIT");

        try {
            byte[] decoded = Base64.getDecoder().decode(data);

            ObjectInputStream ois =
                    new ObjectInputStream(new ByteArrayInputStream(decoded));

            // ❌ INSECURE DESERIALIZATION
            Object obj = ois.readObject();
            System.out.println("DESERIALIZED CLASS: " + obj.getClass().getName());


            model.addAttribute("result", obj.toString());

        } catch (Exception e) {
            model.addAttribute("result", "Error during deserialization");
        }

        return "deserialize";
    }
    @GetMapping("/comment")
    public String commentPage() {
        return "comment";
    }

    @PostMapping("/comment")
    public String postComment(
            @RequestParam String comment,
            org.springframework.ui.Model model) {

        // ❌ Directly reflecting user input
        model.addAttribute("comment", comment);

        return "comment";
    }
    @GetMapping("/admin")
    public String adminPage(HttpSession session) {

        // ❌ Only checks login, no role validation
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return "admin";
    }



}
