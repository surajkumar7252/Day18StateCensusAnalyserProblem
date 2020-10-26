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
	 
	 public void sortingDataByStateCode(List<CSVStates> csvStateList, Comparator<CSVStates> censusComparator) {
			int i=0;
			while(i<csvStateList.size()-1) {
				int j=0;
			  while( j<csvStateList.size()-i-1) {
					CSVStates tempData1=csvStateList.get(j);
					CSVStates tempData2=csvStateList.get(j+1);
					if(censusComparator.compare(tempData1, tempData2)>0) {
						csvStateList.set(j+1, tempData1);
						csvStateList.set(j, tempData2);
						
					}j++;
				}i++;
			}
		}
	 
	 public String sortedCensusPerStateByStateCode(String csvFilePath, TypeOfCsvBuilder typeOfCsvBuilder) throws CensusException, CsvException {
			try (Reader fileReader = Files.newBufferedReader(Paths.get(csvFilePath));) {
				    ICsvCreator csvCreator = (typeOfCsvBuilder==TypeOfCsvBuilder.OPEN_CSV?CsvBuilderFactory.createBuilderEntry():CsvBuilderFactory.createCommonBuilder());
					List<CSVStates>csvStateCensusList = csvCreator.getCSVList(fileReader, CSVStates.class);
				Function<CSVStates, String> key=census->census.stateCode;
				Comparator<CSVStates> comparator=Comparator.comparing(key);
				sortingDataByStateCode(csvStateCensusList, comparator);
				String sortedJsonCensus=new Gson().toJson(csvStateCensusList);
				return sortedJsonCensus;
			} catch (IOException e) {
				throw new CsvException("File Error", typeOfCsvException.FILE_ERROR);
			}
		   
		}
	 
	 public void sortingPopulationDataDescending(List<CSVStateCensus> csvCensusList, Comparator<CSVStateCensus> censusComparator) {
			int i=0;
			while(i<csvCensusList.size()-1) {
				int j=0;
			  while( j<csvCensusList.size()-i-1) {
					CSVStateCensus tempData1=csvCensusList.get(j);
					CSVStateCensus tempData2=csvCensusList.get(j+1);
					if(censusComparator.compare(tempData1, tempData2)<0) {
						
						csvCensusList.set(j, tempData2);
						csvCensusList.set(j+1, tempData1);
						
					}j++;
				}i++;
			}
		}
	 
	 public void sortingPopulationDataAscending(List<CSVStateCensus> csvCensusList, Comparator<CSVStateCensus> censusComparator) {
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
	 
	public String sortedPopulationDataDescending(String filePath) throws IOException {
			FileWriter fileWriter = new FileWriter(filePath);
			List<CSVStateCensus>csvStateCensusList = null ;
			Comparator<CSVStateCensus> comparator = Comparator.comparing(census -> census.population);
			this.sortingPopulationDataDescending(csvStateCensusList,comparator);
			String sortedStateCensus = new Gson().toJson(csvStateCensusList);
			fileWriter.write(sortedStateCensus);
			fileWriter.close();
			return sortedStateCensus;
		}
	
	
	public String sortedPopulationDataAscending(String filePath) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		List<CSVStateCensus>csvStateCensusList = null ;
		Comparator<CSVStateCensus> comparator = Comparator.comparing(census -> census.population);
		this.sortingPopulationDataAscending(csvStateCensusList,comparator);
		String sortedStateCensus = new Gson().toJson(csvStateCensusList);
		fileWriter.write(sortedStateCensus);
		fileWriter.close();
		return sortedStateCensus;
	}
}

	
	

