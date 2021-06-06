import java.math.BigDecimal;

public class Pilot extends Slot implements Comparable<Pilot>{
    public Pilot(String name, BigDecimal price, int points) {
        super(name, price, points);
    }

    @Override
    public int compareTo(Pilot o) {
        return Integer.compare(this.getPoints(), o.getPoints());
    }
}
