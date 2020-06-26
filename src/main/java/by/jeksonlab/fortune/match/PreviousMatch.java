package by.jeksonlab.fortune.match;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class PreviousMatch {

    private LocalDate date;
    private String opponentName;
    private String opponentPageUrl;
    private String map;
    private String result;

    public PreviousMatch(Map<String, String> matchData, LocalDate date) {
        this.date = date;
        unParseMatchData(matchData);
    }

    private void unParseMatchData(Map<String, String> matchData) {
        opponentName = matchData.get("Opponent");
        opponentPageUrl = matchData.get("Opponent Page Url");
        map = matchData.get("Map");
        result = matchData.get("Result");
    }

    public LocalDate getDate() {
        return date;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getOpponentPageUrl() {
        return opponentPageUrl;
    }

    public String getMap() {
        return map;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreviousMatch that = (PreviousMatch) o;
        return opponentName.equals(that.opponentName) &&
                map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opponentName, map);
    }
}
