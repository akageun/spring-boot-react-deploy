package kr.geun.springbootreactdelopy.file;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * FileUploadApi
 *
 * @author akageun
 * @since 2025-02-22
 */
@RestController
@RequestMapping("/api")
public class FileUploadApi {

  private final SimpleFileUploader simpleFileUploader;
  private final ChunkFileUploader chunkFileUploader;

  public FileUploadApi(SimpleFileUploader simpleFileUploader, ChunkFileUploader chunkFileUploader) {
    this.simpleFileUploader = simpleFileUploader;
    this.chunkFileUploader = chunkFileUploader;
  }

  @PostMapping(value = "/upload/simple",
    consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public String simpleUpload(
    HttpServletRequest request
  ) throws IOException {

    String originalFilename = URLDecoder.decode(request.getHeader("X-Original-Filename"), StandardCharsets.UTF_8);

    simpleFileUploader.upload(originalFilename, request.getInputStream());

    return "OK";
  }

  @PostMapping(value = "/upload/chunk",
    consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<String> chunkUpload(
    HttpServletRequest request
  ) throws IOException {

    String originalFilename = URLDecoder.decode(request.getHeader("X-Original-Filename"), StandardCharsets.UTF_8);
    int chunkIndex = request.getHeader("X-Chunk-Index") == null ? 0 : Integer.parseInt(request.getHeader("X-Chunk-Index"));

    String partFilename = originalFilename + ".part" + chunkIndex;

    chunkFileUploader.partUpload(request.getInputStream(), partFilename);

    return ResponseEntity.ok("OK");
  }

  @PostMapping(value = "/upload/chunk/complete")
  public ResponseEntity<String> chunkUploadComplete(
    HttpServletRequest request
  ) {

    String originalFilename = URLDecoder.decode(request.getHeader("X-Original-Filename"), StandardCharsets.UTF_8);
    int lastChunkIndex = request.getHeader("X-Last-Chunk-Index") == null ? 0 : Integer.parseInt(request.getHeader("X-Last-Chunk-Index"));
    List<String> targetFilenames = new ArrayList<>();
    for (int i = 0; i <= lastChunkIndex; i++) {
      targetFilenames.add(originalFilename + ".part" + i);
    }

    chunkFileUploader.completeMerge(originalFilename, targetFilenames);
    chunkFileUploader.removeChunkFile(targetFilenames);

    return ResponseEntity.ok("OK");

  }
}
