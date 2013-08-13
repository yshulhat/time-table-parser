package by.sands.vitebsktransport.data;

import com.spax.vitebsktransport.domain.Departure;
import com.spax.vitebsktransport.domain.Route;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class RawDataParser {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm");
    private State state = State.ROUTE_TYPE;

    private String routeType;
    private String routeNumber;
    private String routeName;
    private String directionName;
    private List<Integer> path = new ArrayList<>();
    private List<Integer> timing = new ArrayList<>();
    private String day;
    private List<Departure> departures = new ArrayList<>();

    public RawDataParser(String data) throws ParseException {
        parseData(data);
    }

    private boolean parseData(String data) throws ParseException {
        StringTokenizer st = new StringTokenizer(data, "\r\n");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.startsWith("#")) {
                continue;
            }
            switch (state) {
            case ROUTE_TYPE:
                routeType = token;
                state = State.ROUTE_NUMBER;
                break;
            case ROUTE_NUMBER:
                routeNumber = token;
                state = State.ROUTE_NAME;
                break;
            case ROUTE_NAME:
                routeName = token;
                state = State.DIRECTION_NAME;
                break;
            case DIRECTION_NAME:
                directionName = token;
                state = State.ROUTE_PATH;
                break;
            case ROUTE_PATH:
                parseRoutePath(token);
                state = State.TIMING;
                break;
            case TIMING:
                parseTiming(token);
                state = State.DAY;
                break;
            case DAY:
                day = token;
                state = State.TIME_TABLE;
                break;
            case TIME_TABLE:
                if (StringUtils.isEmpty(token)) {
                    state = State.END;
                    continue;
                }
                parseTimeTable(token);
                break;
            case END:
                if (StringUtils.isNotEmpty(token)) {
                    state = State.DIRECTION_NAME;
                }
                break;
            default:
            }
        }
        return false;
    }

    private void parseRoutePath(String token) {
        path = new ArrayList<>();
        String[] ids = token.split("\\s");
        for (String id : ids) {
            path.add(new Integer(id));
        }
    }

    private void parseTiming(String token) throws ParseException {
        timing = new ArrayList<>();
        String[] times = token.split("\\s");
        if (times != null && times.length >= 2) {
            for (int i=0; i<times.length-1; i++) {
                Date d1 = FORMAT.parse(times[i]);
                Date d2 = FORMAT.parse(times[i+1]);
                int mins = (int) ((d2.getTime() - d1.getTime()) / (1000*60));
                timing.add(mins);
            }
        }
    }

    private void parseTimeTable(String token) {
        if (StringUtils.isNotEmpty(token)) {
            int fromStop = getFromStop(token);
            int toStop = getToStop(token);
            String time = token.trim().split("\\s")[0];
            departures.add(new Departure(day, time, fromStop, toStop, -1));
        }
    }

    private int getFromStop(String token) {
        int idx = 0;
        if (token.charAt(0) == '\t') {
            while (token.charAt(idx) == '\t') {
                idx++;
            }
        }
        return path.get(idx);
    }

    private int getToStop(String token) {
        int i = token.length() - 1;
        int idx = path.size() - 1;
        if (token.charAt(i) == '\t') {
            while (token.charAt(i--) == '\t') {
                idx--;
            }
            if (!StringUtils.isNumeric("" + token.charAt(i))) {
                idx--;
            }
        }
        return path.get(idx);
    }
    public State getState() {
        return state;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getDirectionName() {
        return directionName;
    }

    public List<Integer> getPath() {
        return path;
    }

    public List<Integer> getTiming() {
        return timing;
    }

    public String getDay() {
        return day;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public Route getRoute() {
        return new Route(routeNumber, routeName, routeType);
    }

    private enum State {
        ROUTE_TYPE,
        ROUTE_NUMBER,
        ROUTE_NAME,
        DIRECTION_NAME,
        ROUTE_PATH,
        TIMING,
        DAY,
        TIME_TABLE,
        END
    };
}
