package com.drazisil.destinations;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class LocationTagType implements PersistentDataType<String, Location> {

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<Location> getComplexType() {
        return Location.class;
    }

    @Override
    public String toPrimitive(Location complex, PersistentDataAdapterContext context) {

        World waystoneWorld = complex.getWorld();

        if (waystoneWorld == null) throw new Error("Unable to fetch world for waystone while storing");

        return String.format("%s, %s, %s, %s",
                complex.getWorld().getName(),
                complex.getX(),
                complex.getY(),
                complex.getZ()
        );
    }

    @Override
    public Location fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        String[] parts = primitive.split(",");

        World world = Destinations.plugin.getServer().getWorld(parts[0]);

        if (world == null) {
            // We were unable to locate this world
            throw new Error(String.format("We were unable to locate a world with the id of %s", parts[0]));
        }

        return new Location(world,
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]));
    }
}
