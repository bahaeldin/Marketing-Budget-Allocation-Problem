package ga;

public class Chromosome {

	private float[] genes;
	private int size;
	public Chromosome(int size) {
		this.size = size;
		genes = new float[size];
	}
}
