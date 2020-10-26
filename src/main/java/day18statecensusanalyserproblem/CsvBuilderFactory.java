package day18statecensusanalyserproblem;

public class CsvBuilderFactory {
	public static ICsvCreator createBuilderEntry() {
		return new OpenCsvBuilder();
	}

	public static ICsvCreator createCommonBuilder() {
		return new CommonCsvBuilder();
	}
   }