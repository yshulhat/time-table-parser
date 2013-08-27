package by.sands.vitebsktransport;

import by.sands.vitebsktransport.data.DataFileProcessor;

public class App {

    public static void main(String[] args) throws Exception {
      DataFileProcessor processor = new DataFileProcessor();

      processor.processFile("data\\29-01.txt");
      processor.processFile("data\\29-02.txt");
      processor.processFile("data\\29-03.txt");
      processor.processFile("data\\29-04.txt");
    }

}
