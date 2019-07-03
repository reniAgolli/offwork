package com.example.offwork.rest.api;
import com.example.offwork.domain.dtos.ApplicationDto;
import com.example.offwork.domain.dtos.UserPrincipal;
import com.example.offwork.services.api.AppService;
import com.example.offwork.services.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class AppController {

    @Autowired
    private AppService service;

    @GetMapping()
    public ResponseEntity<List<ApplicationDto>> getAll(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(service.getAll(userPrincipal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping()
    public ResponseEntity<ApplicationDto> create(@RequestBody ApplicationDto applicationDto, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(service.create(applicationDto, userPrincipal));
    }

    @PutMapping()
    public ResponseEntity<ApplicationDto> update(@RequestBody ApplicationDto applicationDto) {
        return ResponseEntity.ok(service.update(applicationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/revoke/{id}")
    public ResponseEntity<ApplicationDto> revoke(@PathVariable("id") Long id, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(service.revoke(id, userPrincipal));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/confirm/{id}")
    public ResponseEntity<ApplicationDto> confirm(@PathVariable("id") Long id, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(service.confirm(id, userPrincipal));
    }
}
