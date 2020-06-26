package by.jeksonlab.fortune.page;

import by.jeksonlab.fortune.team.Team;

import java.util.ArrayList;
import java.util.List;

public class MatchPage extends AbstractPage {

    private static final String TEAM_PAGE_URL_LOCATOR = ".team a[href *= /team/]";
    private static final String MAP_NAME_LOCATOR = ".map-name-holder > .mapname";

    public List<Team> collectInfoAboutTeams() {
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < getElementsByLocator(TEAM_PAGE_URL_LOCATOR).size(); i++) {
            String url = getBaseUrl() + getHrefAttribute(TEAM_PAGE_URL_LOCATOR, i);
            teams.add(getTeamFromTeamPage(url));
        }
        return teams;
    }

    public List<String> getMatchMapsName() {
        List<String> maps = new ArrayList<>();
        for (int i = 0; i < getElementsByLocator(MAP_NAME_LOCATOR).size(); i++) {
            maps.add(getTextFromLocator(MAP_NAME_LOCATOR, i));
        }
        return maps;
    }

    private Team getTeamFromTeamPage(String teamPageUrl) {
        TeamPage teamPage = new TeamPage();
        teamPage.open(teamPageUrl);
        return teamPage.collectGeneralTeamData();
    }
}
