package OpenWeatherMap.WeatherInfo;

/**
 * This class is being used in CurrentWeather and HourlyWeather classes
 * This class describes the Weather conditions
 */
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather(){
    }

    /**
     * toString method
     * @return String with all field variables
     */
    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    /**
     * SETTERS AND GETTERS
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
