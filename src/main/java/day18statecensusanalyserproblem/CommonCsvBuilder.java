package day18statecensusanalyserproblem;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CommonCsvBuilder implements ICsvCreator {
	
	public <T> Iterator<T> csvIterator(Reader reader, Class<T> bindClass) throws CsvException {
		try {
			List<T> records = this.getCSVList(reader, bindClass);
			Iterator<T> csvIterator = records.iterator();
			return csvIterator;
		} catch (Exception e) {
			throw new CsvException("PARSING ERROR", typeOfCsvException.PARSING_ERROR);
		}
	}
	
	public <T> List<T> getCSVList(Reader reader, Class<T> csvBindedClass) throws CsvException {
		
		CSVFormat csvFormatTemplate = CSVFormat.DEFAULT;
		try (CSVParser csvParserObject=new CSVParser(reader, csvFormatTemplate);){
			List<CSVRecord> records = csvParserObject.getRecords();
			List<T> recordObjects = new ArrayList<T>();
			records.remove(0);
			for (CSVRecord tempRecord : records) {
				List<String> stringsRecorder = new ArrayList<String>();
				for (String recordString : tempRecord) {
					stringsRecorder.add(recordString);
				}
				Constructor<T> constructor = csvBindedClass.getConstructor(new Class[] { List.class });
				T csvObject = constructor.newInstance(stringsRecorder);
				recordObjects.add(csvObject);
			}
			return recordObjects;
		} catch (Exception a) {
			throw new CsvException("PARSING ERROR", typeOfCsvException.PARSING_ERROR);
		}
	}

}