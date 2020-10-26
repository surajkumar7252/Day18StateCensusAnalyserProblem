package day18statecensusanalyserproblem;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {
	@CsvBindByName(column = "stateName", required = true)
    public String stateName;
    @CsvBindByName(column = "districtName", required = true)
    public String districtName;
    @CsvBindByName(column = "population", required = true)
    public long population;
    @CsvBindByName(column = "Area(sq.km)", required = true)
    public long area;
    @CsvBindByName(column = "populationDensity", required = true)
    public long populationDensity;

  

    public CSVStateCensus(List<String> values) {
	
	this.stateName = values.get(0);
	this.districtName= values.get(1);
	this.population = Long.parseLong(values.get(2));
	this.area = Integer.parseInt(values.get(3));
	this.populationDensity = Integer.parseInt(values.get(4));
}

}