package kr.geun.springbootreactdelopy.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * SimpleFileUploader
 *
 * @author akageun
 * @since 2025-02-22
 */
@Service
public class SimpleFileUploader {

  @Value("${spring.upload.path}")
  private String uploadPath;

  public void upload(String filename, InputStream inputStream) {
    Path fileDirectoryPath = Paths.get(uploadPath)
      .toAbsolutePath()
      .normalize();

    Path filePath = fileDirectoryPath.resolve(filename).normalize();
    if (Files.notExists(fileDirectoryPath)) {
      try {
        Files.createDirectories(fileDirectoryPath);

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    try (
      FileOutputStream outStream = new FileOutputStream(filePath.toFile(), false);
    ) {
      StreamUtils.copy(inputStream, outStream);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
