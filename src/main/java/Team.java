import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Team implements Comparable<Team> {
    private List<Pilot> teamPilots;
    private Constructor constructor;
    private int teamPoints;

    public Team(List<Pilot> pilots, Constructor constructor){
        this.teamPilots = pilots;
        this.constructor = constructor;

        int auxPoints = 0;
        for (Slot slot: teamPilots) {
            auxPoints += slot.getPoints();
        }
        auxPoints += constructor.getPoints();
        this.teamPoints = auxPoints;
    }

    public Team(List<Pilot> teamPilots, Constructor constructor, int teamPoints) {
        this.teamPilots = teamPilots;
        this.constructor = constructor;
        this.teamPoints = teamPoints;
    }

    public List<Pilot> getTeamPilots() {
        return teamPilots;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public int getTeamPoints() {
        return teamPoints;
    }

    public BigDecimal getTeamPrice(){
        BigDecimal totalPrice = new BigDecimal("0");
        for (Pilot p:
             teamPilots) {
            totalPrice = totalPrice.add(p.getPrice());
        }
        return totalPrice.add(constructor.getPrice());
    }

    @Override
    public String toString() {

        String answer = "";

        for (Pilot p:
             teamPilots) {
            answer += p.getName() + " ";
        }
        answer += "| ";
        answer += constructor.getName() + " | ";
        answer += teamPoints;

        return answer;
    }

    public static Optional<Team> formTeam(List<Slot> slots){
        List<Pilot> pilots = new ArrayList<>();
        Constructor constructor = null;
        for (Slot s: slots) {
            if(s.getClass().equals(Pilot.class)){
                pilots.add((Pilot) s);
            } else if(s.getClass().equals(Constructor.class)) {
                if(constructor == null){
                    constructor = (Constructor) s;
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }
        }
        if(constructor == null){
            return Optional.empty();
        }
        return Optional.of(new Team(pilots, constructor));
    }

    @Override
    public int compareTo(Team o) {
        return Integer.compare(this.getTeamPoints(), o.getTeamPoints());
    }
}
