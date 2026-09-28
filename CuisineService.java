package com.example.recipebox.service;

import com.example.recipebox.entity.Cuisine;
import com.example.recipebox.repository.CuisineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineService(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    public Cuisine addCuisine(Cuisine cuisine) {
        return cuisineRepository.save(cuisine);
    }

    public List<Cuisine> getAllCuisines() {
        return cuisineRepository.findAll();
    }

    public Cuisine getCuisineById(Long id) {
        return cuisineRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cuisine not found with ID: " + id));
    }

    public void deleteCuisine(Long id) {
        if (!cuisineRepository.existsById(id)) {
            throw new RuntimeException("Cuisine not found with ID: " + id);
        }

        cuisineRepository.deleteById(id);
    }
}