package by.jeksonlab.fortune.helper.analyzer;

import by.jeksonlab.fortune.team.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamAnalyzer {

    private RatingAnalyzer ratingAnalyzer = new RatingAnalyzer();
    private WonMatchesAnalyzer wonMatchesAnalyzer = new WonMatchesAnalyzer();
    private LostMatchesAnalyzer lostMatchesAnalyzer = new LostMatchesAnalyzer();

    public Map<String, Double> getTeamPreviousMatchesAverageStatistic(Team team) {
        Map<String, Double> averageStatistic = new HashMap<>();
        putAverageWonStatistic(averageStatistic, team);
        putAverageLostStatistic(averageStatistic, team);
        return averageStatistic;
    }

    public double getWinPercentage(Team team) {
        return wonMatchesAnalyzer.getWinPercentage(team);
    }

    public double getTeamAverageRating(Team team) {
        return ratingAnalyzer.getTeamAverageRating(team);
    }

    private void putAverageWonStatistic(Map<String, Double> averageStatistic, Team team) {
        averageStatistic.put("Won Teams APR", wonMatchesAnalyzer.getRivalTeamsAveragePlayersRating(team));
        averageStatistic.put("Won Teams AWR", wonMatchesAnalyzer.getRivalTeamsAverageWorldRank(team));
    }

    private void putAverageLostStatistic(Map<String, Double> averageStatistic, Team team) {
        averageStatistic.put("Lost Teams APR", lostMatchesAnalyzer.getRivalTeamsAveragePlayersRating(team));
        averageStatistic.put("Lost Teams AWR", lostMatchesAnalyzer.getRivalTeamsAverageWorldRank(team));
    }
}