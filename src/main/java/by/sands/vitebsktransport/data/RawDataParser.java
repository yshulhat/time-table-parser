package by.sands.vitebsktransport.data;

import by.sands.vitebsktransport.domain.ParsedData;

public interface RawDataParser {
    ParsedData parseData(String data) throws Exception;
}
