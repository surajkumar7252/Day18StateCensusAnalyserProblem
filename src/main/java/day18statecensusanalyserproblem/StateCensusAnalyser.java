package day18statecensusanalyserproblem;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
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
	public void sortingData(List<CSVStateCensus> csvCensusList, Comparator<CSVStateCensus> censusComparator) {
		int i=0;
		while(i<csvCensusList.size()-1) {
			int j=0;
		  while( j<csvCensusList.size()-i-1) {
				CSVStateCensus tempData1=csvCensusList.get(j);
				CSVStateCensus tempData2=csvCensusList.get(j+1);
				if(censusComparator.compare(tempData1, tempData2)>0) {
					csvCensusList.set(j+1, tempData1);
					csvCensusList.set(j, tempData2);
					
				}j++;
			}i++;
		}
	}
	
	 public String sortedCensusPerState(String csvFilePath, TypeOfCsvBuilder typeOfCsvBuilder) throws CensusException, CsvException {
			try (Reader fileReader = Files.newBufferedReader(Paths.get(csvFilePath));) {
				    ICsvCreator csvCreator = (typeOfCsvBuilder==TypeOfCsvBuilder.OPEN_CSV?CsvBuilderFactory.createBuilderEntry():CsvBuilderFactory.createCommonBuilder());
					List<CSVStateCensus>csvStateCensusList = csvCreator.getCSVList(fileReader, CSVStateCensus.class);
				Function<CSVStateCensus, String> key=census->census.stateName;
				Comparator<CSVStateCensus> comparator=Comparator.comparing(key);
				sortingData(csvStateCensusList, comparator);
				String sortedJsonCensus=new Gson().toJson(csvStateCensusList);
				return sortedJsonCensus;
			} catch (IOException e) {
				throw new CsvException("File Error", typeOfCsvException.FILE_ERROR);
			}
		   
		}
	
}

	
	

