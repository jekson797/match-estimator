package by.jeksonlab.fortune.page;

import java.util.LinkedHashMap;
import java.util.Map;

public class TeamMapsStatsPage extends AbstractPage {

    private static final String MAP_NAME_LOCATOR = ".map-statistics-row-map-mapname";
    private static final String MAP_WIN_PERCENTAGE_LOCATOR = ".map-statistics-row-win-percentage";
    private static final String PERCENTAGE_SIGN = "%";
    private Map<String, Double> mapsStats = new LinkedHashMap<>();

    public void collectMapsStats() {
        for (int i = 0; i < getElementsByLocator(MAP_NAME_LOCATOR).size(); i++) {
            String mapName = getTextFromLocator(MAP_NAME_LOCATOR, i);
            double winPercentage =
                    Double.parseDouble(cutPercentageSignFromString(getTextFromLocator(MAP_WIN_PERCENTAGE_LOCATOR, i)));
            mapsStats.put(mapName, winPercentage);
        }
    }

    public Map<String, Double> getMapsStats() {
        return mapsStats;
    }

    private String cutPercentageSignFromString(String string) {
        return string.replace(PERCENTAGE_SIGN, "");
    }
}
