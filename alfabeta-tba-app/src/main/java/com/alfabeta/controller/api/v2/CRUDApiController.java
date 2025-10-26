package com.alfabeta.controller.api.v2;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * Generic CRUD API Controller
 * Provides basic CRUD endpoints for child controllers (v1 and v2).
 *
 * @author AlfaBeta
 * @since 1.0.0
 */
@RestController
public abstract class CRUDApiController {

    protected final String controllerName;

    protected CRUDApiController(String controllerName) {
        this.controllerName = controllerName;
    }

    /**
     * Base endpoint for GET (List all)
     */
    @GetMapping
    public ResponseEntity<String> listAll() {
        return ResponseEntity.ok("Listing all resources for: " + controllerName);
    }

    /**
     * Base endpoint for GET by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getOne(@PathVariable Long id) {
        return ResponseEntity.ok("Fetching " + controllerName + " with ID: " + id);
    }

    /**
     * Base endpoint for POST (Create new)
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Object dto) {
        return new ResponseEntity<>("Created new " + controllerName, HttpStatus.CREATED);
    }

    /**
     * Base endpoint for PUT (Update)
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Object dto) {
        return ResponseEntity.ok("Updated " + controllerName + " with ID: " + id);
    }

    /**
     * Base endpoint for DELETE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok("Deleted " + controllerName + " with ID: " + id);
    }
}
