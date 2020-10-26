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




public class StateCensusAnalyser 
{
	public int stateCensusReader(String csvFilePath) throws CensusException {
		try (Reader fileReader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			
			CsvToBeanBuilder<CSVStateCensus> csvFileToBeanMaker = new CsvToBeanBuilder<>(fileReader);
			csvFileToBeanMaker.withType(CSVStateCensus.class);
			csvFileToBeanMaker.withIgnoreLeadingWhiteSpace(true);
			
			CsvToBean<CSVStateCensus> csvFileToBeanFile = csvFileToBeanMaker.build();
			 Iterator<CSVStateCensus> censusCsvIterator = csvFileToBeanFile.iterator();
			Iterable<CSVStateCensus> csvIterableObject = () ->  censusCsvIterator;
			
			int entryCounter = (int) StreamSupport.stream(csvIterableObject.spliterator(), false).count();
			return entryCounter;
		   } catch (IOException e) {
			throw new CensusException("CSV File is not Correct", CensusExceptionType.FILE_ERROR);
		   } catch (RuntimeException e) {
			if (ExceptionUtils.indexOfType(e, CsvDataTypeMismatchException.class) != -1) {
				throw new CensusException("Type is inconsistent. Please check", CensusExceptionType.TYPE_ERROR);
			   } 
			else if (ExceptionUtils.indexOfType(e, CsvRequiredFieldEmptyException.class) != -1) {
				if(e.getMessage().equals("CSV header couldn't get caught.Please check")) {
					throw new CensusException("Header error", CensusExceptionType.HEADER_ERROR);
				  }
				else {
					throw new CensusException("Delimiter is not consistent",
						CensusExceptionType.DELIMITER_ERROR);
				   }
			  } 
			 else {
				e.printStackTrace();
				throw new RuntimeException();
			}
		   }
		   
	}
	public int stateCodeReader(String csvFilePath) throws CensusException {
		try (Reader fileReader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			
			CsvToBeanBuilder<CSVStates> csvFileToBeanBuilder = new CsvToBeanBuilder<>(fileReader);
			csvFileToBeanBuilder.withType(CSVStates.class);
			csvFileToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			
			CsvToBean<CSVStates> csvFileToBeanFile = csvFileToBeanBuilder.build();
			 Iterator<CSVStates> censusCsvIterator = csvFileToBeanFile.iterator();
			Iterable<CSVStates> csvIterableObject = () ->  censusCsvIterator;
			
			int entryCounter = (int) StreamSupport.stream(csvIterableObject.spliterator(), false).count();
			return entryCounter;
		   } catch (IOException a) {
			throw new CensusException("CSV File is not Correct", CensusExceptionType.FILE_ERROR);
		   } catch (RuntimeException a) {
			if (ExceptionUtils.indexOfType(a, CsvDataTypeMismatchException.class) != -1) {
				throw new CensusException("Type is inconsistent. Please check", CensusExceptionType.TYPE_ERROR);
			   } 
			else if (ExceptionUtils.indexOfType(a, CsvRequiredFieldEmptyException.class) != -1) {
				if(a.getMessage().equals("CSV header couldn't get caught.Please check")) {
					throw new CensusException("Header error", CensusExceptionType.HEADER_ERROR);
				  }
				else {
					throw new CensusException("Delimiter is not consistent",
						CensusExceptionType.DELIMITER_ERROR);
				   }
			  } 
			 else {
				a.printStackTrace();
				throw new RuntimeException();
			}
		   }
	}
		
		
		
    }

	
	

