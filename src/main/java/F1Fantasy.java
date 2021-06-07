import java.math.BigDecimal;
import java.util.List;

public class F1Fantasy {
    public static void main(String[] args) {

        //TODO make raceNumber a globar variable or add to json
        //TODO make the app runnable from one file
        //TODO add command to make the app from a promt message

        int raceNumber = 6;
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


        System.out.println("---------------------------------------------");
        int initialRace = 5;
        int finalRace = 6;
        System.out.println("Best Team Last Races from " + initialRace + " to " + finalRace);

        dataTuple = Helper.pointsByLastRaces(initialRace, finalRace);
        pilots = dataTuple.x;
        constructors = dataTuple.y;

        bestTeams = Helper.bestTeams(pilots, constructors, 5, 10, maxPrice);
        printBestTeams(bestTeams);
    }

    private static void printBestTeams(List<Team> teams){
        for (int i = 0; i<teams.size(); i++){
            Team t = teams.get(i);
            t.sortPilots();
            System.out.println((i+1) +": "+t);
        }
    }

    public static void checkData(List<Pilot> pilots, List<Constructor> constructors){
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
