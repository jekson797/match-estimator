package by.jeksonlab.fortune.team;

import by.jeksonlab.fortune.match.PreviousMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamStatistic {

    private int worldRank;
    private double averageRating;
    private double winPercentage;
    private double currentForm;
    private List<PreviousMatch> wonMatches = new ArrayList<>();
    private List<PreviousMatch> lostMatches = new ArrayList<>();
    private Map<String, Double> mapsStats;
    private Map<String, Double> averageStatistic;

    public int getWorldRank() {
        return worldRank;
    }

    public void setWorldRank(int worldRank) {
        this.worldRank = worldRank;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public double getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(double currentForm) {
        this.currentForm = currentForm;
    }

    public Map<String, Double> getMapsStats() {
        return mapsStats;
    }

    public void setMapsStats(Map<String, Double> mapsStats) {
        this.mapsStats = mapsStats;
    }

    public Map<String, Double> getAverageStatistic() {
        return averageStatistic;
    }

    public void setAverageStatistic(Map<String, Double> averageStatistic) {
        this.averageStatistic = averageStatistic;
    }

    public List<PreviousMatch> getWonMatches() {
        return wonMatches;
    }

    public void setWonMatches(List<PreviousMatch> wonMatches) {
        this.wonMatches = wonMatches;
    }

    public List<PreviousMatch> getLostMatches() {
        return lostMatches;
    }

    public void setLostMatches(List<PreviousMatch> lostMatches) {
        this.lostMatches = lostMatches;
    }
}
