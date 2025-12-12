package pafsmith.project.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class QRCodeController {

  public BufferedImage qrGenerator(int height, int width) {
    BufferedImage image = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, height, width);

    return image;
  }

  @GetMapping("/api/health")
  public ResponseEntity<Void> health() {
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/api/qrcode")
  public ResponseEntity<?> qrcode(
      @RequestParam(name = "size", required = false, defaultValue = "250") String size) {

    int inputSize;
    try {
      inputSize = Integer.parseInt(size);
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest()
          .contentType(MediaType.APPLICATION_JSON)
          .body("{\"error\": \"Image size must be between 150 and 350 pixels\"}");
    }

    if (inputSize < 150 || inputSize > 350) {
      return ResponseEntity.badRequest()
          .contentType(MediaType.APPLICATION_JSON)
          .body("{\"error\": \"Image size must be between 150 and 350 pixels\"}");
    }

    BufferedImage image = qrGenerator(inputSize, inputSize);

    try (var boas = new ByteArrayOutputStream()) {
      ImageIO.write(image, "png", boas);
      byte[] bytes = boas.toByteArray();
      return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
