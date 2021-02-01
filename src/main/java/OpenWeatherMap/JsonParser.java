package OpenWeatherMap;

import OpenWeatherMap.WeatherInfo.WeatherInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class is a Json utility class which helps deserialize Json into Object classes with an ObjectMapper
 * The library used for this is Jackson
 */
public class JsonParser {
    private ObjectMapper mapper;

    public JsonParser() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Gets a Json String from the OpenWeatherMap ONECALL request and returns a WeatherInfo Class with all Weather data
     * Works only for current weather and Hourly weather
     * @param jsonBody json String
     * @return WeatherInfo class with ALL weather data
     * @throws IOException
     */
    public WeatherInfo JsonToWeatherInfo(String jsonBody) throws IOException {
        // Json Deserialization Config
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Get rootNode
        JsonNode rootNode = mapper.readTree(jsonBody);
        // Return a WeatherInfo Object with all Weather Information parsed from JSON to POJO
        return JsonToClass(rootNode, WeatherInfo.class);
    }

    /**
     * A JsonNode is being mapped on a Class and returns the same Type of Class but all
     * the fields have been set.
     * @param node a JsonNode that needs to be mapped ass Class
     * @param clazz The class that the JsonNode is needs to be mapped to
     * @param <A> A Generic Class
     * @return A class that the JsonNode is mapped on
     * @throws JsonProcessingException
     */
    private <A> A JsonToClass(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return mapper.treeToValue(node, clazz);
    }

}
