package pl.edu.pwr.ztw.books.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.models.User;
import pl.edu.pwr.ztw.books.repositories.IUserRepository;
import pl.edu.pwr.ztw.books.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getUsers() {
        return ResponseEntity.ok(new ApiResponse<>(userRepository.findAll(), "success", "Users retrieved successfully"));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            return ResponseEntity.ok(new ApiResponse<>(userRepository.findById(id), "success", "User retrieved successfully"));
        }

        return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "User not found"));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<?>> addUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse<>(savedUser, "success", "User added successfully"));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (userRepository.existsById(id)) {
            User oldUser = userRepository.findById(id).orElseThrow();
            oldUser.setCardNumber(user.getCardNumber());
            oldUser.setName(user.getName());
            oldUser.setSurname(user.getSurname());
            oldUser.setEmail(user.getEmail());
            User updatedUser = userRepository.save(oldUser);
            return ResponseEntity.ok(new ApiResponse<>(updatedUser, "success", "User updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "User not found"));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "User not found"));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "success", "User deleted successfully"));
    }
}
