package kr.geun.springbootreactdelopy.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * ChunkFileUploader
 *
 * @author akageun
 * @since 2025-02-22
 */
@Service
public class ChunkFileUploader {

  @Value("${spring.upload.path}")
  private String uploadPath;

  public void partUpload(InputStream inputStream, String filename) {
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
      FileOutputStream outStream = new FileOutputStream(filePath.toFile(), true);
    ) {
      StreamUtils.copy(inputStream, outStream);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void completeMerge(String originalFilename, List<String> targetFilenames) {
    Path fileDirectoryPath = Paths.get(uploadPath)
      .toAbsolutePath()
      .normalize();

    Path outputFile = fileDirectoryPath.resolve(originalFilename).normalize();
    try {
      Files.createFile(outputFile);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (String targetFilename : targetFilenames) {
      try {
        Path filePath = fileDirectoryPath.resolve(targetFilename).normalize();

        Files.write(outputFile, Files.readAllBytes(filePath), StandardOpenOption.APPEND);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Async
  public void removeChunkFile(List<String> targetFilenames) {
    for (String targetFilename : targetFilenames) {
      Path fileDirectoryPath = Paths.get(uploadPath)
        .toAbsolutePath()
        .normalize();

      Path filePath = fileDirectoryPath.resolve(targetFilename).normalize();
      try {
        Files.delete(filePath);

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
