package by.sands.vitebsktransport.domain;

public class Example {
    private String routeType;
    private String routeNumber;
    private String routeName;
    private String directionName;
    private String path;
    private String timingExample;
    private String day;
    private String timeTable;

    public Example() {
    }

    public Example(String routeType, String routeNumber, String routeName, String directionName, String path,
            String timingExample, String day, String timeTable) {
        super();
        this.routeType = routeType;
        this.routeNumber = routeNumber;
        this.routeName = routeName;
        this.directionName = directionName;
        this.path = path;
        this.timingExample = timingExample;
        this.day = day;
        this.timeTable = timeTable;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimingExample() {
        return timingExample;
    }

    public void setTimingExample(String timingExample) {
        this.timingExample = timingExample;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(String timeTable) {
        this.timeTable = timeTable;
    }

    @Override
    public String toString() {
        return "Example [routeType=" + routeType + ",\nrouteNumber=" + routeNumber + ",\nrouteName=" + routeName
                + ",\ndirectionName=" + directionName + ",\npath=" + path + ",\ntimingExample=" + timingExample +
                ",\nday=" + day + ",\ntimeTable=\n" + timeTable + "]";
    }

    
}
