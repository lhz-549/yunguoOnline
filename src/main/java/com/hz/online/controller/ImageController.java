package com.hz.online.controller;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@Slf4j
public class ImageController {

    @Value("${auth.token}")
    private String authToken;

    private final RestTemplate restTemplate;


    public ImageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String uploadUrl = "https://i.111666.best/image";


    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No image uploaded");
        }
        String url = "https://i.111666.best/image";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Auth-Token", authToken);
        headers.add("Origin", "https://111666.best");

        log.info(String.valueOf(image.getBytes()) + "文件名：" + image.getOriginalFilename());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        });
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            log.info("Response: {}", response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            log.error("Error occurred while uploading image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image");
        }
    }



    @GetMapping("/downloadImage/{imagePath}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String imagePath) {
        String url = "https://i.111666.best/image/" + imagePath;
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return response;
    }

    @DeleteMapping("/deleteImage/{imagePath}")
    public ResponseEntity<String> deleteImage(@PathVariable String imagePath) {
        String url = "https://i.111666.best/image/" + imagePath;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", authToken);
        headers.add("Origin", "https://111666.best");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            return ResponseEntity.ok("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete image: " + e.getMessage());
        }
    }

}

