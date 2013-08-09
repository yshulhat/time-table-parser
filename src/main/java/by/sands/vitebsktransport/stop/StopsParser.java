package by.sands.vitebsktransport.stop;

import static java.text.MessageFormat.format;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StopsParser {
    private static final String S = "INSERT INTO stops VALUES(NULL, ''{0}'', {1}, {2});\n";

    public static void main(String[] args) throws IOException {
        String text = "";
        try (InputStream in = new FileInputStream("d:/1.js")) {
            text = IOUtils.toString(in);
        }
        String[] coords = StringUtils.substringsBetween(text, "google.maps.LatLng(", ");");
        System.out.println(coords.length);

        String[] stops = StringUtils.substringsBetween(text, "\t\ttitle : \"", "\"");
        System.out.println(stops.length);

        List<Stop> list = new ArrayList<>();
        for (int i=0; i<coords.length; i++) {
            String[] c = coords[i].split(", ");
            list.add(new Stop(stops[i].replaceAll("\\s{1,10}", " "), toE6(c[0]), toE6(c[1])));
        }
        Collections.sort(list);
//        for (Stop s : list) {
//            System.out.println(s.getName());
//        }
        try (FileWriter out = new FileWriter("d:/stops-01.txt")) {
            for (Stop s : list) {
                out.write(format(S, s.getName(), s.getLat(), s.getLang()));
            }
        }
    }

    public static String toE6(String e1) {
        return e1.replaceAll("\\.", "").substring(0, 8);
    }
}
