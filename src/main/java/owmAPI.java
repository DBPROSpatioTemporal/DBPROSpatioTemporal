import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.ForecastData;

    /*
            OpenWeatherApi with my own Api Key.
            Implemented a dependency to use OpenWeatherApi in Java called OWM JAPIs https://github.com/iamashks/OWM-JAPIs
     */

public class owmAPI {
    private static final String API_KEY = "81339d3bbeade1e0c69042233be22deb";   // My API Key; Other should be able to use it too

    private OWM owm;

    public owmAPI() {
        this.owm = new OWM(API_KEY);    // declaring object of "OWM" class
        // OWM SETTINGS
        owm.setUnit(OWM.Unit.METRIC);   // using the metric system
    }

    public void requestWeather(String Location) throws APIException {

        // getting current weather data for the "Berlin" city
        CurrentWeather cwd = owm.currentWeatherByCityName(Location);
        HourlyWeatherForecast fd = owm.hourlyWeatherForecastByCityName(Location);

        //System.out.println(cwd.toString());
        System.out.println(fd.getDataList());
        //System.out.println(cwd.hasRainData());

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
    }




}
