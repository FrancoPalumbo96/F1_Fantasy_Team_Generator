import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
    private static Optional<Slot> parseSlotObject(JSONObject jsonSlot)
    {
        String slotClass = "";

        JSONObject slotObject = (JSONObject) jsonSlot.get("pilot");
        if(slotObject == null){
            slotObject = (JSONObject) jsonSlot.get("constructor");
            slotClass = "Constructor";
        } else {
            slotClass = "Pilot";
        }

        String name = (String) slotObject.get("name");
        String auxPrice = "" + slotObject.get("price");

        BigDecimal price = new BigDecimal(auxPrice);

        int points = ((Long) slotObject.get("points")).intValue();

        switch (slotClass){
            case "Pilot":
                return Optional.of(new Pilot(name, price, points));
            case "Constructor":
                return Optional.of(new Constructor(name, price, points));
            default:
                return Optional.empty();
        }

    }

    public static Tuple<List<Pilot>, List<Constructor>> read(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        List<Pilot> pilots = new ArrayList<>();
        List<Constructor> constructors = new ArrayList<>();

        try (FileReader reader = new FileReader("src/main/resources/data.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray slots = (JSONArray) obj;
            System.out.println(slots);


            //Iterate over employee array
            slots.forEach( jsonSlot -> {
                        Optional<Slot> optionalSlot = parseSlotObject( (JSONObject) jsonSlot );
                        if(optionalSlot.isPresent()){
                            Slot slot = optionalSlot.get();
                            String slotClass = String.valueOf(slot.getClass());
                            switch (slotClass){
                                case "class Pilot":
                                    pilots.add((Pilot) slot);
                                    break;
                                case "class Constructor":
                                    constructors.add((Constructor) slot);
                                    break;
                            }
                        }
                    }
            );

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Total Pilots: " + pilots.size());
        System.out.println("Total Constructors: " + constructors.size());

        return new Tuple<>(pilots, constructors);
    }

}

