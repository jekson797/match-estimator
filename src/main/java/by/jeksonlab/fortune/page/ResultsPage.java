package by.jeksonlab.fortune.page;

import java.util.ArrayList;
import java.util.List;

public class ResultsPage extends AbstractPage {

    private static final String FINISHED_MATCH_URL_LOCATOR = ".results-holder > .results-all .result-con > a";
    private static final String FINISHED_MATCH_SCORE_LOCATOR = ".results-holder > .results-all .result-con .result-score";
    private List<String> finishedMatchesUrls = new ArrayList<>();
    private List<String> finishedMatchesScore = new ArrayList<>();

    public void collectFinishedMatchesUrls() {
        for (int i = 0; i < getElementsByLocator(FINISHED_MATCH_URL_LOCATOR).size(); i++) {
            String url = getBaseUrl() + getHrefAttribute(FINISHED_MATCH_URL_LOCATOR, i);
            finishedMatchesUrls.add(url);
        }
    }

    public void collectFinishedMatchesScore() {
        for (int i = 0; i < getElementsByLocator(FINISHED_MATCH_SCORE_LOCATOR).size(); i++) {
            String score = getTextFromLocator(FINISHED_MATCH_SCORE_LOCATOR, i);
            finishedMatchesScore.add(score);
        }
    }

    public List<String> getFinishedMatchesUrls() {
        return finishedMatchesUrls;
    }

    public List<String> getFinishedMatchesScore() {
        return finishedMatchesScore;
    }
}
