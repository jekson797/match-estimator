package by.jeksonlab.fortune.helper.estimator.maps_estimator;

import by.jeksonlab.fortune.helper.estimator.AbstractEstimator;
import by.jeksonlab.fortune.match.CurrentMatch;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.List;

public class MapsEstimator extends AbstractEstimator {

    private static final int MAP_ADVANTAGE_FILTER = 1;
    private static final int MIN_GAMES_PLAYED_FILTER = 3;
    private int teamOneMapPoints = 0;
    private int teamTwoMapPoints = 0;
    private CurrentMatch currentMatch;

    public MapsEstimator(List<Team> teams, CurrentMatch currentMatch) {
        super(teams);
        this.currentMatch = currentMatch;
    }

    public void estimateMatchMaps() {
        for (String mapName : currentMatch.getMaps()) {
            estimateMap(mapName);
        }
    }

    public void showMapsPoints() {
        System.out.println("Maps Points");
        System.out.printf("Team: %s (Points: %d) VS Team: %s (Points: %d)\n",
                getTeams().get(0).getName(), teamOneMapPoints,
                getTeams().get(1).getName(), teamTwoMapPoints);
    }

    private void estimateMap(String mapName) {
        int mapAdvantage = 0;
        if (isTeamsPlayedOnTheMap(mapName)) {
            mapAdvantage += estimateWinPercentageOnMap(mapName);
            mapAdvantage += estimateMapInCommonPreviousMatches(mapName);
            mapAdvantage += estimatePreviousMeetingsOnTheMap(mapName);
        }
        addPointsIfDifferenceGreater(mapAdvantage, MAP_ADVANTAGE_FILTER);
    }

    private boolean isTeamsPlayedOnTheMap(String mapName) {
        int teamOneMatchesAmountOnMap = determineMatchesAmountOnMap(getTeams().get(0), mapName);
        int teamTwoMatchesAmountOnMap = determineMatchesAmountOnMap(getTeams().get(1), mapName);;
        return teamOneMatchesAmountOnMap >= MIN_GAMES_PLAYED_FILTER &&
                teamTwoMatchesAmountOnMap >= MIN_GAMES_PLAYED_FILTER;
    }

    private int estimateWinPercentageOnMap(String mapName) {
        MapWinPercentageEstimator winPercentageEstimator = new MapWinPercentageEstimator(getTeams(), mapName);
        winPercentageEstimator.collectWinPercentage();
        return winPercentageEstimator.estimateMapWinPercentage();
    }

    private int estimateMapInCommonPreviousMatches(String mapName) {
        CommonPreviousMatchesMapEstimator estimator = new CommonPreviousMatchesMapEstimator(getTeams(), mapName);
        return estimator.estimateMapInCommonPreviousMatches(currentMatch);
    }

    private int estimatePreviousMeetingsOnTheMap(String mapName) {
        PreviousMeetingsMapEstimator estimator = new PreviousMeetingsMapEstimator(getTeams(), mapName);
        return estimator.estimateMapInPreviousMeetings(currentMatch.getPreviousTeamMeetings());
    }

    private int determineMatchesAmountOnMap(Team team, String mapName) {
        int matchesAmountOnMap = 0;
        for (PreviousMatch previousMatch : team.getPreviousMatches()) {
            if (previousMatch.getMap().equals(mapName)) {
                matchesAmountOnMap++;
            }
        }
        return matchesAmountOnMap;
    }

    @Override
    protected void addPointsIfDifferenceGreater(double difference, double filter) {
        if (difference >= filter) {
            teamOneMapPoints++;
        } else if (difference <= -filter) {
            teamTwoMapPoints++;
        }
    }
}
