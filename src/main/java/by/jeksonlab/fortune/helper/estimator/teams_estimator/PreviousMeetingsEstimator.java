package by.jeksonlab.fortune.helper.estimator.teams_estimator;

import by.jeksonlab.fortune.helper.estimator.AbstractEstimator;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.List;

public class PreviousMeetingsEstimator extends AbstractEstimator {

    private static final int PREVIOUS_MEETINGS_WON_FILTER = 2;
    private List<PreviousMatch> previousMeetings;

    public PreviousMeetingsEstimator(List<Team> teams, List<PreviousMatch> previousMeetings) {
        super(teams);
        this.previousMeetings = previousMeetings;
    }

    public void estimatePreviousMeetings() {
        int teamOneWonPreviousMeetings = determineWonMatchesNumber(getTeams().get(0));
        int teamTwoWonPreviousMeetings = determineWonMatchesNumber(getTeams().get(1));
        addPointsIfDifferenceGreater(teamOneWonPreviousMeetings - teamTwoWonPreviousMeetings,
                PREVIOUS_MEETINGS_WON_FILTER);
    }

    private int determineWonMatchesNumber(Team team) {
        int wonMatchesNumber = 0;
        for (PreviousMatch previousMeeting : previousMeetings) {
            if (isMatchWasWon(team, previousMeeting)) {
                wonMatchesNumber++;
            }
        }
        return wonMatchesNumber;
    }

    private boolean isMatchWasWon(Team team, PreviousMatch previousMeeting) {
        if (isTeamNameEqualsOpponentNameInPreviousMeeting(team, previousMeeting)) {
            return isResultEqualsLost(previousMeeting);
        } else {
            return isResultEqualsWon(previousMeeting);
        }
    }

    private boolean isTeamNameEqualsOpponentNameInPreviousMeeting(Team team, PreviousMatch previousMeeting) {
        return previousMeeting.getOpponentName().equals(team.getName());
    }

    private boolean isResultEqualsLost(PreviousMatch previousMeeting) {
        return previousMeeting.getResult().equals(getLostResult());
    }

    private boolean isResultEqualsWon(PreviousMatch previousMeeting) {
        return previousMeeting.getResult().equals(getWonResult());
    }
}
