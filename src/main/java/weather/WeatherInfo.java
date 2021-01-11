package weather;

public class WeatherInfo {

    private final String conditionId;
    private final String mainInfo;
    private final String moreInfo;

    public WeatherInfo(String conditionId, String mainInfo, String moreInfo) {
        this.conditionId = conditionId;
        this.mainInfo = mainInfo;
        this.moreInfo = moreInfo;
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
}
