package by.sands.vitebsktransport.data;

import by.sands.vitebsktransport.domain.ParsedData;

import com.spax.vitebsktransport.domain.Departure;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class TextRawDataParser implements RawDataParser {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm");
    private State state = State.ROUTE_TYPE;

    public ParsedData parseData(String data) throws Exception {
        ParsedData result = new ParsedData();
        StringTokenizer st = new StringTokenizer(data, "\r\n");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.startsWith("#")) {
                continue;
            }
            switch (state) {
            case ROUTE_TYPE:
                result.setRouteType(token);
                state = State.ROUTE_NUMBER;
                break;
            case ROUTE_NUMBER:
                result.setRouteNumber(token);
                state = State.ROUTE_NAME;
                break;
            case ROUTE_NAME:
                result.setRouteName(token);
                state = State.DIRECTION_NAME;
                break;
            case DIRECTION_NAME:
                result.setDirectionName(token);
                state = State.ROUTE_PATH;
                break;
            case ROUTE_PATH:
                parseRoutePath(result, token);
                state = State.TIMING;
                break;
            case TIMING:
                parseTiming(result, token);
                state = State.DAY;
                break;
            case DAY:
                result.setDay(token);
                state = State.TIME_TABLE;
                break;
            case TIME_TABLE:
                if (StringUtils.isEmpty(token)) {
                    state = State.END;
                    continue;
                }
                parseTimeTable(result, token);
                break;
            case END:
                if (StringUtils.isNotEmpty(token)) {
                    state = State.DIRECTION_NAME;
                }
                break;
            default:
            }
        }
        return result;
    }

    private void parseRoutePath(ParsedData result, String token) {
        result.setPath(new ArrayList<Integer>());
        String[] ids = token.split("\\s");
        for (String id : ids) {
            result.getPath().add(new Integer(id));
        }
    }

    private void parseTiming(ParsedData result, String token) throws ParseException {
        result.setTiming(new ArrayList<Integer>());
        String[] times = token.split("\\s");
        if (times != null && times.length >= 2) {
            for (int i=0; i<times.length-1; i++) {
                Date d1 = FORMAT.parse(times[i]);
                Date d2 = FORMAT.parse(times[i+1]);
                int mins = (int) ((d2.getTime() - d1.getTime()) / (1000*60));
                result.getTiming().add(mins);
            }
        }
    }

    private void parseTimeTable(ParsedData result, String token) {
        if (StringUtils.isNotEmpty(token)) {
            int fromStop = getFromStop(result.getPath(), token);
            int toStop = getToStop(result.getPath(), token);
            String time = token.trim().split("\\s")[0];
            result.getDepartures().add(new Departure(result.getDay(), time, fromStop, toStop, -1));
        }
    }

    private int getFromStop(List<Integer> path, String token) {
        int idx = 0;
        if (token.charAt(0) == '\t') {
            while (token.charAt(idx) == '\t') {
                idx++;
            }
        }
        return path.get(idx);
    }

    private int getToStop(List<Integer> path, String token) {
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
