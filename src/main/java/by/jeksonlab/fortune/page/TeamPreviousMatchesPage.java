package by.jeksonlab.fortune.page;

import by.jeksonlab.fortune.match.PreviousMatch;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamPreviousMatchesPage extends AbstractPage {

    private static final String PREVIOUS_MATCH_DATE_LOCATOR = "table .time > a";
    private static final String PREVIOUS_MATCH_OPPONENT_LOCATOR = "table .smartphone-only + td a";
    private static final String PREVIOUS_MATCH_MAP_LOCATOR = ".statsMapPlayed";
    private static final String PREVIOUS_MATCH_RESULT_LOCATOR = "[class *= text-center match]";
    private static final String DATE_PATTERN = "dd/MM/yy";
    private static final String TEAM_PAGE_URL_PART = "/team/";
    private static final String TEAM_OVERVIEW_PAGE_URL_PART = "/stats/teams/";
    private static final int DATE_FILTER = 3;
    private List<PreviousMatch> previousMatches = new ArrayList<>();

    public void collectCertainPreviousMatches() {
        Elements previousMatchesDatesElements = getElementsByLocator(PREVIOUS_MATCH_DATE_LOCATOR);
        LocalDate dateFilter = LocalDate.now().minus(Period.ofMonths(DATE_FILTER));
        for (int i = 0; i < previousMatchesDatesElements.size(); i++) {
            LocalDate matchDate = determineMatchDate(previousMatchesDatesElements.get(i));
            if (isMatchDateMeetRequirements(matchDate, dateFilter)) {
                Map<String, String> matchData = putPreviousMatchDataInMap(i);
                previousMatches.add(new PreviousMatch(matchData, matchDate));
            }
        }
    }

    public List<PreviousMatch> getPreviousMatches() {
        return previousMatches;
    }

    private LocalDate determineMatchDate(Element dateElement) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return LocalDate.parse(dateElement.text(), formatter);
    }

    private boolean isMatchDateMeetRequirements(LocalDate matchDate, LocalDate filterDate) {
        return matchDate.isAfter(filterDate);
    }

    private Map<String, String> putPreviousMatchDataInMap(int index) {
        Map<String, String> matchData = new HashMap<>();
        matchData.put("Result", getTextFromLocator(PREVIOUS_MATCH_RESULT_LOCATOR, index));
        matchData.put("Opponent", getTextFromLocator(PREVIOUS_MATCH_OPPONENT_LOCATOR, index));
        matchData.put("Map", getTextFromLocator(PREVIOUS_MATCH_MAP_LOCATOR, index));
        matchData.put("Opponent Page Url", getOpponentTeamPageUrl(index));
        return matchData;
    }

    private String getOpponentTeamPageUrl(int index) {
        String url = getBaseUrl() + getHrefAttribute(PREVIOUS_MATCH_OPPONENT_LOCATOR, index);
        return url.replace(TEAM_OVERVIEW_PAGE_URL_PART, TEAM_PAGE_URL_PART);
    }
}
