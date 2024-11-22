package com.example.login.controller;

import com.example.login.model.User;
import com.example.login.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserManagementController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String account = loginRequest.get("account");
        String password = loginRequest.get("password");

        logger.info("Login request received.");

        if (account == null || account.isEmpty()) {
            logger.warn("Login failed: 请输入账户");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error("请输入账户"));
        }

        if (password == null || password.isEmpty()) {
            logger.warn("Login failed: 请输入密码");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error("请输入密码"));
        }

        User user = userService.findByAccount(account);
        if (user != null && user.getPassword().equals(password)) {
            logger.info("Login successful. Account: {}", account);
            return ResponseEntity.ok(ok(user));
        } else {
            logger.warn("Login failed: 账户或密码输入不正确");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error("账户或密码输入不正确"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        logger.info("Register request received.");

        try {
            // 检查用户是否已存在
            User existingUser = userService.findByAccount(user.getAccount());
            if (existingUser != null) {
                logger.warn("Register failed: Account already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error("Account already exists"));
            }

            // 保存用户
            User savedUser = userService.saveUser(user);
            logger.info("Register successful. Account: {}", user.getAccount());

            // 返回所有用户信息
            return ResponseEntity.ok(ok(savedUser));
        } catch (Exception e) {
            logger.error("Register failed: Registration failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Registration failed"));
        }
    }

    @DeleteMapping("/deleteUsers/{account}")
    public ResponseEntity<?> deleteUserByAccount(@PathVariable String account) {
        logger.info("Delete request received.");

        try {
            // 查找要删除的用户
            User user = userService.findByAccount(account);
            if (user == null) {
                logger.warn("Delete failed: Account not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Account not found"));
            }

            // 删除用户
            userService.deleteByAccount(account);
            logger.info("Delete successful. Account: {}", account);

            // 返回删除的用户信息
            return ResponseEntity.ok(ok(user));
        } catch (Exception e) {
            logger.error("Delete failed: Failed to delete user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to delete user"));
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        logger.info("Get all users request received.");

        try {
            List<User> users = userService.findAllUsers();
            if (users.isEmpty()) {
                logger.warn("Get all users failed: No users found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("No users found"));
            }

            logger.info("Get all users successful. Users count: {}", users.size());
            // 返回所有用户信息
            return ResponseEntity.ok(ok(users));
        } catch (Exception e) {
            logger.error("Get all users failed: Failed to retrieve users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve users"));
        }
    }

    @PutMapping("/updateUser/{account}")
    public ResponseEntity<?> updateUserByAccount(@PathVariable String account, @RequestBody User updatedUser) {
        logger.info("Update user request received.");

        try {
            // 查找要更新的用户
            User existingUser = userService.findByAccount(account);
            if (existingUser == null) {
                logger.warn("Update failed: Account not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Account not found"));
            }

            // 更新用户信息
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setPassword(updatedUser.getPassword());

            // 保存更新后的用户信息
            User savedUser = userService.saveUser(existingUser);
            logger.info("Update successful. Account: {}", account);

            // 返回更新后的用户信息
            return ResponseEntity.ok(ok(savedUser));
        } catch (Exception e) {
            logger.error("Update failed: Failed to update user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to update user"));
        }
    }

    private Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 403);
        response.put("message", message);
        return response;
    }

    private Map<String, Object> ok(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", data);
        return response;
    }
}
