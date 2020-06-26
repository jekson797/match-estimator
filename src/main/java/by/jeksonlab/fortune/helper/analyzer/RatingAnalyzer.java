package by.jeksonlab.fortune.helper.analyzer;

import by.jeksonlab.fortune.team.Player;
import by.jeksonlab.fortune.team.Team;

public class RatingAnalyzer {

    public double getTeamAverageRating(Team team) {
        double totalRating = 0.0;
        for (Player player : team.getPlayers()) {
            totalRating += player.getRating();
        }
        return totalRating / team.getPlayers().size();
    }
}
