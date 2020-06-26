package by.jeksonlab.fortune.match;

import by.jeksonlab.fortune.helper.estimator.teams_estimator.TeamsEstimator;
import by.jeksonlab.fortune.page.ResultsPage;
import by.jeksonlab.fortune.team.Team;

import java.util.ArrayList;
import java.util.List;

public class MatchesResult {

    private static final String RESULT_PAGE_URL = "https://www.hltv.org/results";
    private List<CurrentMatch> matches = new ArrayList<>();
    private List<Integer> winnerIndexes = new ArrayList<>();
    private List<TeamsEstimator> teamsEstimation = new ArrayList<>();

    public void collectFinishedMatchesData() {
        ResultsPage resultsPage = new ResultsPage();
        resultsPage.open(RESULT_PAGE_URL);
        List<String> finishedMatchesUrls = getFinishedMatchesUrls(resultsPage);
        List<String> finishedMatchesScores = getFinishedMatchesScore(resultsPage);
        collectFinishedMatchesInfoAndEstimation(finishedMatchesUrls, finishedMatchesScores);
    }

    public void showFinishedMatchesInfo() {
        for (int i = 0; i < matches.size(); i++) {
            showMatchInfo(teamsEstimation.get(i), matches.get(i).getTeams(), winnerIndexes.get(i));
        }
    }

    private List<String> getFinishedMatchesUrls(ResultsPage resultsPage) {
        resultsPage.collectFinishedMatchesUrls();
        return resultsPage.getFinishedMatchesUrls();
    }

    private List<String> getFinishedMatchesScore(ResultsPage resultsPage) {
        resultsPage.collectFinishedMatchesScore();
        return resultsPage.getFinishedMatchesScore();
    }

    private void collectFinishedMatchesInfoAndEstimation(List<String> finishedMatchesUrls,
                                                         List<String> finishedMatchesScores) {
        for (int i = 0; i < finishedMatchesUrls.size(); i++) {
            collectMatchInfo(finishedMatchesUrls, i);
            collectWinnerIndex(finishedMatchesScores, i);
            collectTeamsEstimation(i);
        }
    }

    private void collectMatchInfo(List<String> finishedMatchesUrls, int index) {
        String matchUrl = finishedMatchesUrls.get(index);
        CurrentMatch currentMatch = new CurrentMatch(matchUrl);
        currentMatch.collectMatchInfo();
        matches.add(currentMatch);
    }

    private void collectWinnerIndex(List<String> finishedMatchesScores, int index) {
        winnerIndexes.add(determineWinnerIndex(finishedMatchesScores.get(index)));
    }

    private void collectTeamsEstimation(int index) {
        TeamsEstimator estimator = new TeamsEstimator(matches.get(index).getTeams());
        estimator.estimateTeams();
        teamsEstimation.add(estimator);
    }

    private int determineWinnerIndex(String score) {
        String[] scoreText = score.split(" - ");
        int teamOneScore = Integer.parseInt(scoreText[0]);
        int teamTwoScore = Integer.parseInt(scoreText[1]);
        if (teamOneScore > teamTwoScore) {
            return 0;
        } else {
            return 1;
        }
    }

    private void showMatchInfo(TeamsEstimator estimator, List<Team> teams, int winnerIndex) {
        System.out.printf("Team: %s (Points: %d) VS Team: %s (Points: %d) || Winner: %s\n",
                teams.get(0).getName(), estimator.getTeamOnePoints(),
                teams.get(1).getName(), estimator.getTeamTwoPoints(),
                teams.get(winnerIndex).getName());
    }
}
