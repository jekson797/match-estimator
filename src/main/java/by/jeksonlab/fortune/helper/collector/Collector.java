package by.jeksonlab.fortune.helper.collector;

import by.jeksonlab.fortune.helper.analyzer.TeamAnalyzer;
import by.jeksonlab.fortune.page.MatchPage;
import by.jeksonlab.fortune.page.TeamPage;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Collector {

    private static final String LOST_RESULT = "L";
    private List<Team> teams;
    private Set<String> commonRivalsTeamsName = new HashSet<>();
    private TeamAnalyzer teamAnalyzer = new TeamAnalyzer();

    public void collectMatchTeamsData(String matchUrl) {
        MatchPage match = new MatchPage();
        match.open(matchUrl);
        teams = match.collectInfoAboutTeams();
        setAdditionalTeamsInfo();
    }

    public void collectCommonRivalTeamsName() {
        Set<String> teamOneRivalsName = getTeamRivalsName(teams.get(0));
        Set<String> teamTwoRivalsName = getTeamRivalsName(teams.get(1));
        for (String rivalName : teamOneRivalsName) {
            if (teamTwoRivalsName.contains(rivalName)) {
                commonRivalsTeamsName.add(rivalName);
            }
        }
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Set<String> getCommonRivalsTeamsName() {
        return commonRivalsTeamsName;
    }

    public List<String> getMatchMaps(String matchUrl) {
        MatchPage matchPage = new MatchPage();
        matchPage.open(matchUrl);
        return matchPage.getMatchMapsName();
    }

    private void setAdditionalTeamsInfo() {
        for (Team team : teams) {
            collectTeamOpponents(team);
            setLostAndWonMatches(team);
            setTeamAverageStatistic(team);
            setTeamWinPercentage(team);
            setTeamAveragePlayersRating(team);
        }
    }

    private Set<String> getTeamRivalsName(Team team) {
        Set<String> rivalsName = new HashSet<>();
        for (Team opponent : team.getOpponents()) {
            rivalsName.add(opponent.getName());
        }
        return rivalsName;
    }

    private void collectTeamOpponents(Team team) {
        List<Team> opponents = new ArrayList<>();
        Set<String> usedLinks = new HashSet<>();
        for (PreviousMatch match : team.getPreviousMatches()) {
            collectOpponentData(opponents, usedLinks, match);
        }
        team.setOpponents(opponents);
    }

    private void collectOpponentData(List<Team> opponents, Set<String> usedLinks, PreviousMatch match) {
        String opponentTeamPageUrl = match.getOpponentPageUrl();
        if (isTeamPageNotScanned(opponentTeamPageUrl, usedLinks)) {
            scanOpponentTeamPage(opponents, usedLinks, opponentTeamPageUrl);
        }
    }

    private boolean isTeamPageNotScanned(String teamPageUrl, Set<String> usedLinks) {
        return !usedLinks.contains(teamPageUrl);
    }

    private void scanOpponentTeamPage(List<Team> opponents, Set<String> usedLinks, String opponentTeamPageUrl) {
        Team opponentTeam = getOpponentTeamFromTeamPage(opponentTeamPageUrl);
        setTeamAveragePlayersRating(opponentTeam);
        opponents.add(opponentTeam);
        usedLinks.add(opponentTeamPageUrl);
    }

    private Team getOpponentTeamFromTeamPage(String opponentTeamPageUrl) {
        TeamPage teamPage = new TeamPage();
        teamPage.open(opponentTeamPageUrl);
        return teamPage.collectTeamWorldRankAndPlayersData();
    }

    private void setTeamAveragePlayersRating(Team team) {
        team.setAverageRating(teamAnalyzer.getTeamAverageRating(team));
    }

    private void setTeamAverageStatistic(Team team) {
        team.setAverageStatistic(teamAnalyzer.getTeamPreviousMatchesAverageStatistic(team));
    }

    private void setTeamWinPercentage(Team team) {
        team.setWinPercentage(teamAnalyzer.getWinPercentage(team));
    }

    public void setLostAndWonMatches(Team team) {
        List<PreviousMatch> wonMatches = new ArrayList<>();
        List<PreviousMatch> lostMatches = new ArrayList<>();
        separatePreviousMatchesOnLostAndWonMatches(team, wonMatches, lostMatches);
        team.setWonMatches(wonMatches);
        team.setLostMatches(lostMatches);
    }

    private void separatePreviousMatchesOnLostAndWonMatches(Team team,
                                                            List<PreviousMatch> wonMatches,
                                                            List<PreviousMatch> lostMatches) {
        for (PreviousMatch match : team.getPreviousMatches()) {
            addMatchInAppropriateList(match, wonMatches, lostMatches);
        }
    }

    private void addMatchInAppropriateList(PreviousMatch match,
                                           List<PreviousMatch> wonMatches,
                                           List<PreviousMatch> lostMatches) {
        if (isMatchWasLost(match)) {
            lostMatches.add(match);
        } else {
            wonMatches.add(match);
        }
    }

    private boolean isMatchWasLost(PreviousMatch match) {
        return match.getResult().equals(LOST_RESULT);
    }
}
