import java.math.BigDecimal;
import java.util.List;

public class F1Fantasy {
    public static void main(String[] args) {

        int raceNumber = 5;
        String filePath = "src/main/resources/standing_results_race_" + raceNumber + ".json";
        Tuple<List<Pilot>, List<Constructor>> dataTuple = JsonReader.read(filePath);

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
        System.out.println("Total Pilots: " + pilots.size());
        System.out.println("Total Constructors: " + constructors.size());
        for (Pilot p: pilots) {
            System.out.println("Name: " + p.getName() + " Price: " + p.getPrice() + " Points: " + p.getPoints());
        }

        for (Constructor c: constructors) {
            System.out.println("Name: " + c.getName() + " Price: " + c.getPrice() + " Points: " + c.getPoints());
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - -");
    }
}
