package by.jeksonlab.fortune.helper.informer;

import by.jeksonlab.fortune.team.Player;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Informer {

    private List<Team> teams;
    private Set<String> commonRivalsName;

    public Informer(List<Team> teams, Set<String> commonRivalsName) {
        this.teams = teams;
        this.commonRivalsName = commonRivalsName;
    }

    public void showTeamsInfo() {
        for (Team team : teams) {
            showGeneralInfo(team);
        }
    }

    public void showPreviousTeamMeetings(List<PreviousMatch> previousMeetings) {
        if (previousMeetings.size() < 1) {
            System.out.println("No previous team meetings");
        } else {
            for (PreviousMatch match : previousMeetings) {
                System.out.printf("Map: %s | Opponent: %s | Result: %s\n",
                        match.getMap(), match.getOpponentName(), match.getResult());
            }
        }
    }

    private void showGeneralInfo(Team team) {
        System.out.printf("\nTeam: %s, World Rank: %d, Form: %s\n",
                team.getName(), team.getTeamStatistic().getWorldRank(), team.getTeamStatistic().getCurrentForm());
    }

    private void showTeamAverageRating(Team team) {
        System.out.printf("Average Rating: %.2f\n", team.getTeamStatistic().getAverageRating());
    }

    private void showWinPercentage(Team team) {
        System.out.printf("Win %% %.2f\n", team.getTeamStatistic().getWinPercentage());
    }

    private void showPlayersDetailedInfo(Team team) {
        for (Player player : team.getPlayers()) {
            System.out.printf("-> Player: %s, Rating: %.2f\n", player.getName(), player.getRating());
        }
    }

    private void showMapsStats(Map<String, Double> mapsStats) {
        separateSection();
        mapsStats.forEach((key, value) -> System.out.printf("Map: %s, Win Percentage: %.2f\n", key, value));
        System.out.println();
    }

    private void showEfficiency(Team team) {
        separateSection();
        Map<String, Double> teamEfficiency = team.getTeamStatistic().getAverageStatistic();
        System.out.printf("Lost Teams AWR: %.2f\n" +
                        "Lost Teams APR: %.2f\n" +
                        "Won Teams AWR: %.2f\n" +
                        "Won Teams APR: %.2f\n",
                teamEfficiency.get("Lost Teams AWR"), teamEfficiency.get("Lost Teams APR"),
                teamEfficiency.get("Won Teams AWR"), teamEfficiency.get("Won Teams APR"));
    }

    private void showPreviousMatchesWithCommonRivals(Team team) {
        separateSection();
        for (PreviousMatch previousMatch : team.getPreviousMatches()) {
            String previousMatchOpponentName = previousMatch.getOpponentName();
            if (commonRivalsName.contains(previousMatchOpponentName)) {
                showPreviousMatch(previousMatch);
            }
        }
    }

    private void showPreviousMatch(PreviousMatch previousMatch) {
        System.out.printf("Team: %s || Map: %s || Result: %s\n",
                previousMatch.getOpponentName(), previousMatch.getMap(), previousMatch.getResult());
    }

    private void separateSection() {
        System.out.println("------------------------------------------------");
    }
}
