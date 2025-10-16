package com.igorjava.userservice.controller;

import com.igorjava.userservice.dto.CreateUpdateUserRequest;
import com.igorjava.userservice.dto.UserDto;
import com.igorjava.userservice.entity.User;
import com.igorjava.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody CreateUpdateUserRequest req) {
        User u = new User(req.getFirstName(), req.getLastName(), req.getBirthDate(), req.getEmail());
        User saved = userService.create(u);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(UserDto.fromEntity(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(UserDto.fromEntity(userService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listAll() {
        var list = userService.getAll().stream().map(UserDto::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody CreateUpdateUserRequest req) {
        User upd = new User(req.getFirstName(), req.getLastName(), req.getBirthDate(), req.getEmail());
        var saved = userService.update(id, upd);
        return ResponseEntity.ok(UserDto.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
