import java.math.BigDecimal;

public abstract class Slot {
    private final String name;
    private final BigDecimal price;
    private final int points;

    public Slot(String name, BigDecimal price, int points) {
        this.name = name;
        this.price = price;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getPoints() {
        return points;
    }


}
