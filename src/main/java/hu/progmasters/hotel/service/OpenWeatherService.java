package hu.progmasters.hotel.service;

import hu.progmasters.hotel.dto.response.HotelDetails;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Transactional
public class OpenWeatherService {

    @Value("${openWeather.apiKey}")
    private String apiKey;

//    private String lofasz = "lofasz";

    public HotelDetails currentWeatherInfo (String cityName) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HotelDetails hotelDetails = new HotelDetails();

        HttpGet httpGet =
                new HttpGet(
                        "https://api.openweathermap.org/data/2.5/weather" +
                                "?q=" + cityName +
                                "&appid=" + apiKey +
                                "&units=metric" +
                                "&lang=hu"
                );

        CloseableHttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder jsonResponse = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            inputStreamReader.close();
            reader.close();

            JSONObject jsonObject = new JSONObject(jsonResponse.toString());

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
}


