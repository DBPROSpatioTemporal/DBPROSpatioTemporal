package weather;
import net.aksingh.owmjapis.api.APIException; 
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.WeatherData;

    /*
            OpenWeatherApi with my own Api Key.
            Implemented a dependency to use OpenWeatherApi in Java called OWM JAPIs https://github.com/iamashks/OWM-JAPIs
     */

public class owmAPI {
    // TODO: make api key secure
    private static final String API_KEY = "81339d3bbeade1e0c69042233be22deb";   // My API Key; Other should be able to use it too

    private OWM owm;

    public owmAPI() {
        this.owm = new OWM(API_KEY);    // declaring object of "OWM" class
        // OWM SETTINGS
        owm.setUnit(OWM.Unit.METRIC);   // using the metric system
    }

    // TODO
    public String requestHourlyWeather(double latitude, double longitude ) throws APIException {

        // getting current weather data for the "Berlin" city
        HourlyWeatherForecast hwf = owm.hourlyWeatherForecastByCoords(latitude, longitude);
        System.out.println(hwf.getDataList().size());
        System.out.println(hwf.toString());

        WeatherData weatherDataForecast1 = hwf.getDataList().get(0);
        WeatherData weatherDataForecast2 = hwf.getDataList().get(1);

        // TODO: ??????
        System.out.println(weatherDataForecast1.getDateTime());
        System.out.println(weatherDataForecast1.getDateTimeText());


        return hwf.getDataList().toString();
    }

    // Get the current weather of given Coords.
    public WeatherInfo requestCurrentWeather(double latitude, double longitude ) throws APIException {

        // getting current weather data for the Coords
        CurrentWeather cwd = owm.currentWeatherByCoords(latitude, longitude);

        // checking data retrieval was successful or not
        if (cwd.hasRespCode() && cwd.getRespCode() == 200) {

            // checking if city name is available
            if (cwd.hasCityName()) {
                //printing city name from the retrieved data
                System.out.println("City: " + cwd.getCityName());
            }

            // checking if max. temp. and min. temp. is available
            if (cwd.hasMainData() && cwd.getMainData().hasTempMax() && cwd.getMainData().hasTempMin()) {
                // printing the max./min. temperature
                System.out.println("Temperature: " + cwd.getMainData().getTempMax()
                        + "/" + cwd.getMainData().getTempMin() + "\'C");
            }


        }
        System.out.println(cwd.getMainData().getTemp());
        
        // Get out of the request all relevant Weather Data
        String weatherList = cwd.getWeatherList().toString();
        double temperature = cwd.getMainData().getTemp();

        // return WeatherInfo Object
        return toWeatherObject(weatherList, temperature);
    }

    private WeatherInfo toWeatherObject(String weatherList, double temperature) {
        // Splitting the Weather String on "," and spaces into an array
        String[] weatherArr =  weatherList.substring(9, weatherList.length() - 2).split("\\s*,\\s*");

        // get from the new weatherArr all data that is needed and split at '=' where the data is not relevant.
        // e.g. "mainInfo = Rain"     becomes just     "Rain"
        String conditionId = weatherArr[0].split("=")[1];
        String mainInfo = weatherArr[1].split("=")[1];
        String moreInfo = weatherArr[2].split("=")[1];

        // Creating a new weatherInfo Instance with all the weather Info that OWM gave us
        return new WeatherInfo(conditionId, mainInfo, moreInfo, temperature);
    }






}
