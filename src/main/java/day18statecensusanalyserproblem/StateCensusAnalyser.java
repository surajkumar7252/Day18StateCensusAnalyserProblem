package day18statecensusanalyserproblem;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.StreamSupport;

import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

enum CensusExceptionType{
	FILE_ERROR, TYPE_ERROR, DELIMITER_ERROR, HEADER_ERROR,PARSING_ERROR
}

final class CensusException extends Exception{	
	CensusExceptionType foundExceptionType;
	public CensusException(String message, CensusExceptionType exceptionTypeFound) {
		
		foundExceptionType=exceptionTypeFound;
	}
}

 enum typeOfCsvException {
	PARSING_ERROR, FILE_ERROR, TYPE_ERROR, DELIMITER_ERROR, HEADER_ERROR
}


final class CsvException extends Exception {	
	   typeOfCsvException typeOfException;
	public CsvException(String message, typeOfCsvException exceptionType) {
		
		this.typeOfException=exceptionType;
	}
}

enum TypeOfCsvBuilder {
	OPEN_CSV, COMMON_CSV
}

public class StateCensusAnalyser 
{
	public int stateCensusReader(String csvFilePath,TypeOfCsvBuilder typeOfCsvBuilder) throws CensusException, CsvException {
		try (Reader fileReader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			
			ICsvCreator csvCreator = (typeOfCsvBuilder==TypeOfCsvBuilder.OPEN_CSV?CsvBuilderFactory.createBuilderEntry():CsvBuilderFactory.createCommonBuilder());
			
			
			List<CSVStateCensus> censusCsvList = csvCreator.getCSVList(fileReader, CSVStateCensus.class);
			return censusCsvList.size();
			} catch (IOException e) {
				throw new CsvException("File Error", typeOfCsvException.FILE_ERROR);
			}
		   
	}
	public int stateCodeReader(String csvFilePath,TypeOfCsvBuilder typeOfCsvBuilder) throws CensusException, CsvException {
		try (Reader fileReader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			
			ICsvCreator csvCreator = (typeOfCsvBuilder==TypeOfCsvBuilder.OPEN_CSV?CsvBuilderFactory.createBuilderEntry():CsvBuilderFactory.createCommonBuilder());
			
			
			List<CSVStates> codesCsvList = csvCreator.getCSVList(fileReader, CSVStates.class);
			return codesCsvList.size();
			} catch (IOException e) {
				throw new CsvException("File Error", typeOfCsvException.FILE_ERROR);
			}
		   
	}
}

	
	

