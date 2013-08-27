package by.sands.vitebsktransport;

import by.sands.vitebsktransport.data.DataReader;
import by.sands.vitebsktransport.data.RawDataParser;
import by.sands.vitebsktransport.data.TextRawDataParser;
import by.sands.vitebsktransport.data.YamlRawDataParser;
import by.sands.vitebsktransport.domain.ParsedData;

public class Test {
    private static final String[] TXT = new String[] {
        "data\\29-01.txt", "data\\29-02.txt", "data\\29-03.txt", "data\\29-04.txt", "data\\29A-01.txt"
    };
    private static final String[] YAML = new String[] {
        "data\\29-01.yaml", "data\\29-02.yaml", "data\\29-03.yaml", "data\\29-04.yaml", "data\\29A-01.yaml"
    };

    public static void main(String[] args) throws Exception {
        String data = new DataReader(YAML[4]).read();
        RawDataParser parser = new YamlRawDataParser();
        System.out.println("From YAML: " + parser.parseData(data));

        data = new DataReader(TXT[4]).read();
        parser = new TextRawDataParser();
        System.out.println("From TEXT: " + parser.parseData(data));
    }

    public static void printPaths() throws Exception {
        for (String file : TXT) {
            String data = new DataReader(file).read();
            RawDataParser parser = new TextRawDataParser();
            ParsedData parsed = parser.parseData(data);
            System.out.println(parsed.getDirectionName() + " (" + parsed.getDay() + "): ");
            System.out.print("\ttiming: ");
            for (Integer i : parsed.getTiming()) {
                System.out.print("" + i + " ");
            }
            System.out.println();
            System.out.print("\tpath: ");
            for (Integer i : parsed.getPath()) {
                System.out.print("" + i + " ");
            }
            System.out.println();
        }
    }

}
