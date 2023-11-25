package hu.progmasters.hotel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ProfanityFilterService {

    private List<String> profanities = Arrays.asList("fasz, geci, kurva, köcsög, buzi, szopós, szopj, szopjál, picsa, szar, szaros, basz, baszás, basznád, baszódj, baszott, baszd, megbasz");

    public ProfanityFilterService() {
    }

    public List<String> getProfanities() {
        return profanities.stream().sorted().collect(Collectors.toList());
    }

    public int searchForProfanity(String target) {
        String[] targetList = target.split(" ");
        List<String> profanityList = getProfanities();

        for (String targetWord : targetList) {
            int left = 0;
            int right = profanityList.size() - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                // Check if the target is present at the middle
                int compareResult = targetWord.compareTo(profanityList.get(mid));
                if (compareResult == 0) {
                    return mid; // Target found, return the index
                } else if (compareResult < 0) {
                    right = mid - 1; // Target is in the left half
                } else {
                    left = mid + 1; // Target is in the right half
                }
            }
        }

        return -1; // None of the target words are present in the profanity list
    }

}
