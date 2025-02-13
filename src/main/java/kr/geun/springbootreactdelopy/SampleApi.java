package kr.geun.springbootreactdelopy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * SampleApi
 *
 * @author akageun
 * @since 2025-02-13
 */
@RestController
@RequestMapping("/api")
public class SampleApi {

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {

        Map<String, String> result = Map.of("message", "Hello World!");

        return ResponseEntity.ok(result);
    }
}
