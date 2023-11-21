package hu.progmasters.hotel.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DailyForecast {

        private String dateAndTime;
        private String minTemp;
        private String maxTemp;
        private String weatherDescription;
    }

