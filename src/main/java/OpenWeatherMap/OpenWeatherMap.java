package OpenWeatherMap;

import OpenWeatherMap.WeatherInfo.WeatherInfo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is used to make API requests to OpenWeatherMap (https://api.openweathermap.org)
 * based on the coordinates (latitude and longitude) that is being given and returns a WeatherInfo Class.
 * This class uses a regular HttpClient from the java.net. library to make a connection.
 * It uses the ONECALL API from OpenWeatherMaps https://openweathermap.org/api/one-call-api
 * ONECALL API can respond with data about the current, minutely, and hourly weather in the same request.
 * THIS ONE WORKS ONLY FOR CURRENT AND HOURLY WEATHER!!!
 * The response is in JSON format which is being deserialized into Plain Old Java Objects.
 * The Jackson library is used for the deserialization of the JSON response.
 */
public class OpenWeatherMap {

    private final String API_BASE_URL_ONECALL = "https://api.openweathermap.org/data/2.5/onecall?";
    private final int AMOUNT_HOURS_SAVE = 3; // How many hours of the hourly weather forecast to save (1-48)
    private HttpClient client;
    private JsonParser jsonParser;

    // SETTINGS: for OpenWeatherMaps ONECALL //
    private String units = "metric"; // imperial
    private String exclude = "daily,minutely,alerts"; // current, minutely, hourly, daily, alerts
    private String API_KEY = "81339d3bbeade1e0c69042233be22deb";


    /**
     * Creates an HttpClient for OpenWeatherMaps API requests
     * Creates a JsonParser to deserialize the JSON response to Java Objects
     */
    public OpenWeatherMap() {
        this.client = HttpClient.newHttpClient();
        this.jsonParser = new JsonParser();
    }

    /**
     * Request Weather from OpenWeatherMaps with latitude and longitude and returns
     * a WeatherInfo Class with all the weather information.
     * @param latitude
     * @param longitude
     * @return WeatherInfo Class which has all the Weather Data that was requests
     * @throws IOException
     */
    public WeatherInfo requestWeather(double latitude, double longitude ) throws IOException {
        // Build URI String for the API request
        String URIString = API_BASE_URL_ONECALL +
                "lat=" + String.valueOf(latitude) +
                "&lon=" + String.valueOf(longitude) +
                "&units=" + units +
                "&exclude=" + exclude +
                "&appid=" + API_KEY;

        // Make an Http request and get a json response back
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept","application/json")
                .uri(URI.create(URIString))
                .build();

        // Get response back and Save the Body as a String
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(response.body());
        // parse the Json response into Java Objects and return a WeatherInfo class with ALL Weather Information
        return jsonParser.JsonToWeatherInfo(response.body());
    }

}
