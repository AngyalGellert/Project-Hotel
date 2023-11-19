package hu.progmasters.hotel.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ForecastResponse {

    private String cityName;
    private List<DailyForecast> foreCast;

}
