package by.sands.vitebsktransport.data;

import by.sands.vitebsktransport.db.DbWorker;
import by.sands.vitebsktransport.db.SqlHelper;
import by.sands.vitebsktransport.domain.ParsedData;

import com.spax.vitebsktransport.domain.Direction;
import com.spax.vitebsktransport.domain.Route;

public class DataFileProcessor {

    public boolean processFile(String file) throws Exception {
        String data = new DataReader(file).read();
        RawDataParser parser = new TextRawDataParser();
        ParsedData parsed = parser.parseData(data);

        try (DbWorker db = new DbWorker()) {
            long routeId = insertRoute(db, parsed.getRoute());
            long dirId = insertDirection(db, new Direction(parsed.getDirectionName(), routeId));
            insertPath(db, parsed, dirId);
            insertDepartures(db, parsed, dirId);
        } catch (Exception e) {
            System.out.println("Failed to process file [" + file + "]:");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private long insertRoute(DbWorker db, Route route) throws Exception {
        long routeId = db.insertRoute(route);
        if (routeId == SqlHelper.WRONG_ID) {
            throw new Exception("Failed to insert route");
        }
        return routeId;
    }

    private long insertDirection(DbWorker db, Direction dir) throws Exception {
        long dirId = db.insertDirection(dir);
        if (dirId == SqlHelper.WRONG_ID) {
            throw new Exception("Failed to insert direction");
        }
        return dirId;
    }

    private void insertPath(DbWorker db, ParsedData parsed, long dirId) throws Exception {
        if (!db.isPathPresent(dirId, parsed.getPath())) {
            boolean success = db.insertPath(parsed.getPath(), parsed.getTiming(), dirId);
            if (!success) {
                throw new Exception("Failed to insert move times");
            }
        }
    }

    private void insertDepartures(DbWorker db, ParsedData parsed, long dirId) throws Exception {
        boolean success = db.insertDepartures(parsed.getDepartures(), dirId);
        if (!success) {
            throw new Exception("Failed to insert departures");
        }
    }
}
