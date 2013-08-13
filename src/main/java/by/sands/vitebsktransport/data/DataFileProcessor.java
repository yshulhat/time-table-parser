package by.sands.vitebsktransport.data;

import by.sands.vitebsktransport.db.DbWorker;
import by.sands.vitebsktransport.db.SqlHelper;

import com.spax.vitebsktransport.domain.Direction;
import com.spax.vitebsktransport.domain.Route;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class DataFileProcessor {

    public boolean processFile(String file) throws FileNotFoundException, IOException, ParseException {
        String data = new DataReader(file).read();
        RawDataParser parser = new RawDataParser(data);

        try (DbWorker db = new DbWorker()) {
            long routeId = insertRoute(db, parser.getRoute());
            long dirId = insertDirection(db, new Direction(parser.getDirectionName(), routeId));
            insertPath(db, parser, dirId);
            insertDepartures(db, parser, dirId);
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

    private void insertPath(DbWorker db, RawDataParser parser, long dirId) throws Exception {
        if (!db.isPathPresent(dirId, parser.getPath())) {
            boolean success = db.insertPath(parser.getPath(), parser.getTiming(), dirId);
            if (!success) {
                throw new Exception("Failed to insert move times");
            }
        }
    }

    private void insertDepartures(DbWorker db, RawDataParser parser, long dirId) throws Exception {
        boolean success = db.insertDepartures(parser.getDepartures(), dirId);
        if (!success) {
            throw new Exception("Failed to insert departures");
        }
    }
}
