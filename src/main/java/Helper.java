import java.math.BigDecimal;
import java.util.*;

public class Helper {

    //https://hmkcode.com/calculate-find-all-possible-combinations-of-an-array-using-java/
    public static List<Team> bestTeams(List<Pilot> pilots, List<Constructor> constructors,
                                       int pilotsPerTeam, int nOfTeams, BigDecimal maxPrice){

        List<Team> teams = new ArrayList<>(nOfTeams);

        if(pilotsPerTeam > pilots.size()){
            return teams;
        }

        int k = pilotsPerTeam + 1;
        int N = pilots.size() + 1;

        int[] pointers = new int[k];

        int r = 0;
        int i = 0;


        for (Constructor c: constructors) {

            List<Slot> pilotsAndConstructor = new ArrayList<>(pilots);
            pilotsAndConstructor.add(c);

            while (r >= 0) {
                if (i <= (N + (r - k))) {
                    pointers[r] = i;
                    if (r == k - 1) {
                        Optional<Team> optionalTeam = Team.formTeam(getSlotByPointers(
                                pointers, pilotsAndConstructor));
                        if(optionalTeam.isPresent()){
                            Team newTeam = optionalTeam.get();
                            if (maxPrice.compareTo(newTeam.getTeamPrice()) >= 0){
                                if(teams.size() < nOfTeams){
                                    teams.add(newTeam);
                                } else {
                                    teams.sort(Collections.reverseOrder());
                                    int worstTeamIndex = teams.size() - 1 ;
                                    if(teams.get(worstTeamIndex).getTeamPoints() < newTeam.getTeamPoints()){
                                        teams.remove(worstTeamIndex);
                                        teams.add(newTeam);
                                    }
                                }
                            }
                        }
                        i++;
                    } else {
                        i = pointers[r] + 1;
                        r++;
                    }
                }
                else {
                    r--;
                    if (r >= 0)
                        i = pointers[r] + 1;

                }
            }

            r = 0;
            i = 0;

        }
        return teams;
    }

    public static List<Slot> getSlotByPointers(int[] pointers, List<Slot> slots){
        List<Slot> returnSlots = new ArrayList<>();
        for (int p:
             pointers) {
            returnSlots.add(slots.get(p));
        }
        return returnSlots;
    }

    public static Tuple<List<Pilot>, List<Constructor>> pointsByLastRaces(int initialRace, int finalRace){

        String initialFilePath = "src/main/resources/standing_results_race_" + initialRace + ".json";
        String finalFilePath = "src/main/resources/standing_results_race_" + finalRace + ".json";
        Tuple<List<Pilot>, List<Constructor>> initialResults = JsonReader.read(initialFilePath);
        Tuple<List<Pilot>, List<Constructor>> finalResults = JsonReader.read(finalFilePath);

        List<Pilot> pilotsInitialResults = initialResults.x;
        List<Constructor> constructorsInitialResults = initialResults.y;

        List<Pilot> pilotsFinalResults = finalResults.x;
        List<Constructor> constructorsFinalResults = finalResults.y;

        List<Pilot> pilotsIntervalResults = new ArrayList<>();
        List<Constructor> constructorIntervalResults = new ArrayList<>();

        for (Pilot p:pilotsFinalResults) {
            Optional<Pilot> optional = pilotsInitialResults.stream()
                    .filter(pilot -> p.getName().equals(pilot.getName()))
                    .findFirst();
            if (optional.isPresent()){
                int points = p.getPoints() - optional.get().getPoints();
                Pilot intervalPilotResult = new Pilot(p.getName(), p.getPrice(), points);
                pilotsIntervalResults.add(intervalPilotResult);
            } else {
                System.out.println(p.getName() + " will not be considered");
            }
        }

        for (Constructor c:constructorsFinalResults) {
            Optional<Constructor> optional = constructorsInitialResults.stream()
                    .filter(constructor -> c.getName().equals(constructor.getName()))
                    .findFirst();
            if (optional.isPresent()){
                int points = c.getPoints() - optional.get().getPoints();
                Constructor intervalPilotResult = new Constructor(c.getName(), c.getPrice(), points);
                constructorIntervalResults.add(intervalPilotResult);
            } else {
                System.out.println(c.getName() + " will not be considered");
            }
        }

        return new Tuple<>(pilotsIntervalResults, constructorIntervalResults);

    }
}


