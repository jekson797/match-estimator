package by.jeksonlab.fortune.helper.estimator.teams_estimator;

import by.jeksonlab.fortune.helper.estimator.AbstractEstimator;
import by.jeksonlab.fortune.team.Team;
import by.jeksonlab.fortune.team.TeamStatistic;

import java.util.List;

public class TeamsEstimator extends AbstractEstimator {

    private static final double FORM_FILTER = 25.0;
    private static final double WORLD_RANK_FILTER = 20.0;
    private static final double WIN_PERCENTAGE_FILTER = 10.0;
    private static final double AVERAGE_RATING_FILTER = 0.04;

    public TeamsEstimator(List<Team> teams) {
        super(teams);
    }

    public void estimateTeams() {
        TeamStatistic teamOneStatistic = getTeams().get(0).getTeamStatistic();
        TeamStatistic teamTwoStatistic = getTeams().get(1).getTeamStatistic();
        estimateGeneralStatistic(teamOneStatistic, teamTwoStatistic);
        estimateAverageStatistic(teamOneStatistic, teamTwoStatistic);
        estimateTeamsAsRivals(teamOneStatistic, teamTwoStatistic);
    }

    private void estimateGeneralStatistic(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        estimateWorldRank(teamOneStatistic, teamTwoStatistic);
        estimateForm(teamOneStatistic, teamTwoStatistic);
        estimateWinPercentage(teamOneStatistic, teamTwoStatistic);
        estimateAveragePlayersRating(teamOneStatistic, teamTwoStatistic);
    }

    private void estimateAverageStatistic(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        estimateLostTeamsAverageWorldRank(teamOneStatistic, teamTwoStatistic);
        estimateLostTeamsPlayersRating(teamOneStatistic, teamTwoStatistic);
        estimateWonTeamsAverageWorldRank(teamOneStatistic, teamTwoStatistic);
        estimateWonTeamsPlayersRating(teamOneStatistic, teamTwoStatistic);
    }

    private void estimateWorldRank(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double worldRankDifference = teamOneStatistic.getWorldRank() - teamTwoStatistic.getWorldRank();
        addPointsIfWorldRankLower(worldRankDifference);
    }

    private void addPointsIfWorldRankLower(double difference) {
        if (difference <= -WORLD_RANK_FILTER) {
            incrementTeamOnePoints();
        } else if (difference >= WORLD_RANK_FILTER) {
            incrementTeamTwoPoints();
        }
    }

    private void estimateForm(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double formDifference = teamOneStatistic.getCurrentForm() - teamTwoStatistic.getCurrentForm();
        addPointsIfDifferenceGreater(formDifference, FORM_FILTER);
    }

    private void estimateWinPercentage(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double winPercentageDifference = teamOneStatistic.getWinPercentage() - teamTwoStatistic.getWinPercentage();
        addPointsIfDifferenceGreater(winPercentageDifference, WIN_PERCENTAGE_FILTER);
    }

    private void estimateAveragePlayersRating(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double averageRatingDifference = teamOneStatistic.getAverageRating() -teamTwoStatistic.getAverageRating();
        addPointsIfDifferenceGreater(averageRatingDifference, AVERAGE_RATING_FILTER);
    }

    private void estimateLostTeamsAverageWorldRank(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double averageWorldRankDifference = teamOneStatistic.getAverageStatistic().get("Lost Teams AWR") -
                teamTwoStatistic.getAverageStatistic().get("Lost Teams AWR");
        addPointsIfWorldRankLower(averageWorldRankDifference);
    }

    private void estimateWonTeamsAverageWorldRank(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double averageWorldRankDifference = teamOneStatistic.getAverageStatistic().get("Won Teams AWR") -
                teamTwoStatistic.getAverageStatistic().get("Won Teams AWR");
        addPointsIfWorldRankLower(averageWorldRankDifference);
    }

    private void estimateLostTeamsPlayersRating(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double averagePlayersRatingDifference = teamOneStatistic.getAverageStatistic().get("Lost Teams APR") -
                teamTwoStatistic.getAverageStatistic().get("Lost Teams APR");
        addPointsIfDifferenceGreater(averagePlayersRatingDifference, AVERAGE_RATING_FILTER);
    }

    private void estimateWonTeamsPlayersRating(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        double averagePlayersRatingDifference = teamOneStatistic.getAverageStatistic().get("Won Teams APR") -
                teamTwoStatistic.getAverageStatistic().get("Won Teams APR");
        addPointsIfDifferenceGreater(averagePlayersRatingDifference, AVERAGE_RATING_FILTER);
    }

    private void estimateTeamsAsRivals(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        estimateWorldRankLostStatistic(teamOneStatistic, teamTwoStatistic);
        estimateWorldRankWonStatistic(teamOneStatistic, teamTwoStatistic);
        estimatePlayersRatingLostStatistic(teamOneStatistic, teamTwoStatistic);
        estimatePlayersRatingWonStatistic(teamOneStatistic, teamTwoStatistic);
    }

    private void estimateWorldRankLostStatistic(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        if (teamOneStatistic.getWorldRank() < teamTwoStatistic.getAverageStatistic().get("Lost Teams AWR")) {
            incrementTeamOnePoints();
        }
        if (teamTwoStatistic.getWorldRank() < teamOneStatistic.getAverageStatistic().get("Lost Teams AWR")) {
            incrementTeamTwoPoints();
        }
    }

    private void estimateWorldRankWonStatistic(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        if (teamOneStatistic.getWorldRank() < teamTwoStatistic.getAverageStatistic().get("Won Teams AWR")) {
            incrementTeamOnePoints();
        }
        if (teamTwoStatistic.getWorldRank() < teamOneStatistic.getAverageStatistic().get("Won Teams AWR")) {
            incrementTeamTwoPoints();
        }
    }

    private void estimatePlayersRatingLostStatistic(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        if (teamOneStatistic.getAverageRating() > teamTwoStatistic.getAverageStatistic().get("Lost Teams APR")) {
            incrementTeamOnePoints();
        }
        if (teamTwoStatistic.getAverageRating() > teamOneStatistic.getAverageStatistic().get("Lost Teams APR")) {
            incrementTeamTwoPoints();
        }
    }

    private void estimatePlayersRatingWonStatistic(TeamStatistic teamOneStatistic, TeamStatistic teamTwoStatistic) {
        if (teamOneStatistic.getAverageRating() > teamTwoStatistic.getAverageStatistic().get("Won Teams APR")) {
            incrementTeamOnePoints();
        }
        if (teamTwoStatistic.getAverageRating() > teamOneStatistic.getAverageStatistic().get("Won Teams APR")) {
            incrementTeamTwoPoints();
        }
    }
}