import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
}


