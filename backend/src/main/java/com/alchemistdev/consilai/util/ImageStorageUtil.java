package com.alchemistdev.consilai.util;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Component
public class ImageStorageUtil {

    private final String uploadDir = "uploads/profile-pictures/";

    public String saveBase64Image(String base64) {
        try {
            byte[] data = Base64.getDecoder().decode(base64);
            String fileName = UUID.randomUUID() + ".png";
            String filePath = Paths.get(uploadDir, fileName).toString();

            try (OutputStream stream = new FileOutputStream(filePath)) {
                stream.write(data);
            }

            // Puedes devolver una ruta relativa o URL pública
            return "/images/profile/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
}
