package pafsmith.project.qrcode;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;

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
  public ResponseEntity<byte[]> qrcode() throws IOException {
    BufferedImage image = qrGenerator(250, 250);

    try (var boas = new ByteArrayOutputStream()) {
      ImageIO.write(image, "png", boas);
      byte[] bytes = boas.toByteArray();
      return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
