package day18statecensusanalyserproblem;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.file.*;
import java.util.stream.StreamSupport;
import java.util.*;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class StateCensusAnalyserTest 
{   public static  String CENSUS_CSV_CORRECT = "src/main/resources/CensusDetails.csv";
  
	@Test
	public void whenFedIndiaCensusDetailsCsv_shouldReturnTheNumberOfDetailCounts() {
		StateCensusAnalyser stateAnalyser = new StateCensusAnalyser();
		try {
			int counter = stateAnalyser.stateCensusReader(CENSUS_CSV_CORRECT);
			Assert.assertEquals(20, counter);
		} catch (CensusException e) {
			e.printStackTrace();
		}

	}
	
	public static final String CENSUS_CSV_ERROR = "src/main/resources/CensusDetails.csv";
	@Test
	public void whenFedWrongTypeCsvFile_ShouldThrowTypeErrorCensusException() {
		try {
			StateCensusAnalyser stateAnalyser=new StateCensusAnalyser();
			ExpectedException expectedException= ExpectedException.none();
			expectedException.expect(CensusException.class);
			stateAnalyser.stateCensusReader(CENSUS_CSV_ERROR);
		}catch(CensusException e) {
			Assert.assertEquals(CensusExceptionType.TYPE_ERROR, e.foundExceptionType);
		}
	}
	
	public static final String CENSUS_CSV_TYPE_ERROR = "src/main/resources/India-Census-Data-WrongType.csv";
	@Test
	public void whenFedWrongTypeCsvFile_ShouldThrowIncorrectTypeException() {
		try {
			StateCensusAnalyser stateAnalyser=new StateCensusAnalyser();
			ExpectedException expectedException= ExpectedException.none();
			expectedException.expect(CensusException.class);
			stateAnalyser.stateCensusReader(CENSUS_CSV_TYPE_ERROR);
		}catch(CensusException e) {
			Assert.assertEquals(CensusExceptionType.TYPE_ERROR, e.foundExceptionType);
		}
	}

	public static final String DELIMITER_ERROR= "src/main/resources/India-Census-Data-WrongDelimiter.csv";
	@Test
	public void whenFedWrongTypeCsvFile_ShouldThrowDelimiterErrorException() {
		try {
			StateCensusAnalyser stateAnalyser=new StateCensusAnalyser();
			ExpectedException expectedException= ExpectedException.none();
			expectedException.expect(CensusException.class);
			stateAnalyser.stateCensusReader(DELIMITER_ERROR);
		}catch(CensusException e) {
			Assert.assertEquals(CensusExceptionType.DELIMITER_ERROR, e.foundExceptionType);
		}
	}

	public static final String  HEADER_ERROR = "src/main/resources/India-Census-Data-WrongHeader.csv";
	@Test
	public void givenIndiaCensusCsvFileIncorrectHeaderShouldThrowCensusAnalyserExceptionOfTypeIncorrectHeader() {
		try {
			StateCensusAnalyser stateAnalyser=new StateCensusAnalyser();
			ExpectedException expectedException= ExpectedException.none();
			expectedException.expect(CensusException.class);
			stateAnalyser.stateCensusReader(HEADER_ERROR);
		}catch(CensusException e) {
			Assert.assertEquals(CensusExceptionType.HEADER_ERROR, e.foundExceptionType);
		}
	}
	
	}