package pafsmith.project.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
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

  static final Set<String> SUPPORT_FORMATS = Set.of("png", "jpg", "gif");
  static final Map<String, MediaType> MEDIA_TYPES =
      Map.of(
          "png", MediaType.IMAGE_PNG,
          "jpeg", MediaType.IMAGE_JPEG,
          "gif", MediaType.IMAGE_GIF);

  @GetMapping("/api/qrcode")
  public ResponseEntity<?> qrcode(
      @RequestParam(name = "size", defaultValue = "250") int size,
      @RequestParam(name = "contents", required = false) String contents,
      @RequestParam(name = "type", defaultValue = "png") String type) {

    if (contents == "" || contents == null) {
      return ResponseEntity.badRequest().body(Map.of("error", "Contents cannot be null or blank"));
    }
    if (size < 150 || size > 350) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", "Image size must be between 150 and 350 pixels"));
    }
    if (!SUPPORT_FORMATS.contains(type)) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", "Only png, jpeg and gif image types are supported"));
    }

    if (size < 150 || size > 350) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", "Image size must be between 150 and 350 pixels"));
    }

    if (!SUPPORT_FORMATS.contains(type)) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", "Only png, jpeg and gif image types are supported"));
    }

    BufferedImage image = qrGenerator(size, size);

    try (var boas = new ByteArrayOutputStream()) {
      ImageIO.write(image, type, boas);
      byte[] bytes = boas.toByteArray();
      return ResponseEntity.ok().contentType(MEDIA_TYPES.get(type)).body(bytes);
    } catch (IOException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
