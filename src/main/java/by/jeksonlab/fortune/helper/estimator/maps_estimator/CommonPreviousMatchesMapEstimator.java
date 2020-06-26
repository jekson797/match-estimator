package by.jeksonlab.fortune.helper.estimator.maps_estimator;

import by.jeksonlab.fortune.helper.estimator.AbstractEstimator;
import by.jeksonlab.fortune.match.CurrentMatch;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonPreviousMatchesMapEstimator extends AbstractEstimator {

    private static final int PREVIOUS_MATCHES_ADVANTAGE_FILTER = 1;
    private String mapName;

    public CommonPreviousMatchesMapEstimator(List<Team> teams, String mapName) {
        super(teams);
        this.mapName = mapName;
    }

    public int estimateMapInCommonPreviousMatches(CurrentMatch currentMatch) {
        Set<PreviousMatch> commonMatchesOnMap = getCommonMatchesOnCertainMap(currentMatch);
        double teamOneWinsPercentageOnPreviousMap =
                getPreviousMapWinsPercentage(currentMatch.getTeamOneCommonMatchesWithRivals(), commonMatchesOnMap);
        double teamTwoWinsPercentageOnPreviousMap =
                getPreviousMapWinsPercentage(currentMatch.getTeamTwoCommonMatchesWithRivals(), commonMatchesOnMap);
        return getCommonPreviousMatchesAdvantage(teamOneWinsPercentageOnPreviousMap
                - teamTwoWinsPercentageOnPreviousMap);
    }

    private Set<PreviousMatch> getCommonMatchesOnCertainMap(CurrentMatch currentMatch) {
        Set<PreviousMatch> commonMatches = new HashSet<>();
        for (PreviousMatch teamOnePreviousMatch : currentMatch.getTeamOneCommonMatchesWithRivals()) {
            for (PreviousMatch teamTwoPreviousMatch : currentMatch.getTeamTwoCommonMatchesWithRivals()) {
                addMatchIfMatchesSameAndOnCertainMap(commonMatches, teamOnePreviousMatch, teamTwoPreviousMatch);
            }
        }
        return commonMatches;
    }

    private void addMatchIfMatchesSameAndOnCertainMap(Set<PreviousMatch> commonMatches,
                                                      PreviousMatch firstMatch, PreviousMatch secondMatch) {
        if (areMatchesSame(firstMatch, secondMatch) && firstMatch.getMap().equals(mapName)) {
            commonMatches.add(firstMatch);
        }
    }

    private boolean areMatchesSame(PreviousMatch firstMatch, PreviousMatch secondMatch) {
        return areOpponentsNameEquals(firstMatch, secondMatch) && areMatchMapsEquals(firstMatch, secondMatch);
    }

    private boolean areOpponentsNameEquals(PreviousMatch firstMatch, PreviousMatch secondMatch) {
        return firstMatch.getOpponentName().equals(secondMatch.getOpponentName());
    }

    private boolean areMatchMapsEquals(PreviousMatch firstMatch, PreviousMatch secondMatch) {
        return firstMatch.getMap().equals(secondMatch.getMap());
    }

    private double getPreviousMapWinsPercentage(List<PreviousMatch> teamPreviousMatches,
                                                Set<PreviousMatch> commonMatches) {
        List<PreviousMatch> previousMatchesOnTheMap = getPreviousMatchesOnTheMap(teamPreviousMatches, commonMatches);
        return getPreviousMatchesWinsPercentage(previousMatchesOnTheMap);
    }

    private List<PreviousMatch> getPreviousMatchesOnTheMap(List<PreviousMatch> teamPreviousMatches,
                                                           Set<PreviousMatch> commonMatches) {
        List<PreviousMatch> previousMatchesOnTheMap = new ArrayList<>();
        for (PreviousMatch previousMatch : teamPreviousMatches) {
            for (PreviousMatch commonMatch : commonMatches) {
                if (areMatchesSame(previousMatch, commonMatch)) {
                    previousMatchesOnTheMap.add(previousMatch);
                }
            }
        }
        return previousMatchesOnTheMap;
    }

    private double getPreviousMatchesWinsPercentage(List<PreviousMatch> previousMatches) {
        int totalMatches = 0;
        int wonMatches = 0;
        for (PreviousMatch previousMatch : previousMatches) {
            totalMatches++;
            if (isMatchWasWon(previousMatch)) {
                wonMatches++;
            }
        }
        return (double) wonMatches / totalMatches * 100;
    }

    private boolean isMatchWasWon(PreviousMatch previousMatch) {
        return previousMatch.getResult().equals(getWonResult());
    }

    private int getCommonPreviousMatchesAdvantage(double difference) {
        if (difference >= PREVIOUS_MATCHES_ADVANTAGE_FILTER) {
            return 1;
        } else if (difference <= -PREVIOUS_MATCHES_ADVANTAGE_FILTER) {
            return -1;
        }
        return 0;
    }
}
