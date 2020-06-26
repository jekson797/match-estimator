package by.jeksonlab.fortune.page;

import by.jeksonlab.fortune.team.Player;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamPage extends AbstractPage {

    private static final String TEAM_NAME_LOCATOR = ".profile-team-info > div:nth-child(2)";
    private static final String WORLD_RANK_LOCATOR = ".profile-team-stat > .right";
    private static final String CURRENT_FORM_LOCATOR = ".streak-widget-container";
    private static final String PLAYER_PAGE_LOCATOR = "[href *= /player/]";
    private static final String STATS_PAGE_URL_POSTFIX = "#tab-statsBox";
    private static final String TEAM_PAGE_URL_PART = "/team/";
    private static final String TEAM_PREVIOUS_MATCHES_PAGE_URL_PART = "/stats/teams/matches/";
    private static final String WIN_SIGN = "W";
    private static final String SHARP_SIGN = "#";
    private static final String DASH_SIGN = "-";

    public Team collectGeneralTeamData() {
        return new Team.TeamBuilder()
                .buildName(getName())
                .buildWorldRank(getWorldRank())
                .buildCurrentForm(getCurrentForm())
                .buildPlayers(getPlayersData())
                .buildMapsStats(getMapsStatsData())
                .buildPreviousMatches(getPreviousMatchesData())
                .build();
    }

    public Team collectTeamWorldRankAndPlayersData() {
        return new Team.TeamBuilder()
                .buildName(getName())
                .buildWorldRank(getWorldRank())
                .buildPlayers(getPlayersData())
                .build();
    }

    private String getName() {
        return getTextFromLocator(TEAM_NAME_LOCATOR);
    }

    private int getWorldRank() {
        String stringWithRank = getTextFromLocator(WORLD_RANK_LOCATOR);
        if (stringWithRank.equals(DASH_SIGN)) {
            return 250;
        } else {
            return Integer.parseInt(cutSharpFromString(stringWithRank));
        }
    }

    private String cutSharpFromString(String string) {
        return string.replace(SHARP_SIGN, "");
    }

    private double getCurrentForm() {
        String[] teamCurrentFormText = getTextFromLocator(CURRENT_FORM_LOCATOR).split(" ");
        if (teamCurrentFormText.length >= 10) {
            return convertFormFromTextToNumber(teamCurrentFormText);
        }
        return 0;
    }

    private double convertFormFromTextToNumber(String[] currentFormText) {
        int totalWins = 0;
        for (int i = 2; i < 10; i++) {
            if (currentFormText[i].equals(WIN_SIGN)) {
                totalWins++;
            }
        }
        return (double) totalWins / 8 * 100;
    }

    private List<Player> getPlayersData() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < getElementsByLocator(PLAYER_PAGE_LOCATOR).size(); i++) {
            String url = getBaseUrl() + getHrefAttribute(PLAYER_PAGE_LOCATOR, i);
            players.add(getPlayerFromPlayerPage(url));
        }
        return players;
    }

    private Player getPlayerFromPlayerPage(String playerPageUrl) {
        PlayerPage playerPage = new PlayerPage();
        playerPage.open(playerPageUrl);
        playerPage.collectPlayerData();
        return new Player(playerPage.getName(), playerPage.getRating());
    }

    private Map<String, Double> getMapsStatsData() {
        TeamMapsStatsPage statsPage = new TeamMapsStatsPage();
        statsPage.open(getDocument().location() + STATS_PAGE_URL_POSTFIX);
        statsPage.collectMapsStats();
        return statsPage.getMapsStats();
    }

    private List<PreviousMatch> getPreviousMatchesData() {
        TeamPreviousMatchesPage previousMatchesPage = new TeamPreviousMatchesPage();
        String url = getAllStatsPageUrl();
        previousMatchesPage.open(url);
        previousMatchesPage.collectCertainPreviousMatches();
        return previousMatchesPage.getPreviousMatches();
    }

    private String getAllStatsPageUrl() {
        return getDocument().location().replace(TEAM_PAGE_URL_PART, TEAM_PREVIOUS_MATCHES_PAGE_URL_PART);
    }
}
