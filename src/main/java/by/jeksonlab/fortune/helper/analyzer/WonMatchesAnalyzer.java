package by.jeksonlab.fortune.helper.analyzer;

import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

public class WonMatchesAnalyzer extends AbstractPreviousMatchesAnalyzer {

    public double getRivalTeamsAveragePlayersRating(Team team) {
        double totalPlayersRating = 0.0;
        for (PreviousMatch match : team.getTeamStatistic().getWonMatches()) {
            totalPlayersRating += getRivalTeamTotalPlayersRating(match, team);
        }
        return totalPlayersRating / (team.getTeamStatistic().getWonMatches().size() * getPlayersInTeam());
    }

    public double getRivalTeamsAverageWorldRank(Team team) {
        int totalWorldRank = 0;
        for (PreviousMatch match : team.getTeamStatistic().getWonMatches()) {
            totalWorldRank += getRivalTeamWorldRank(match, team);
        }
        return (double) totalWorldRank / team.getTeamStatistic().getWonMatches().size();
    }

    public double getWinPercentage(Team team) {
        int totalMatches = team.getPreviousMatches().size();
        return team.getTeamStatistic().getWonMatches().size() / (double) totalMatches * 100;
    }
}
