package day18statecensusanalyserproblem;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

interface ICsvCreator {
	public <T> Iterator<T> csvIterator(Reader reader, Class<T>bindClass) throws CensusException;
	public <T> List<T>  getCSVList(Reader reader, Class<T> bindClass) throws CensusException;
}

    class CsvBuilderFactory {
	public static ICsvCreator createBuilderEntry() {
		return new OpenCsvBuilder();
	}
	
   }

public class OpenCsvBuilder implements ICsvCreator {
	
	private <T> CsvToBean<T> csvToBean(Reader reader, Class<T> csvBindedClass) throws RuntimeException {
		CsvToBeanBuilder<T> beanBuilder = new CsvToBeanBuilder<>(reader);
		beanBuilder.withType(csvBindedClass);
		beanBuilder.withIgnoreLeadingWhiteSpace(true);
		CsvToBean<T> csvToBean = beanBuilder.build();
		return csvToBean;
	}

	public <T> Iterator<T> csvIterator(Reader reader, Class<T> bindClass) throws CensusException {
		try {
			CsvToBean<T> csvToBeanMaker = this.csvToBean(reader, bindClass);
			Iterator<T> censusCsvIterator = csvToBeanMaker.iterator();
			return censusCsvIterator;
		} catch (RuntimeException e) {
			if (ExceptionUtils.indexOfType(e, CsvDataTypeMismatchException.class) != -1) {
				throw new CensusException("Incorrect CSV File", CensusExceptionType.FILE_ERROR);
			} else if (ExceptionUtils.indexOfType(e, CsvRequiredFieldEmptyException.class) != -1) {
				if (e.getMessage().equals("CSV header not acquired!")) {
					throw new CensusException("Header error", CensusExceptionType.HEADER_ERROR);
				} else {
					throw new CensusException("Delimiter is not consistent",
							CensusExceptionType.DELIMITER_ERROR);
				}
			} else {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	
	public <T> List<T> getCSVList(Reader reader, Class<T> bindClass) throws CensusException {
		try {
			CsvToBean<T> csvToBean = this.csvToBean(reader, bindClass);
			List<T> csvList = csvToBean.parse();
			return  csvList;
		} catch (RuntimeException e) {
			if (ExceptionUtils.indexOfType(e, CsvDataTypeMismatchException.class) != -1) {
				throw new CensusException("TYPE ERROR", CensusExceptionType.TYPE_ERROR);
			
			} else if (ExceptionUtils.indexOfType(e, CsvRequiredFieldEmptyException.class) != -1) {
				if (e.getMessage().equals("CSV header not acquired!")) {
					throw new CensusException("Header error", CensusExceptionType.HEADER_ERROR);
				} else {
					throw new CensusException("Delimiter is not consistent",
							CensusExceptionType.DELIMITER_ERROR);
				}
			} else {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	
}

