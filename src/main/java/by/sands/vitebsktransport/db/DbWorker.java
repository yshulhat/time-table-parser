package by.sands.vitebsktransport.db;

import static by.sands.vitebsktransport.db.SqlHelper.WRONG_ID;

import com.spax.vitebsktransport.domain.Departure;
import com.spax.vitebsktransport.domain.Direction;
import com.spax.vitebsktransport.domain.Route;

import java.util.List;

public class DbWorker implements AutoCloseable {
    private SqlHelper helper;
    private QueryCreator creator = new QueryCreator();

    public DbWorker() {
        helper = new SqlHelper();
    }

    public DbWorker(String dbfile) {
        helper = new SqlHelper(dbfile);
    }

    public long insertRoute(Route route) {
        if (!isRouteExists(route)) {
            String sql = creator.createRouteInsertQuery(route.getType(), route.getNumber(), route.getName());
            if (!helper.insert(sql)) {
                return WRONG_ID;
            }
        }
        return getRouteId(route);
    }

    public long insertDirection(Direction dir) {
        if (!isDirectionExists(dir)) {
            String sql = creator.createDirectionInsertQuery((int) dir.getRouteId(), dir.getName());
            if (!helper.insert(sql)) {
                return WRONG_ID;
            }
        }
        return getDirectionId(dir);
    }

    public long getRouteId(Route route) {
        return helper.selectId(creator.createFindRouteQuery(route.getType(), route.getNumber()));
    }

    public long getDirectionId(Direction dir) {
        return helper.selectId(creator.createFindDirectionQuery((int) dir.getRouteId(), dir.getName()));
    }

    public boolean isRouteExists(Route route) {
        return getRouteId(route) != WRONG_ID;
    }

    public boolean isDirectionExists(Direction dir) {
        return getDirectionId(dir) != WRONG_ID;
    }

    public boolean insertPath(List<Integer> path, List<Integer> timing, long directionId) {
        String sql = creator.createMoveTimeQueries(path, timing, (int) directionId);
        return helper.insert(sql);
    }

    public boolean insertDepartures(List<Departure> starts, long directionId) {
        String sql = creator.createDepartureInsertQueries(starts, (int) directionId);
        return helper.insert(sql);
    }

    public boolean isPathPresent(long directionId, List<Integer> path) {
        return helper.selectCount(creator.createCheckPathQuery(directionId)) == path.size() - 1;
    }

    @Override
    public void close() {
        if (helper != null) {
            helper.close();
        }
    }

}
