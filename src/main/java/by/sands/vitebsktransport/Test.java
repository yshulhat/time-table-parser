package by.sands.vitebsktransport;

import by.sands.vitebsktransport.data.DataReader;
import by.sands.vitebsktransport.data.RawDataParser;
import by.sands.vitebsktransport.sql.QueryCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Test {

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
        String data = new DataReader("data\\29A-01.txt").read();
        RawDataParser parser = new RawDataParser(data);

        QueryCreator creator = new QueryCreator();
        System.out.println(creator.createRouteInsertQuery(parser.getRouteType(), parser.getRouteNumber(),
                parser.getRouteName()));
        System.out.println(creator.createDirectionInsertQuery(101, parser.getDirectionName()));
        System.out.println(creator.createMoveTimeQueries(parser.getPath(), parser.getTiming(), 202));
        System.out.println(creator.createDepartureInsertQueries(parser.getDepartures(), 202));
        System.out.println(creator.createFindRouteQuery(parser.getRouteType(), parser.getRouteNumber(),
                parser.getRouteName()));
        System.out.println(creator.createFindDirectionQuery(101, parser.getDirectionName()));
    }

}
