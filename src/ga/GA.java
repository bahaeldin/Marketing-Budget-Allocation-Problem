package ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA {

	private List<Chromosome> population;
	private List<Chromosome> matingPool;
	private List<Chromosome> nextGeneration;
	private List<Chromosome> previousGeneration;
	private List<Channel> channels;
	private float budget;
	private float crossoverProbability ;
	private float mutationProbability ;
	private int chromosomeSize;
	private int trailNumer;
	private Random rand;
		
	public GA(List<Channel> channels, float budget) {
		rand = new Random();
		this.channels = channels;
		this.budget = budget;
		this.trailNumer = rand.nextInt(150) + 100 ;
		
		nextGeneration = new ArrayList<Chromosome>();
		previousGeneration = new ArrayList<Chromosome>();
		matingPool = new ArrayList<Chromosome>();
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
			this.evaluteFitness();	
			/* 
			 * Selection
			 * Tournament selection
			 */
			this.matingPool = this.tournamentSelection();
			/* 
			 * Create Next generation 
			 * Crossover
			 */
			this.createNextGeneraton(crossoverProbability);
			/*
			 * Mutation
			 */
			this.mutation();
			/*
			 *  mix generations 
			 */
			this.mixgenerations();
			/*
			 * elitist replacement
			 */
			this.population = this.replacement();
			for(Chromosome chromosome : this.population) {
				chromosome.display();
			}
			System.out.println("---------- after mutation -----------");
			/*
			 * 
			 * 
			 */
			nextGeneration = new ArrayList<Chromosome>();
			previousGeneration = new ArrayList<Chromosome>();
			
			/*
			 * 
			 */
			population = evaluteFitness(population);
			
			trailNumer--;
			break;
		}
			
	}

	private List<Chromosome> replacement() {				
		return Chromosome.replaceGeneration(this.population,this.nextGeneration);
	}

	private void mixgenerations() {
		this.nextGeneration.addAll(this.previousGeneration);
		evaluteFitness(this.nextGeneration);
	}

	private void mutation() {
		for(Chromosome chromosome : this.nextGeneration) {
			chromosome.doMutation(this.mutationProbability,this.channels);
		}
	}

	private void createNextGeneraton(float crossoverProbability) {
	
		while (!this.matingPool.isEmpty()) {
			
			Chromosome parentX = matingPool.remove(0);
			Chromosome parentY = matingPool.remove(0);
			float probabilityToDoCrossover = rand.nextFloat();
			
			if(probabilityToDoCrossover <= this.crossoverProbability) {
				this.nextGeneration.addAll(crossover(parentX, parentY));
			}else {
				this.previousGeneration.add(parentX);
				this.previousGeneration.add(parentY);
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
        int temp = 0;
        
        if(point1 > point2) {
        	temp = point2;
        	point2 = point1;
        	point1 = temp;
        }
        
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

	private List<Chromosome> tournamentSelection() {
		
		List<Chromosome> matingPool = new ArrayList<Chromosome>();
		List<Chromosome> temp = this.population;
		if(temp.size() % 2 != 0) {
			temp.remove(0);
		}
		while (!temp.isEmpty()) {
			matingPool.add(tournamentSelection(temp.remove(0),temp.remove(0)));
		}	
		
		if(matingPool.size() % 2 != 0) {
			matingPool.remove(matingPool.size() - 1);
		}
		
		return matingPool;
	}
	

	private Chromosome tournamentSelection(Chromosome chromosomeX, Chromosome chromosomeY) {
		Chromosome chromosome = chromosomeX.getFitness() > chromosomeY.getFitness() ? chromosomeX : chromosomeY;
		return chromosome;
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
