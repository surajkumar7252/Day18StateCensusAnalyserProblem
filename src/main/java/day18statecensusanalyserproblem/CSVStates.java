package day18statecensusanalyserproblem;

import java.util.List;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {
    @CsvBindByName(column = "Name of state", required = true)
	public String stateName;
    @CsvBindByName(column = "state code", required = true)
	public String stateCode;


	@CsvBindByName(column = "Pin Code", required = true)
	public int pin;

	
	
	public CSVStates() {
	}

	public CSVStates(List<String> values) {
		
		this.stateName = values.get(0);
		this.stateCode = values.get(1);
		this.pin = Integer.parseInt(values.get(2));
		
	}
}