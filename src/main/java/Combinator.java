import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Combinator {

    public static Team[] teams = new Team[3];
    //TODO probably memory problems, put it in json
    public static Pilot[] pilots;


    public static Constructor team;
    public static int points = 0;
    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    static void combinationUtil(Pilot[] arr, int n, int r, int index,
                                Pilot[] data, int i, BigDecimal maxPrice, Constructor constructor)
    {

        // Current combination is ready to be printed, print it
        if (index == r)
        {
            BigDecimal totalPrice = new BigDecimal("0");
            int totalPoints = 0;
            for (int j=0; j<r; j++) {
                //System.out.print(data[j].getName() + " ");
                totalPoints += data[j].getPoints();
                totalPrice = totalPrice.add(data[j].getPrice());
            }
            totalPrice = totalPrice.add(constructor.getPrice());

            if(totalPrice.compareTo(maxPrice) > 0){
                return;
            }

            totalPoints += constructor.getPoints();

            if(totalPoints > points) {
                points = totalPoints;
                pilots = data;
                team = constructor;



                for (Pilot pilot: pilots) {
                    System.out.print(pilot.getName());
                    System.out.print(" ");
                }
                System.out.print(Combinator.team.getName() + " ");
                System.out.println(": " + Combinator.points);
            }
            return;
        }

        // When no more elements are there to put in data[]
        if (i >= n)
            return;

        // current is included, put next at next location
        data[index] = arr[i];
        combinationUtil(arr, n, r, index+1, data, i+1, maxPrice, constructor);

        // current is excluded, replace it with next (Note that
        // i+1 is passed, but index is not changed)
        combinationUtil(arr, n, r, index, data, i+1, maxPrice, constructor);
    }

    static void combinationUtil2(Pilot[] arr, int n, int r, int index,
                                Pilot[] data, int i, BigDecimal maxPrice, Constructor constructor)
    {

        // Current combination is ready to be printed, print it
        if (index == r)
        {
            BigDecimal totalPrice = new BigDecimal("0");
            int totalPoints = 0;
            for (int j=0; j<r; j++) {
                //System.out.print(data[j].getName() + " ");
                totalPoints += data[j].getPoints();
                totalPrice = totalPrice.add(data[j].getPrice());
            }
            totalPrice = totalPrice.add(constructor.getPrice());

            if(totalPrice.compareTo(maxPrice) > 0){
                return;
            }

            totalPoints += constructor.getPoints();
            boolean print = false;
            if(teams[2] == null){
                Team team = new Team(Arrays.asList(data), constructor, totalPoints);
                teams[2] = team;
                print = true;
            } else if(teams[1] == null){
                Team team = new Team(Arrays.asList(data), constructor, totalPoints);
                teams[1] = team;
                print = true;
            } else if(teams[0] == null){
                Team team = new Team(Arrays.asList(data), constructor, totalPoints);
                teams[0] = team;
                print = true;
            } else {
                if (totalPoints > teams[2].getTeamPoints() && totalPoints < teams[1].getTeamPoints()) {
                    Team team = new Team(Arrays.asList(data), constructor, totalPoints);
                    teams[2] = team;
                    print = true;
                }
                if (totalPoints > teams[1].getTeamPoints() && totalPoints < teams[0].getTeamPoints()) {
                    Team team = new Team(Arrays.asList(data), constructor, totalPoints);
                    teams[1] = team;
                    print = true;
                }
                if (totalPoints > teams[0].getTeamPoints()) {
                    Team team = new Team(Arrays.asList(data), constructor, totalPoints);
                    teams[0] = team;
                    print = true;
                }
            }
            if(print){
                for (int t = 0; t<teams.length; t++){
                    System.out.println(teams[t]);
                }
            }
            return;
        }

        // When no more elements are there to put in data[]
        if (i >= n)
            return;

        // current is included, put next at next location
        data[index] = arr[i];
        combinationUtil2(arr, n, r, index+1, data, i+1, maxPrice, constructor);

        // current is excluded, replace it with next (Note that
        // i+1 is passed, but index is not changed)
        combinationUtil2(arr, n, r, index, data, i+1, maxPrice, constructor);
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void printCombination(Pilot[] arr, List<Constructor> constructors, int n, int r, BigDecimal maxPrice)
    {
        for (Constructor c: constructors) {
            // A temporary array to store all combination one by one
            Pilot[] data =new Pilot[r];

            // Print all combination using temprary array 'data[]'
            combinationUtil(arr, n, r, 0, data, 0, maxPrice, c);
        }
    }

    static void printCombination2(Pilot[] arr, List<Constructor> constructors, int n, int r, BigDecimal maxPrice)
    {
        for (Constructor c: constructors) {
            // A temporary array to store all combination one by one
            Pilot[] data =new Pilot[r];

            // Print all combination using temprary array 'data[]'
            combinationUtil2(arr, n, r, 0, data, 0, maxPrice, c);
        }
    }


}
