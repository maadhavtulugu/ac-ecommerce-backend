package com.example.ac_ecommerce.service;

import com.example.ac_ecommerce.dto.ACRequest;
import com.example.ac_ecommerce.dto.ACResponse;
import com.example.ac_ecommerce.model.AC;
import com.example.ac_ecommerce.repository.ACRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ACService {

    private final ACRepository acRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    // Get all ACs
    public List<ACResponse> getAllACs() {
        return acRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Add a new AC
    public ACResponse addAC(ACRequest request) {
        AC ac = new AC();
        ac.setName(request.getName());
        ac.setBrand(request.getBrand());
        ac.setCategory(request.getCategory());
        ac.setCapacity(request.getCapacity());
        ac.setPrice(request.getPrice());

        AC saved = acRepository.save(ac);
        return toResponse(saved);
    }

    // Upload AC image
    public String saveACImage(Long acId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("No file provided");
        }

        AC ac = acRepository.findById(acId)
                .orElseThrow(() -> new RuntimeException("AC not found with id: " + acId));

        String contentType = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
        if (!(contentType.contains("image/png") || contentType.contains("image/jpeg") || contentType.contains("image/jpg"))) {
            throw new RuntimeException("Only PNG and JPEG images are allowed");
        }

        String original = file.getOriginalFilename() == null ? "image" : file.getOriginalFilename();
        String safeOriginal = original.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
        String filename = UUID.randomUUID() + "_" + safeOriginal;

        // Ensure upload directory exists
        Path dir = Paths.get(uploadDir);
        Files.createDirectories(dir);

        Path target = dir.resolve(filename);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        String publicPath = "/images/" + filename;
        ac.setImage(publicPath);
        acRepository.save(ac);

        return publicPath;
    }

    // Convert AC entity to ACResponse
    private ACResponse toResponse(AC ac) {
        ACResponse response = new ACResponse();
        response.setId(ac.getId());
        response.setName(ac.getName());
        response.setBrand(ac.getBrand());
        response.setCategory(ac.getCategory());
        response.setCapacity(ac.getCapacity());
        response.setPrice(ac.getPrice());
        response.setImage(ac.getImage());
        return response;
    }
}
