package hu.progmasters.hotel.validator;

import hu.progmasters.hotel.exception.FileSizeException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxSizeValidator implements ConstraintValidator<MaxSize, List<MultipartFile>> {

    private static final long FIVE_MB_IN_BYTES = 5_000_000;

    @Override
    public void initialize(MaxSize constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<MultipartFile> images, ConstraintValidatorContext context) {
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                if (image.getSize() > FIVE_MB_IN_BYTES) {
                    throw new FileSizeException(
                            "The maximum file size allowed is 5 MB, " +
                                    "and the uploaded image has: " + image.getSize() / 1_000_000 + "MB ");
                }
            }
        }
        return true;
    }
}
