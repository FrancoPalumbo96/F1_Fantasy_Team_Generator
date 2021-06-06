import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class F1Fantasy {
    public static void main(String[] args) {

        Tuple<List<Pilot>, List<Constructor>> dataTuple = JsonReader.read();

        List<Pilot> pilots = dataTuple.x;
        List<Constructor> constructors = dataTuple.y;

        BigDecimal maxPrice = new BigDecimal("102.5");
        Pilot[] pilotsArray = new Pilot[pilots.size()];
        pilots.toArray(pilotsArray);

        //checkData(pilots, constructors);

        List<Team> bestTeams = Helper.bestTeams(pilots, constructors, 5, 10, maxPrice);
        printBestTeams(bestTeams);
    }

    private static void printBestTeams(List<Team> teams){
        for (Team t: teams) {
            System.out.println(t);
        }
    }

    private static void checkData(List<Pilot> pilots, List<Constructor> constructors){
        System.out.println("- - - - - - - - - - DATA - - - - - - - - - -");
        for (Pilot p: pilots) {
            System.out.println("Name: " + p.getName() + " Price: " + p.getPrice() + " Points: " + p.getPoints());
        }

        for (Constructor c: constructors) {
            System.out.println("Name: " + c.getName() + " Price: " + c.getPrice() + " Points: " + c.getPoints());
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - -");
    }
}
