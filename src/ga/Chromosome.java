package ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome {

	private float[] genes;
	private int size;
	private float fitness;
	private static Random floatRand = new Random();
	private static Random intRand = new Random();
	
	public Chromosome(int size) {
		this.size = size;
		this.fitness = 0;
		genes = new float[size];
	}
	/**
	 * @return the genes
	 */
	public float[] getGenes() {
		return genes;
	}
	/**
	 * @param genes the genes to set
	 */
	public void setGenes(float[] genes) {
		this.genes = genes;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	public float getFitness() {
		return fitness;
	}
	
	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	
	public void setFitness(List<Channel> channels) {
		float fitness = 0;
		for(int i = 0 ; i < this.size; i++) {
			fitness+= this.genes[i] * (channels.get(i).getRoi() / 100);
		}
		this.setFitness(fitness);
	}
	
	public void setGenes(List<Channel> channels, float budget) {
		
		float lBound = 0, uBound = 0, random = 0, max = 0, min = 0;
		for (int i = 0; i < channels.size(); i++) {
			lBound = channels.get(i).getlBound() == 0 ? 0 : (channels.get(i).getlBound() / 100 ) * budget;
			uBound = channels.get(i).getuBound() == 0 ? 0 : (channels.get(i).getuBound() / 100 ) * budget;
			max = lBound > uBound ? lBound : uBound;
			min = lBound < uBound ? lBound : uBound;
			random = (intRand.nextInt((int) ((max - min) + min)) + floatRand.nextFloat());
			this.genes[i] = random;
		}
		
	}
	
	public boolean checkConstraints(float budget) {
		float totalallocation = this.getTotalallocation();
		return totalallocation <= budget ;	
	}
	
	private float getTotalallocation() {
		float totalallocation = 0;
		for(float gene : this.genes) {
			totalallocation += gene;
		}
		return totalallocation;
	}
	
	public void fixchromosome(float budget) {
		float totalallocation = this.getTotalallocation();
		if (totalallocation > budget) {
			 
		}else {
			
		}
	}
	
	public void clone(Chromosome chromosome) {
		for(int i = 0; i < this.getSize(); i++) {
			this.genes[i] = chromosome.genes[i];
		}
	}
	
	public void doMutation(float mutationProbability, List<Channel> channels) {
		float probabilityToDoCrossover = 0,diff = 0, diffLower = 0, diffUpper =0;
		
		for(int i = 0; i < this.genes.length; i++) { 
			
			probabilityToDoCrossover = floatRand.nextFloat();
			if (probabilityToDoCrossover < mutationProbability) {
				diffLower = this.genes[i] - channels.get(i).getlBound();
				diffUpper = channels.get(i).getuBound() - this.genes[i];
				
				probabilityToDoCrossover = floatRand.nextFloat();
				
				if (probabilityToDoCrossover <= 0.5) {
					diff = diffLower;
                 } else {
                	 diff = diffUpper;
                 }
				
                if (diff == diffLower) {
                	this.genes[i] = this.genes[i] - diff;
                } else {
                	this.genes[i] = this.genes[i] + diff;
                }
			}
		}
		
	}
	
	public static List<Chromosome> replaceGeneration(List<Chromosome> population, List<Chromosome> currentGeneration) {
		
		List<Chromosome> nextGeneration = new ArrayList<Chromosome>();
		float max = 0;
		int index = 0;
       
		for (int i = 0; i < currentGeneration.size(); i++) {
        	index = 0;
        	max = population.get(i).getFitness();
           
        	for (int j = 0; j < population.size(); j++) {
               if(population.get(j).getFitness() > max) {
            	   max = population.get(j).getFitness();
            	   index = j;
               }
            }
            nextGeneration.add(population.get(index));  
        }
        
        for (int i = 0; i < currentGeneration.size(); i++) {
        	index = 0;
        	max = currentGeneration.get(i).getFitness();
            for (int j = 0; j < currentGeneration.size(); j++) {
                if (currentGeneration.get(j).getFitness() > max) {
                    max = currentGeneration.get(j).getFitness();
                    index = j;
                }
            }
            nextGeneration.add(currentGeneration.get(index));
        }
	    
		return nextGeneration;	
	}
	
	public void display() {
		for(float gene : this.genes) {
			System.out.print(gene + " ");
		}
		System.out.println("Fitness = "+this.getFitness());
	}
	
}
