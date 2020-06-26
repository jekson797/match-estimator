package by.jeksonlab.fortune.helper.estimator.maps_estimator;

import by.jeksonlab.fortune.helper.estimator.AbstractEstimator;
import by.jeksonlab.fortune.team.Team;

import java.util.List;
import java.util.Map;

public class MapWinPercentageEstimator extends AbstractEstimator {

    private static final double WIN_PERCENTAGE_FILTER = 10;
    private String map;
    double teamOneWinPercentageOnMap;
    double teamTwoWinPercentageOnMap;

    public MapWinPercentageEstimator(List<Team> teams, String map) {
        super(teams);
        this.map = map;
    }

    public void collectWinPercentage() {
        teamOneWinPercentageOnMap = getTeamWinPercentageOnMap(0, map);
        teamTwoWinPercentageOnMap = getTeamWinPercentageOnMap(1, map);
    }

    public int estimateMapWinPercentage() {
        return getMapWinPercentageAdvantage(teamOneWinPercentageOnMap - teamTwoWinPercentageOnMap);
    }

    public double getTeamOneWinPercentageOnMap() {
        return teamOneWinPercentageOnMap;
    }

    public double getTeamTwoWinPercentageOnMap() {
        return teamTwoWinPercentageOnMap;
    }

    private int getMapWinPercentageAdvantage(double difference) {
        if (difference >= WIN_PERCENTAGE_FILTER) {
            return 1;
        } else if (difference <= -WIN_PERCENTAGE_FILTER) {
            return -1;
        }
        return 0;
    }

    private double getTeamWinPercentageOnMap(int teamIndex, String mapName) {
        Map<String, Double> mapsStats = getTeams().get(teamIndex).getTeamStatistic().getMapsStats();
        return mapsStats.getOrDefault(mapName, -1.0);
    }
}
