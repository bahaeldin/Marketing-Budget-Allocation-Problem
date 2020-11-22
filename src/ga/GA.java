package ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA {

	List<Chromosome> population;
	List<Channel> channels;
	private float budget;
	private Random rand;
	private int chromosomeSize;
	
	public GA(List<Channel> channels, float budget) {
		this.channels = channels;
		this.budget = budget;
		population = new ArrayList<Chromosome>();
		chromosomeSize = channels.size();
		rand = new Random();
		initializePopulation();
	}
	
	public void start() {
		boolean flag = true;
		for(Chromosome chromosome : population) {
			flag = chromosome.checkConstraints(this.budget);
			if(flag) 
				continue;
			else
				chromosome.fixchromosome(this.budget);
		}
		
		// Fitness evaluation
		evaluteFitness();
		// Tournament selection
		
		
	}

	private void evaluteFitness() {
		for(Chromosome chromosome : population) {
			chromosome.setFitness(this.channels);
		}
	}

	public void initializePopulation() {
		int populationSize = rand.nextInt(10) + 5;
		for (int i = 0; i < populationSize; i++) {
			population.add(new Chromosome(this.chromosomeSize));
			population.get(i).setGenes(this.channels, this.budget);
		}
	}

}
