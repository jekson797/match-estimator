package by.jeksonlab.fortune.team;

import by.jeksonlab.fortune.match.PreviousMatch;

import java.util.List;
import java.util.Map;

public class Team {

    private String name;
    private List<Player> players;
    private List<PreviousMatch> previousMatches;
    private List<Team> opponents;
    private TeamStatistic teamStatistic = new TeamStatistic();

    public Team(TeamBuilder builder) {
        name = builder.name;
        teamStatistic.setWorldRank(builder.worldRank);
        teamStatistic.setCurrentForm(builder.currentForm);
        teamStatistic.setMapsStats(builder.mapsStats);
        players = builder.players;
        previousMatches = builder.previousMatches;
    }

    public TeamStatistic getTeamStatistic() {
        return teamStatistic;
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<PreviousMatch> getPreviousMatches() {
        return previousMatches;
    }

    public void setAverageRating(double averageRating) {
        teamStatistic.setAverageRating(averageRating);
    }

    public void setAverageStatistic(Map<String, Double> averageStatistic) {
        teamStatistic.setAverageStatistic(averageStatistic);
    }

    public void setWinPercentage(double winPercentage) {
        teamStatistic.setWinPercentage(winPercentage);
    }

    public List<Team> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Team> opponents) {
        this.opponents = opponents;
    }

    public void setWonMatches(List<PreviousMatch> wonMatches) {
        teamStatistic.setWonMatches(wonMatches);
    }

    public void setLostMatches(List<PreviousMatch> lostMatches) {
        teamStatistic.setLostMatches(lostMatches);
    }

    public static class TeamBuilder {

        private String name;
        private int worldRank;
        private double currentForm;
        private List<Player> players;
        private Map<String, Double> mapsStats;
        private List<PreviousMatch> previousMatches;

        public TeamBuilder buildName(String name) {
            this.name = name;
            return this;
        }

        public TeamBuilder buildWorldRank(int worldRank) {
            this.worldRank = worldRank;
            return this;
        }

        public TeamBuilder buildCurrentForm(double currentForm) {
            this.currentForm = currentForm;
            return this;
        }

        public TeamBuilder buildPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public TeamBuilder buildMapsStats(Map<String, Double> mapsStats) {
            this.mapsStats = mapsStats;
            return this;
        }

        public TeamBuilder buildPreviousMatches(List<PreviousMatch> previousMatches) {
            this.previousMatches = previousMatches;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }
}