package hu.progmasters.hotel.service;

import hu.progmasters.hotel.dto.response.DailyForecast;
import hu.progmasters.hotel.dto.response.ForecastResponse;
import hu.progmasters.hotel.dto.response.HotelDetails;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OpenWeatherService {

    @Value("${openWeather.apiKey}")
    private String apiKey;

    public HotelDetails currentWeatherInfo(String cityName) throws IOException {

        HotelDetails hotelDetails;
        CloseableHttpResponse response;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            hotelDetails = new HotelDetails();

            HttpGet httpGet =
                    new HttpGet(
                            "https://api.openweathermap.org/data/2.5/weather" +
                                    "?q=" + cityName +
                                    "&appid=" + apiKey +
                                    "&units=metric"
                    );

            response = client.execute(httpGet);
        }
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {

            JSONObject jsonObject = getJsonObject(response);

            JSONObject main = jsonObject.getJSONObject("main");
            double temp = main.getDouble("temp");
            String temperature = temp + " °C";

            String description = jsonObject
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");

            hotelDetails.setWeatherDescription(description);
            hotelDetails.setTemperature(temperature);

            return hotelDetails;

        }
        return hotelDetails;
    }

    public ForecastResponse getForecast(String cityName) throws IOException {

        ForecastResponse forecastResponse;
        CloseableHttpResponse response;
        CloseableHttpClient client = HttpClients.createDefault();
            forecastResponse = new ForecastResponse();
            forecastResponse.setCityName(cityName);

            HttpGet httpGet =
                    new HttpGet(
                            "https://api.openweathermap.org/data/2.5/forecast" +
                                    "?q=" + cityName +
                                    "&units=metric" +
                                    "&appid=" + apiKey
                    );

            response = client.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {

            JSONObject jsonObject = getJsonObject(response);
            List<DailyForecast> forecastInfos = selectForecastInfos(jsonObject);
            forecastResponse.setForeCast(forecastInfos);
            return forecastResponse;
        }
        return forecastResponse;
    }

    private List<DailyForecast> selectForecastInfos(JSONObject jsonObject) {

        List<DailyForecast> dailyForecasts = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject dailyWeatherJson = jsonArray.getJSONObject(i);
            double min = dailyWeatherJson.getJSONObject("main").getDouble("temp_min");
            double max = dailyWeatherJson.getJSONObject("main").getDouble("temp_max");
            String temperatureMin = min + " °C";
            String temperatureMax = max + " °C";

            String description = dailyWeatherJson
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");

            DailyForecast dailyForecast = new DailyForecast();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. - HH:mm");
            currentDateTime = currentDateTime.plusHours(3);
            dailyForecast.setDateAndTime(currentDateTime.format(formatter));
            dailyForecast.setMinTemp(temperatureMin);
            dailyForecast.setMaxTemp(temperatureMax);
            dailyForecast.setWeatherDescription(description);

            dailyForecasts.add(dailyForecast);
        }
        return dailyForecasts;
    }

    private JSONObject getJsonObject(CloseableHttpResponse response) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder jsonResponse = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonResponse.append(line);
        }
        inputStreamReader.close();
        reader.close();

        return new JSONObject(jsonResponse.toString());
    }

}


