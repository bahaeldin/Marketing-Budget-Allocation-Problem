package ga;

public class Chromosome {

	private float[] genes;
	private int size;
	public Chromosome(int size) {
		this.size = size;
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
	
	
}
