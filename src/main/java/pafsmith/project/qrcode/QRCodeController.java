package pafsmith.project.qrcode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeController {
  @GetMapping("/api/health")
  public ResponseEntity<Void> health() {
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/api/qrcode")
  public ResponseEntity<Void> qrcode() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
