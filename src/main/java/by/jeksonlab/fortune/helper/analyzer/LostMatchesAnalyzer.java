package by.jeksonlab.fortune.helper.analyzer;

import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

public class LostMatchesAnalyzer extends AbstractPreviousMatchesAnalyzer {

    public double getRivalTeamsAveragePlayersRating(Team team) {
        double totalPlayersRating = 0.0;
        for (PreviousMatch match : team.getTeamStatistic().getLostMatches()) {
            totalPlayersRating += getRivalTeamTotalPlayersRating(match, team);
        }
        return totalPlayersRating / (team.getTeamStatistic().getLostMatches().size() * getPlayersInTeam());
    }

    public double getRivalTeamsAverageWorldRank(Team team) {
        int totalWorldRank = 0;
        for (PreviousMatch match : team.getTeamStatistic().getLostMatches()) {
            totalWorldRank += getRivalTeamWorldRank(match, team);
        }
        return (double) totalWorldRank / team.getTeamStatistic().getLostMatches().size();
    }
}
