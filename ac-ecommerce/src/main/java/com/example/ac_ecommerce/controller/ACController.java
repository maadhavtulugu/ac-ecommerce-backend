package com.example.ac_ecommerce.controller;

import com.example.ac_ecommerce.dto.ACResponse;
import com.example.ac_ecommerce.dto.ACRequest;
import com.example.ac_ecommerce.service.ACService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/acs")
@RequiredArgsConstructor
public class ACController {

    private final ACService acService;

    // Get all ACs
    @GetMapping("/all")
    public ResponseEntity<List<ACResponse>> getAllACs() {
        List<ACResponse> acList = acService.getAllACs();
        return ResponseEntity.ok(acList);
    }

    // Create a new AC
    @PostMapping
    public ResponseEntity<ACResponse> addAC(@RequestBody ACRequest acRequest) {
        ACResponse createdAC = acService.addAC(acRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAC);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadACImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String publicPath = acService.saveACImage(id, file);
            return ResponseEntity.ok(publicPath);
        } catch (IOException io) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to store file: " + io.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}