package hu.progmasters.hotel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ProfanityFilterService {

    private List<String> profanities;

    public ProfanityFilterService() {
    }

    private List<String> sortedProfanities(){
        List<String> profanities = Arrays.asList("fasz", "geci", "kurva", "köcsög", "buzi", "szopós", "szopj", "szopjál", "picsa", "szar", "szaros", "basz", "baszás", "basznád", "baszódj", "baszott", "baszd", "megbasz");
        Collections.sort(profanities);
        return profanities;
    }


    public boolean searchForProfanity(String target) {
        String[] targetWords = target.split("\\s+");
        for (String word : targetWords) {
            boolean isProfanity = binarySearch(word.toLowerCase());
            if (isProfanity) {
                return true;
            }
        }
        return false;
    }

    private boolean binarySearch(String target) {
        Collections.sort(sortedProfanities());
        int left = 0;
        int right = sortedProfanities().size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compareResult = target.compareTo(sortedProfanities().get(mid));
            if (compareResult == 0) {
                return true;
            } else if (compareResult < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

}
