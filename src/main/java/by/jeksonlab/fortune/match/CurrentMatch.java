package by.jeksonlab.fortune.match;

import by.jeksonlab.fortune.helper.collector.Collector;
import by.jeksonlab.fortune.helper.informer.Informer;
import by.jeksonlab.fortune.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CurrentMatch {

    private String matchUrl;
    private List<Team> teams;
    private List<String> maps;
    private List<PreviousMatch> previousTeamMeetings = new ArrayList<>();
    private List<PreviousMatch> teamOneCommonMatchesWithRivals = new ArrayList<>();
    private List<PreviousMatch> teamTwoCommonMatchesWithRivals = new ArrayList<>();
    private Set<String> commonRivalsName;
    private Collector collector = new Collector();

    public CurrentMatch(String matchUrl) {
        this.matchUrl = matchUrl;
    }

    public void showTeamsInfo() {
        Informer informer = new Informer(teams, commonRivalsName);
        informer.showTeamsInfo();
        informer.showPreviousTeamMeetings(previousTeamMeetings);
    }

    public void collectMatchInfo() {
        collectTeams();
        collectCommonRivalsName();
        collectPreviousTeamMeetings();
        collectCommonMatchesForBothTeams();
        collectMatchMapsName();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Set<String> getCommonRivalsName() {
        return commonRivalsName;
    }

    public List<PreviousMatch> getPreviousTeamMeetings() {
        return previousTeamMeetings;
    }

    public List<String> getMaps() {
        return maps;
    }

    public List<PreviousMatch> getTeamOneCommonMatchesWithRivals() {
        return teamOneCommonMatchesWithRivals;
    }

    public List<PreviousMatch> getTeamTwoCommonMatchesWithRivals() {
        return teamTwoCommonMatchesWithRivals;
    }

    private void collectTeams() {
        collector.collectMatchTeamsData(matchUrl);
        teams = collector.getTeams();
    }

    private void collectCommonRivalsName() {
        collector.collectCommonRivalTeamsName();
        commonRivalsName = collector.getCommonRivalsTeamsName();
    }

    private void collectPreviousTeamMeetings() {
        if (teams.size() > 2) {
            throw new IllegalArgumentException("Wrong teams amount");
        }
        for (PreviousMatch match : teams.get(0).getPreviousMatches()) {
            if (isMatchCommonForBothTeams(match)) {
                previousTeamMeetings.add(match);
            }
        }
    }

    private boolean isMatchCommonForBothTeams(PreviousMatch match) {
        return match.getOpponentName().equals(teams.get(1).getName());
    }

    private void collectCommonMatchesForBothTeams() {
        teamOneCommonMatchesWithRivals = getCommonPreviousMatchesForTeam(0);
        teamTwoCommonMatchesWithRivals = getCommonPreviousMatchesForTeam(1);
    }

    private List<PreviousMatch> getCommonPreviousMatchesForTeam(int teamIndex) {
        List<PreviousMatch> previousCommonMatches = new ArrayList<>();
        for (PreviousMatch previousMatch : getTeams().get(teamIndex).getPreviousMatches()) {
            addInCollectionIfMatchIsCommon(previousMatch, previousCommonMatches);
        }
        return previousCommonMatches;
    }

    private void addInCollectionIfMatchIsCommon(PreviousMatch previousMatch, List<PreviousMatch> commonMatches) {
        String previousMatchOpponentName = previousMatch.getOpponentName();
        if (commonRivalsName.contains(previousMatchOpponentName)) {
            commonMatches.add(previousMatch);
        }
    }

    private void collectMatchMapsName() {
        maps = collector.getMatchMaps(matchUrl);
    }
}
