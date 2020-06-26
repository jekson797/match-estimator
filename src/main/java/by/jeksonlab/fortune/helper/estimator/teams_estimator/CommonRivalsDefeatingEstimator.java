package by.jeksonlab.fortune.helper.estimator.teams_estimator;

import by.jeksonlab.fortune.helper.estimator.AbstractEstimator;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.List;
import java.util.Set;

public class CommonRivalsDefeatingEstimator extends AbstractEstimator {

    private static final int TEAMS_DEFEATED_FILTER = 2;
    private Set<String> commonRivalsName;

    public CommonRivalsDefeatingEstimator(List<Team> teams, Set<String> commonRivalsName) {
        super(teams);
        this.commonRivalsName = commonRivalsName;
    }

    public void estimateCommonRivalsDefeating() {
        int teamOneTotalRivalsDefeated = determineTotalRivalsDefeated(getTeams().get(0).getPreviousMatches());
        int teamTwoTotalRivalsDefeated = determineTotalRivalsDefeated(getTeams().get(1).getPreviousMatches());
        addPointsIfDifferenceGreater(teamOneTotalRivalsDefeated - teamTwoTotalRivalsDefeated,
                TEAMS_DEFEATED_FILTER);
    }

    private int determineTotalRivalsDefeated(List<PreviousMatch> previousMatches) {
        int totalRivalsDefeated = 0;
        for (String rivalName : commonRivalsName) {
            if (isRivalWasDefeated(rivalName, previousMatches)) {
                totalRivalsDefeated++;
            }
        }
        return totalRivalsDefeated;
    }

    private boolean isRivalWasDefeated(String rivalName, List<PreviousMatch> previousMatches) {
        int totalGamesWithRival = determineTotalGamesWithRival(rivalName, previousMatches);
        int totalRivalDefeats = determineTotalRivalDefeats(rivalName, previousMatches);
        return isDefeatsMoreThanHalf(totalGamesWithRival, totalRivalDefeats);
    }

    private int determineTotalGamesWithRival(String rivalName, List<PreviousMatch> previousMatches) {
        int totalGamesWithRival = 0;
        for (PreviousMatch previousMatch : previousMatches) {
            if (previousMatch.getOpponentName().equals(rivalName)) {
                totalGamesWithRival++;
            }
        }
        return totalGamesWithRival;
    }

    private int determineTotalRivalDefeats(String rivalName, List<PreviousMatch> previousMatches) {
        int totalRivalDefeats = 0;
        for (PreviousMatch previousMatch : previousMatches) {
            if (previousMatch.getOpponentName().equals(rivalName)) {
                totalRivalDefeats += incrementIfRivalWasDefeated(previousMatch);
            }
        }
        return totalRivalDefeats;
    }
    
    private int incrementIfRivalWasDefeated(PreviousMatch previousMatch) {
        if (previousMatch.getResult().equals(getWonResult())) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean isDefeatsMoreThanHalf(int totalGamesWithRival, int totalRivalDefeats) {
        return (double) totalRivalDefeats / totalGamesWithRival * 100 > 50;
    }
}
