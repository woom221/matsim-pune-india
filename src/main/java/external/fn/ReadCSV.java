package external.fn;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {
    public List<String[]> read(File file) throws Exception {
        List<String[]> records = new ArrayList<String[]>();
        CSVReader csvReader = null;
        try {
            String[] rows;
            csvReader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(new CSVParserBuilder().withSeparator(',').build()).build();
            while ((rows = csvReader.readNext()) != null) {
                records.add(rows);
            }
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
        }
        return records;
    }
}
