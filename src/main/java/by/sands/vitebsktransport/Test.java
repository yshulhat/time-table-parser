package by.sands.vitebsktransport;

import by.sands.vitebsktransport.data.DataReader;
import by.sands.vitebsktransport.data.RawDataParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;


public class Test {

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
        String data = new DataReader("data\\29A-01.txt").read();
        RawDataParser parser = new RawDataParser(data);
//        System.out.println(parser.getRouteName());
//        System.out.println(parser.getRouteNumber());
//        System.out.println(parser.getRouteType());
//        System.out.println(parser.getDirectionName());
//        System.out.println(parser.getPath());
//        System.out.println(parser.getTiming());
//        System.out.println(parser.getDay());
//        System.out.println(parser.getDepartures());

        QueryCreator creator = new QueryCreator();
        System.out.println(creator.createRouteInsertQuery(parser.getRouteType(), parser.getRouteNumber(), parser.getRouteName()));
        System.out.println(creator.createDirectionInsertQuery(101, parser.getDirectionName()));
        System.out.println(creator.createMoveTimeQueries(parser.getPath(), parser.getTiming(), 202));
        System.out.println(creator.createDepartureInsertQueries(parser.getDepartures(), 202));
    }

}
