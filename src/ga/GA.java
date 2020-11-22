package ga;

import java.util.ArrayList;
import java.util.List;

public class GA {

	List<Chromosome> population;
	List<Channel> channels;
	private float budget;
	public GA(List<Channel> channels, float budget) {
		this.channels = channels;
		this.budget = budget;
		population = new ArrayList<Chromosome>();
	}
	
	public void start() {
		
		
	}

	public void initializePopulation() {
		
		
	}

}
