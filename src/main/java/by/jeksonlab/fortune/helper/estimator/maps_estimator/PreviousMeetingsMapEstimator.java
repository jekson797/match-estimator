package by.jeksonlab.fortune.helper.estimator.maps_estimator;

import by.jeksonlab.fortune.helper.estimator.AbstractEstimator;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.ArrayList;
import java.util.List;

public class PreviousMeetingsMapEstimator extends AbstractEstimator {

    private static final int PREVIOUS_MEETINGS_WIN_FILTER = 1;
    private String map;

    public PreviousMeetingsMapEstimator(List<Team> teams, String map) {
        super(teams);
        this.map = map;
    }

    public int estimateMapInPreviousMeetings(List<PreviousMatch> previousMeetings) {
        List<PreviousMatch> previousMeetingOnMap = getPreviousMeetingsOnMap(previousMeetings);
        return getPreviousMeetingsMapAdvantage(getWinsDifferenceOnMap(previousMeetingOnMap));
    }

    private List<PreviousMatch> getPreviousMeetingsOnMap(List<PreviousMatch> previousMeetings) {
        List<PreviousMatch> previousMeetingsOnMap = new ArrayList<>();
        for (PreviousMatch previousMeeting : previousMeetings) {
            addPreviousMeetingIfMapsSame(previousMeeting, previousMeetingsOnMap);
        }
        return previousMeetingsOnMap;
    }

    private void addPreviousMeetingIfMapsSame(PreviousMatch previousMeeting,
                                              List<PreviousMatch> previousMeetingsOnMap) {
        if (previousMeeting.getMap().equals(map)) {
            previousMeetingsOnMap.add(previousMeeting);
        }
    }

    private int getWinsDifferenceOnMap(List<PreviousMatch> previousMeetingsOnMap) {
        int difference = 0;
        for (PreviousMatch previousMeeting : previousMeetingsOnMap) {
            difference += changeDifferenceOnMap(previousMeeting);
        }
        return difference;
    }

    private int changeDifferenceOnMap(PreviousMatch previousMeeting) {
        if (previousMeeting.getResult().equals(getWonResult())) {
            return 1;
        } else {
            return -1;
        }
    }

    private int getPreviousMeetingsMapAdvantage(double difference) {
        if (difference >= PREVIOUS_MEETINGS_WIN_FILTER) {
            return 1;
        } else if (difference <= -PREVIOUS_MEETINGS_WIN_FILTER) {
            return -1;
        }
        return 0;
    }
}
