package ium.pethub.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FilePathResolver {

    @Value("${app.file-upload-path}")
    private String fileUploadPath;

    public Path resolveImageBasePath() {
        return Paths.get(fileUploadPath).normalize();
    }
}
