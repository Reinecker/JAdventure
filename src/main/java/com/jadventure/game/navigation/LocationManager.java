package com.jadventure.game.navigation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LocationManager {
    INSTANCE;
    private static final String FILE_NAME = "json/locations.json";

    private Map<Coordinate, ILocation> locations = new HashMap<Coordinate, ILocation>();

    private LocationManager() {
        JsonParser parser = new JsonParser();

        try {
            Reader reader = new FileReader(FILE_NAME);

            JsonObject json = parser.parse(reader).getAsJsonObject();

            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                locations.put(new Coordinate(entry.getKey()), loadLocation(entry.getValue().getAsJsonObject()));
            }

            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to load game locations.");
            ex.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Location loadLocation(JsonObject json) {
        Location location = new Location();

        Coordinate coordinate = new Coordinate(json.get("coordinate").getAsString());
        location.setCoordinate(coordinate);
        location.setTitle(json.get("title").getAsString());
        location.setDescription(json.get("description").getAsString());
        location.setLocationType(LocationType.valueOf(json.get("locationType").getAsString()));

        return location;
    }

    public ILocation getInitialLocation() {
        Coordinate coordinate = new Coordinate(0, 0, -1);
        return getLocation(coordinate);
    }

    public ILocation getLocation(Coordinate coordinate) {
        return locations.get(coordinate);
    }
}
