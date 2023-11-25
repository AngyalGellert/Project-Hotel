package hu.progmasters.hotel.dto.request;

import hu.progmasters.hotel.validator.Image;
import hu.progmasters.hotel.validator.MaxSize;
import hu.progmasters.hotel.validator.NotEmptyList;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data

public class HotelCreateRequest {

    @NotNull(message = "Hotel name must not be empty")
    @Size(min = 1, max = 200, message = "Hotel name must be between 1 and 200 characters")
    private String name;

    @NotNull(message = "Hotel's city name must not be empty")
    @Size(min = 1, max = 200, message = "Hotel's city name must be between 1 and 200 characters")
    private String city;

    @NotNull(message = "Hotel address must not be empty")
    @Size(min = 1, max = 200, message = "Hotel address must be between 1 and 200 characters")
    private String address;

    @NotNull(message = "Hotel zipCode must not be empty")
    @Size(min = 1, max = 200, message = "Hotel zipCode must be between 1 and 200 characters")
    private String zipCode;

    @NotEmptyList(message = "Images list must not be empty")
    @MaxSize(message = "Size max 5MB/file")
    @Image(message = "Only JPG/PNG/JPEG accepted")
    private List<MultipartFile> images;

}
