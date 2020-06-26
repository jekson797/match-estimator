package by.jeksonlab.fortune.helper.estimator;

import by.jeksonlab.fortune.team.Team;

import java.util.List;

public abstract class AbstractEstimator {

    private static final String WON_RESULT = "W";
    private static final String LOST_RESULT = "L";
    private List<Team> teams;
    private static int teamOnePoints = 0;
    private static int teamTwoPoints = 0;

    public AbstractEstimator(List<Team> teams) {
        this.teams = teams;
    }

    public void showPoints() {
        System.out.println("Teams Points");
        System.out.printf("Team: %s (Points: %d) VS Team: %s (Points: %d)\n",
                getTeams().get(0).getName(), teamOnePoints,
                getTeams().get(1).getName(), teamTwoPoints);
    }

    public int getTeamOnePoints() {
        return teamOnePoints;
    }

    public int getTeamTwoPoints() {
        return teamTwoPoints;
    }

    public void resetPoints() {
        teamOnePoints = 0;
        teamTwoPoints = 0;
    }

    protected void addPointsIfDifferenceGreater(double difference, double filter) {
        if (difference >= filter) {
            teamOnePoints++;
        } else if (difference <= -filter) {
            teamTwoPoints++;
        }
    }

    protected void incrementTeamOnePoints() {
        teamOnePoints++;
    }

    protected void incrementTeamTwoPoints() {
        teamTwoPoints++;
    }

    protected List<Team> getTeams() {
        return teams;
    }

    public static String getWonResult() {
        return WON_RESULT;
    }

    public static String getLostResult() {
        return LOST_RESULT;
    }
}
