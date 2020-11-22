package ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA {

	List<Chromosome> population;
	List<Chromosome> matingPool;
	List<Chromosome> nextGeneration;
	List<Chromosome> previousGeneration;
	List<Channel> channels;
	private float budget;
	private float crossoverProbability ;
	private float mutationProbability ;
	private int chromosomeSize;
	private int trailNumer;
	private Random rand;
		
	public GA(List<Channel> channels, float budget) {
		this.channels = channels;
		this.budget = budget;
		rand = new Random();
		
		this.trailNumer = rand.nextInt(400) + 100 ;
		
		nextGeneration = new ArrayList<Chromosome>();
		previousGeneration = new ArrayList<Chromosome>();
		chromosomeSize = channels.size();
		
		crossoverProbability = rand.nextFloat();
		mutationProbability = rand.nextFloat();
		
		
	}
	
	public void start() {
		// Initialization
		initializePopulation();
		
		// Looping over generations
		while (trailNumer != 0) {
			
			/*
			 *  Fitness evaluation
			 */
			evaluteFitness();			
			/* 
			 * Selection
			 * Tournament selection
			 */
			tournamentSelection();		
			/* 
			 * Create Next generation 
			 * Crossover
			 */
			createNextGeneraton(crossoverProbability);
			/*
			 * Mutation
			 */
			mutation(mutationProbability);
			
			// mix generations 
			mixgenerations();
			
			/*
			 * elitist replacement
			 */
			population = replacement();
			/*
			 * 
			 * 
			 */
			nextGeneration = new ArrayList<Chromosome>();
			previousGeneration = new ArrayList<Chromosome>();
			
			/*
			 * 
			 */
			nextGeneration = evaluteFitness(population);
			
			trailNumer--;
		}
			
	}

	private List<Chromosome> replacement() {				
		return Chromosome.replaceGeneration(population,nextGeneration);
	}

	private void mixgenerations() {
		nextGeneration.addAll(previousGeneration);
		evaluteFitness(nextGeneration);
	}

	private void mutation(float mutationProbability) {
		for(Chromosome chromosome : nextGeneration) {
			chromosome.doMutation(mutationProbability);
		}
	}

	private void createNextGeneraton(float crossoverProbability) {
		
		while (this.matingPool.isEmpty()) {
			
			Chromosome parentX = matingPool.remove(0);
			Chromosome parentY = matingPool.remove(0);
			float probabilityToDoCrossover = rand.nextFloat();
			
			if(probabilityToDoCrossover <= this.crossoverProbability) {
				nextGeneration.addAll(crossover(parentX, parentY));
			}else {
				previousGeneration.add(parentX);
				previousGeneration.add(parentY);
			}
		}
	}

	private List<Chromosome> crossover(Chromosome chromosomeX, Chromosome chromosomeY) {
		
		List<Chromosome> children = new ArrayList<Chromosome>();
		
		int chromosomeSize = chromosomeX.getSize();
		Chromosome child1 = new Chromosome(chromosomeSize);
		Chromosome child2 = new Chromosome(chromosomeSize);
		
		
		int point1 = rand.nextInt(chromosomeSize - 2) + 1;
        int point2 = rand.nextInt(chromosomeSize - 2) + 1;
        
        child1.clone(chromosomeX);
        child2.clone(chromosomeY);
      
        for (int i = point1; i < point2; i++) {
        	child1.getGenes()[i] = chromosomeY.getGenes()[i];
        	child2.getGenes()[i] = chromosomeX.getGenes()[i];
		}
        
        children.add(child1);
        children.add(child2);
        
		return children;
	}

	private void tournamentSelection() {
		
		matingPool = new ArrayList<Chromosome>();
		int count = 0;
		
		while (count < population.size()) {
			
			int chromosomeX = rand.nextInt(population.size());
			int chromosomeY = rand.nextInt(population.size());
			if(chromosomeX == chromosomeY) {
				continue;
			}
			
			matingPool.add(tournamentSelection(population.get(chromosomeX),population.get(chromosomeY)));
		}	
	}

	private Chromosome tournamentSelection(Chromosome chromosomeX, Chromosome chromosomeY) {
		return chromosomeX.getFitness() > chromosomeY.getFitness() ? chromosomeX : chromosomeY;
	}

	private void evaluteFitness() {
		for(Chromosome chromosome : this.population) {
			chromosome.setFitness(this.channels);
		}
	}
	
	private List<Chromosome> evaluteFitness(List<Chromosome> population) {
		for(Chromosome chromosome : population) {
			chromosome.setFitness(this.channels);
		}
		return population;
	}

	public void initializePopulation() {
		
		population = new ArrayList<Chromosome>();
		int populationSize = rand.nextInt(25) + 5;
		for (int i = 0; i < populationSize; i++) {
			population.add(new Chromosome(this.chromosomeSize));
			population.get(i).setGenes(this.channels, this.budget);
		}
		// 
		boolean flag = true;
		for(Chromosome chromosome : population) {
			flag = chromosome.checkConstraints(this.budget);
			if(flag) 
				continue;
			else
				chromosome.fixchromosome(this.budget);
		}
	}

}
