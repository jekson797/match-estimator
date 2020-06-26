package by.jeksonlab.fortune;

import by.jeksonlab.fortune.helper.estimator.teams_estimator.CommonRivalsDefeatingEstimator;
import by.jeksonlab.fortune.helper.estimator.maps_estimator.MapsEstimator;
import by.jeksonlab.fortune.helper.estimator.teams_estimator.PreviousMeetingsEstimator;
import by.jeksonlab.fortune.helper.estimator.teams_estimator.TeamsEstimator;
import by.jeksonlab.fortune.helper.reader.ConsoleReader;
import by.jeksonlab.fortune.match.CurrentMatch;
import by.jeksonlab.fortune.match.PreviousMatch;
import by.jeksonlab.fortune.team.Team;

import java.util.List;
import java.util.Set;

public class Main {

    private static final String ANSWER_YES = "yes";
    private static final String ANSWER_NO = "no";

    public static void main(String[] args) {
        ConsoleReader reader = new ConsoleReader();
        reader.createReader();
        boolean isWorking = true;
        while (isWorking) {
            CurrentMatch match = collectMatchInfo(reader);
            estimateTeams(match.getTeams());
            estimateCommonRivals(match.getTeams(), match.getCommonRivalsName());
            estimatePreviousMeetingsAndShowPoints(match.getTeams(), match.getPreviousTeamMeetings());
            estimateMatchMapsAndShowMapsPoints(match);
            isWorking = proposeToContinue(reader);
        }
        reader.closeReader();
    }

    private static CurrentMatch collectMatchInfo(ConsoleReader reader) {
        System.out.print("Enter match url from hltv.org: ");
        String url = reader.readLineFromConsole();
        CurrentMatch match = new CurrentMatch(url);
        System.out.println("Collecting match info...");
        match.collectMatchInfo();
        return match;
    }

    private static void estimateTeams(List<Team> teams) {
        TeamsEstimator teamsEstimator = new TeamsEstimator(teams);
        teamsEstimator.estimateTeams();
    }

    private static void estimateCommonRivals(List<Team> teams, Set<String> commonRivalsName) {
        CommonRivalsDefeatingEstimator defeatingEstimator = new CommonRivalsDefeatingEstimator(teams, commonRivalsName);
        defeatingEstimator.estimateCommonRivalsDefeating();
    }

    private static void estimatePreviousMeetingsAndShowPoints(List<Team> teams, List<PreviousMatch> previousMeetings) {
        PreviousMeetingsEstimator previousMeetingsEstimator =
                new PreviousMeetingsEstimator(teams, previousMeetings);
        previousMeetingsEstimator.estimatePreviousMeetings();
        previousMeetingsEstimator.showPoints();
        previousMeetingsEstimator.resetPoints();
    }

    private static void estimateMatchMapsAndShowMapsPoints(CurrentMatch match) {
        MapsEstimator mapsEstimator = new MapsEstimator(match.getTeams(), match);
        mapsEstimator.estimateMatchMaps();
        mapsEstimator.showMapsPoints();
    }

    private static boolean proposeToContinue(ConsoleReader reader) {
        while (true) {
            System.out.print("Do you want to continue? (yes/no): ");
            String answer = reader.readLineFromConsole().toLowerCase();
            if (answer.equals(ANSWER_YES)) {
                return true;
            } else if (answer.equals(ANSWER_NO)) {
                return false;
            }
        }
    }
}
