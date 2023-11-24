package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.Hotel;
import hu.progmasters.hotel.dto.response.HotelDetails;
import hu.progmasters.hotel.dto.response.HotelGeocodingResponse;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class OpenCageGeocodingService {

    @Value("${openCageGeocoding.apiKey}")
    private String apiKey;

    public HotelGeocodingResponse getGeocodingDetails(Hotel hotel) throws IOException {
        String address = hotel.getAddress();
        String zipCode = hotel.getZipCode();
        String cityName = hotel.getCity();

        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String encodedZipCode = URLEncoder.encode(zipCode, StandardCharsets.UTF_8);
        String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        HotelGeocodingResponse geocodingResponse;
        CloseableHttpResponse response;
//        String address = urlCodedAddress(hotel);
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            geocodingResponse = new HotelGeocodingResponse();
//
//            HttpGet httpGet =
//                    new HttpGet(
//                            "https://api.opencagedata.com/geocode/v1/json" +
//                                    "?q=" + address +
//                                    "&key=" + apiKey
//                    );
//
//            response = client.execute(httpGet);
//        }
        String openCageUrl = String.format("https://api.opencagedata.com/geocode/v1/json?q=%s+%s+%s&key=%s",
                encodedAddress, encodedZipCode, encodedCityName, apiKey);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            geocodingResponse = new HotelGeocodingResponse();

            HttpGet httpGet =
                    new HttpGet(
                            openCageUrl
                    );

            response = client.execute(httpGet);
        }

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {

            JSONObject jsonObject = getJsonObject(response);

            JSONArray resultsArray = jsonObject.getJSONArray("results");
            JSONObject firstResult = resultsArray.getJSONObject(0);
            JSONObject geometryObject = firstResult.getJSONObject("geometry");
            double lat = geometryObject.getDouble("lat");
            double lng = geometryObject.getDouble("lng");

            geocodingResponse.setName(hotel.getName());
            geocodingResponse.setCity(hotel.getCity());
            geocodingResponse.setLongitude(lng);
            geocodingResponse.setLatitude(lat);

            return geocodingResponse;
        }

        return geocodingResponse;
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

    public String urlCodedAddress (Hotel hotel){
        String address = hotel.getZipCode() +  hotel.getCity() + hotel.getAddress();
        return URLEncoder.encode(address, StandardCharsets.UTF_8);
    }
}
