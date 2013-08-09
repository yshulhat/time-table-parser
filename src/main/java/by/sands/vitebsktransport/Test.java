package by.sands.vitebsktransport;

import by.sands.vitebsktransport.data.DataReader;
import by.sands.vitebsktransport.data.RawDataParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;


public class Test {

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
//        QueryCreator creator = new QueryCreator();
//        System.out.println(creator.createMoveTimeQueries(Arrays.asList(1,2,3,4,5,6), Arrays.asList(1,2,3,2,1), 10));

        String data = new DataReader("data\\29A-01.txt").read();
        RawDataParser parser = new RawDataParser(data);
        System.out.println(parser.getDepartures());
    }

}
