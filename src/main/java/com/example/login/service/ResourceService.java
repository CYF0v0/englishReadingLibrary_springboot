package com.example.login.service;

import com.example.login.model.Resource;
import com.example.login.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource updateResourceById(int id, Resource updatedResource) {
        return resourceRepository.findById(id)
                .map(resource -> {
                    resource.setPublisher(updatedResource.getPublisher());
                    resource.setName(updatedResource.getName());
                    resource.setTime(updatedResource.getTime());
                    resource.setUrl(updatedResource.getUrl());
                    return resourceRepository.save(resource);
                })
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));
    }

    public void deleteResourceById(int id) {
        resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));
        resourceRepository.deleteById(id);
    }
}