package by.sands.vitebsktransport;

import static java.text.MessageFormat.format;

import com.spax.vitebsktransport.domain.Departure;

import java.util.List;

public class QueryCreator {
    public QueryCreator() {
    }

    public String createRouteInsertQuery(String type, String number, String name) {
        return format("INSERT INTO routes VALUES (''{0}'', ''{1}'', NULL, ''{2}'');", type, number, name);
    }

    public String createDirectionInsertQuery(Integer routeId, String name) {
        return format("INSERT INTO directions VALUES (NULL, ''{0}'', ''{1}'');", routeId, name);
    }

    public String createMoveTimeQueries(List<Integer> path, List<Integer> times, Integer directionId) {
        StringBuffer sb = new StringBuffer();
        if (path.size() == times.size() + 1) {
            for (int i=0; i<times.size(); i++) {
                String q = format("INSERT INTO move_times VALUES(NULL, {0}, {1}, {2}, {3}, {4});",
                        times.get(i), path.get(i), path.get(i + 1), directionId, i + 1);
                sb.append(q).append("\n");
            }
        } else {
            System.out.println("Wrong list sizes");
        }
        return sb.toString();
    }

    public String createDepartureInsertQueries(List<Departure> starts) {
        StringBuffer sb = new StringBuffer();
        for (Departure time : starts) {
            String q = format("INSERT INTO departures VALUES (''{0}'', NULL, ''{1}'', {2}, {3}, {4});",
                    time.getDay(), time.getTime(), time.getFromStopId(), time.getToStopId(), time.getDirectionId());
            sb.append(q).append("\n");
        }
        return sb.toString();
    }

}
