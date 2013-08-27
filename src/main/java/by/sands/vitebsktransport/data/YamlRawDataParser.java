package by.sands.vitebsktransport.data;

import by.sands.vitebsktransport.domain.Example;
import by.sands.vitebsktransport.domain.ParsedData;

import com.spax.vitebsktransport.domain.Departure;

import org.apache.commons.lang.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class YamlRawDataParser implements RawDataParser {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm");

    public ParsedData parseData(String data) throws Exception {
        Yaml yaml = new Yaml();
        Example e = yaml.loadAs(data, Example.class);
        return fromExample(e);
    }

    private ParsedData fromExample(Example e) throws ParseException {
        ParsedData data = new ParsedData();
        data.setRouteType(e.getRouteType());
        data.setRouteNumber(e.getRouteNumber());
        data.setRouteName(e.getRouteName());
        data.setDirectionName(e.getDirectionName());
        data.setPath(parseRoutePath(e.getPath()));
        data.setTiming(parseTiming(e.getTimingExample()));
        data.setDay(e.getDay());
        data.setDepartures(parseTimeTable(e.getDay(), data.getPath(), e.getTimeTable()));
        return data;
    }

    private List<Integer> parseRoutePath(String path) {
        List<Integer> result = new ArrayList<>();
        String[] ids = path.split("\\s");
        for (String id : ids) {
            result.add(new Integer(id));
        }
        return result;
    }

    private List<Integer> parseTiming(String token) throws ParseException {
        List<Integer> result = new ArrayList<>();
        String[] times = token.split("\\s");
        if (times != null && times.length >= 2) {
            for (int i=0; i<times.length-1; i++) {
                Date d1 = FORMAT.parse(times[i]);
                Date d2 = FORMAT.parse(times[i+1]);
                int mins = (int) ((d2.getTime() - d1.getTime()) / (1000*60));
                result.add(mins);
            }
        }
        return result;
    }

    private List<Departure> parseTimeTable(String day, List<Integer> path, String timeTable) {
        List<Departure> departures = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(timeTable, "\r\n");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (StringUtils.isNotEmpty(token)) {
                int fromStop = getFromStop(path, token);
                int toStop = getToStop(path, token);
                String time = token.trim().split("\\s")[0];
                departures.add(new Departure(day, time, fromStop, toStop, -1));
            }
        }
        return departures;
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
}
