package com.example.login.controller;

import com.example.login.model.Resource;
import com.example.login.service.ResourceService;
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
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllResources() {
        logger.info("Get all resources request received.");

        try {
            List<Resource> resources = resourceService.getAllResources();
            if (resources.isEmpty()) {
                logger.warn("No resources found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("No resources found"));
            }
            logger.info("Resources retrieved successfully. Total resources: {}", resources.size());
            return ResponseEntity.ok(ok(resources));
        } catch (Exception e) {
            logger.error("Failed to retrieve resources.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve resources"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createResource(@RequestBody Resource resource) {
        logger.info("Create resource request received. Resource: {}", resource);

        try {
            Resource savedResource = resourceService.saveResource(resource);
            logger.info("Resource created successfully. Resource: {}", savedResource);
            return ResponseEntity.ok(ok(savedResource));
        } catch (Exception e) {
            logger.error("Failed to create resource. Resource: {}", resource, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to create resource"));
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateResourceById(@PathVariable int id, @RequestBody Resource updatedResource) {
        logger.info("Update resource by ID request received. ID: {}, Updated Resource: {}", id, updatedResource);

        try {
            Resource resource = resourceService.updateResourceById(id, updatedResource);
            logger.info("Resource updated successfully. Resource: {}", resource);
            return ResponseEntity.ok(ok(resource));
        } catch (RuntimeException e) {
            logger.error("Resource not found. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Resource not found"));
        } catch (Exception e) {
            logger.error("Failed to update resource. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to update resource"));
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteResourceById(@PathVariable int id) {
        logger.info("Delete resource by ID request received. ID: {}", id);

        try {
            resourceService.deleteResourceById(id);
            logger.info("Resource deleted successfully. ID: {}", id);
            return ResponseEntity.ok(ok("Resource deleted successfully"));
        } catch (RuntimeException e) {
            logger.error("Resource not found. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Resource not found"));
        } catch (Exception e) {
            logger.error("Failed to delete resource. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to delete resource"));
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