package com.example.offwork.rest.api;

import com.example.offwork.domain.dtos.PartialUserDto;
import com.example.offwork.domain.dtos.ResetPasswordDto;
import com.example.offwork.domain.dtos.UserDto;
import com.example.offwork.domain.entities.UserEnt;
import com.example.offwork.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class User {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserEnt userEnt) {
        return ResponseEntity.ok(service.create(userEnt));
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody PartialUserDto partialUserDto) {
        return ResponseEntity.ok(service.update(partialUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<PartialUserDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return ResponseEntity.ok(service.resetPassword(resetPasswordDto));
    }
}
