package day18statecensusanalyserproblem;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICsvCreator {
	public <T> Iterator<T> csvIterator(Reader reader, Class<T>bindClass) throws CensusException, CsvException;
	public <T> List<T>  getCSVList(Reader reader, Class<T> bindClass) throws CensusException, CsvException;

}

