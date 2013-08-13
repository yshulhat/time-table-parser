package by.sands.vitebsktransport;

import by.sands.vitebsktransport.data.DataFileProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Test {

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
        DataFileProcessor processor = new DataFileProcessor();

//        processor.processFile("data\\29-01.txt");
//        processor.processFile("data\\29-02.txt");
        processor.processFile("data\\29-03.txt");
        processor.processFile("data\\29-04.txt");
    }

}
