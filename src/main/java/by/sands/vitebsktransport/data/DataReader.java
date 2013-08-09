package by.sands.vitebsktransport.data;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataReader {
    private String path;

    public DataReader(String path) {
        this.path = path;
    }

    public String read() throws FileNotFoundException, IOException {
        return IOUtils.toString(new FileInputStream(path));
    }
}
