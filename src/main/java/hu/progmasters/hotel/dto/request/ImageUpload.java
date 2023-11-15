package hu.progmasters.hotel.dto.request;
import hu.progmasters.hotel.validator.Image;
import hu.progmasters.hotel.validator.MaxSize;
import hu.progmasters.hotel.validator.NotEmptyList;
import hu.progmasters.hotel.validator.MaxSize;
import hu.progmasters.hotel.validator.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ImageUpload {

    @NotEmptyList(message = "Images list must not be empty")
    @MaxSize(message = "Size max 5MB/file")
    @Image(message = "Only JPG/PNG/JPEG accepted")
    private List<MultipartFile> images;

}
