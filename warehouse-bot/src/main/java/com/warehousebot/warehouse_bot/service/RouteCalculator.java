package com.warehousebot.warehouse_bot.service;

import com.warehousebot.warehouse_bot.model.Location;

import java.util.*;

public class RouteCalculator {


    private static List<Location> expandManhattanPath(List<Location> points) {

        List<Location> full = new ArrayList<>();
        if (points.isEmpty()) return full;

        Location cur = points.get(0);
        full.add(cur);

        for (int i = 1; i < points.size(); i++) {

            Location next = points.get(i);
            // първо horizontal move
            while (cur.getX() != next.getX()) {

                int stepX = Integer.signum(next.getX() - cur.getX());
                cur = new Location(cur.getX() + stepX, cur.getY());
                full.add(cur);

            }
            // после vertical move
            while (cur.getY() != next.getY()) {

                int stepY = Integer.signum(next.getY() - cur.getY());
                cur = new Location(cur.getX(), cur.getY() + stepY);
                full.add(cur);

            }

        }

        return full;
    }

    public static List<Location> calculateRoute(Collection<Location> pickLocations) {

        List<Location> route = new ArrayList<>();

        Location current = new Location(0, 0);
        route.add(current);

        Set<Location> toVisit = new HashSet<>(pickLocations);

        while(!toVisit.isEmpty()){

            Location nearest = null;
            int bestDist = Integer.MAX_VALUE;

            for(Location loc : toVisit){

                int dist = current.manhattanDistance(loc);

                if (dist < bestDist || (dist == bestDist &&
                        (loc.getX() < nearest.getX() ||
                                (loc.getX() == nearest.getX() &&
                                        loc.getY() < nearest.getY())))
                ) {

                    bestDist = dist;
                    nearest  = loc;

                }

            }

            route.add(nearest);
            toVisit.remove(nearest);
            current = nearest;

        }

        route.add(new Location(0,0));
        return expandManhattanPath(route);

    }
}
