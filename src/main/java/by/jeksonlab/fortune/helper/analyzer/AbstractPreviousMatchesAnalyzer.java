package by.jeksonlab.fortune.helper.analyzer;

import by.jeksonlab.fortune.team.Player;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPreviousMatchesAnalyzer {

    private static final int PLAYERS_IN_TEAM = 5;

    @Nullable
    protected Team findRivalTeamFromTeamOpponentsList(PreviousMatch match, Team team) {
        for (Team rivalTeam : team.getOpponents()) {
            if (match.getOpponentName().equals(rivalTeam.getName())) {
                return rivalTeam;
            }
        }
        return null;
    }

    protected double getRivalTeamTotalPlayersRating(PreviousMatch match, Team team) {
        double totalTeamPlayersRating = 0.0;
        Team rivalTeam = findRivalTeamFromTeamOpponentsList(match, team);
        if (null != rivalTeam) {
            for (Player player : rivalTeam.getPlayers()) {
                totalTeamPlayersRating += player.getRating();
            }
        }
        return totalTeamPlayersRating;
    }

    protected int getRivalTeamWorldRank(PreviousMatch match, Team team) {
        Team rivalTeam = findRivalTeamFromTeamOpponentsList(match, team);
        if (null != rivalTeam) {
            return rivalTeam.getTeamStatistic().getWorldRank();
        }
        return 0;
    }

    protected int getPlayersInTeam() {
        return PLAYERS_IN_TEAM;
    }
}