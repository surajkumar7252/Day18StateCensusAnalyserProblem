package day18statecensusanalyserproblem;

public class CsvBuilderFactory {
	public static ICsvCreator createBuilderEntry() {
		return new OpenCsvBuilder();
	}
	
   }