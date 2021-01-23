package weather;

public class WeatherInfo {

    private final String conditionId;
    private final String mainInfo;
    private final String moreInfo;
    private final double temperature;

    /**
     * WeatherInfo Object class.
     * Creates an instance and saves all relevant Weather information after fetching data from OpenWeatherMaps
     * @param conditionId
     * @param mainInfo
     * @param moreInfo
     * @param temperature
     */
    public WeatherInfo(String conditionId, String mainInfo, String moreInfo, double temperature) {
        this.conditionId = conditionId;
        this.mainInfo = mainInfo;
        this.moreInfo = moreInfo;
        this.temperature = temperature;
    }

    /**
     * checks if the weather condition is good.
     * Temperature must be between -10 and 40 degrees Celsius
     * https://openweathermap.org/weather-conditions
     * @return true if weather is good and false otherwise.
     */
    public boolean isWeatherGood() {
        // Checks if the Temperature is between -10 and 40 degrees. If not returns false
        if (temperature < -10.0 || temperature > 40.0) {
            return false;
        }
        // Checks what the weather is like.
        // TODO: make it more specific
        if (mainInfo.equals("Thunderstorm")  ||
                mainInfo.equals("Drizzle")  ||
                mainInfo.equals("Rain")  ||
                mainInfo.equals("Snow") ) {
            return false;
        }
        return true;
    }

    public String getConditionId() {
        return conditionId;
    }

    public String getMainInfo() {
        return mainInfo;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public double getTemperature() {
        return temperature;
    }
}
