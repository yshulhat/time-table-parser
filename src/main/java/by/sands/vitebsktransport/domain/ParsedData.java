package by.sands.vitebsktransport.domain;

import com.spax.vitebsktransport.domain.Departure;
import com.spax.vitebsktransport.domain.Route;

import java.util.ArrayList;
import java.util.List;

public class ParsedData {
    private String routeType;
    private String routeNumber;
    private String routeName;
    private String directionName;
    private List<Integer> path = new ArrayList<>();
    private List<Integer> timing = new ArrayList<>();
    private String day;
    private List<Departure> departures = new ArrayList<>();

    public ParsedData() {
    }

    public ParsedData(String routeType, String routeNumber, String routeName, String directionName, List<Integer> path,
            List<Integer> timing, String day, List<Departure> departures) {
        this.routeType = routeType;
        this.routeNumber = routeNumber;
        this.routeName = routeName;
        this.directionName = directionName;
        this.path = path;
        this.timing = timing;
        this.day = day;
        this.departures = departures;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public List<Integer> getPath() {
        return path;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public List<Integer> getTiming() {
        return timing;
    }

    public void setTiming(List<Integer> timing) {
        this.timing = timing;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    public Route getRoute() {
        return new Route(routeNumber, routeName, routeType);
    }

    @Override
    public String toString() {
        return "ParsedData [\n\trouteType=" + routeType + ",\n\trouteNumber=" + routeNumber + ",\n\trouteName="
                + routeName + ",\n\tdirectionName=" + directionName + ",\n\tpath=" + path + ",\n\ttiming=" + timing
                + ",\n\tday=" + day + ",\n\tdepartures=" + departures + "\n]";
    }

}
