package hu.progmasters.hotel.dto.request;

import hu.progmasters.hotel.validator.Image;
import hu.progmasters.hotel.validator.MaxSize;
import hu.progmasters.hotel.validator.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomFormUpdate {

    @NotNull(message = "Room Id must not be empty")
    private Long id;

    private String name;

    @Positive (message = "The value must be positive")
    private Integer numberOfBeds;

    @Positive(message = "The value must be positive")
    private Integer pricePerNight;

    private String description;

    @MaxSize(message = "Size max 5MB/file")
    @Image(message = "Only JPG/PNG/JPEG accepted")
    @NotEmptyList(message = "Images list must not be empty")
    private List<MultipartFile> images;

}
