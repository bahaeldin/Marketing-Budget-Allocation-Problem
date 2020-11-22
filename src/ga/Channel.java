/**
 * 
 */
package ga;

/**
 * @author BAHAELDIN
 *
 */
public class Channel {
	private String name;
	private float roi;
	private float uBound;
	private float lBound;
	/**
	 * @param name
	 * @param roi
	 * @param uBound
	 * @param lBound
	 */
	public Channel(String name, float roi, float uBound, float lBound) {
		this.name = name;
		this.roi = roi;
		this.uBound = uBound;
		this.lBound = lBound;
	}
	public Channel() {
		this.name = "";
		this.roi = 0;
		this.uBound = 0;
		this.lBound = 0;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the roi
	 */
	public float getRoi() {
		return roi;
	}
	/**
	 * @param roi the roi to set
	 */
	public void setRoi(float roi) {
		this.roi = roi;
	}
	/**
	 * @return the uBound
	 */
	public float getuBound() {
		return uBound;
	}
	/**
	 * @param uBound the uBound to set
	 */
	public void setuBound(float uBound) {
		this.uBound = uBound;
	}
	/**
	 * @return the lBound
	 */
	public float getlBound() {
		return lBound;
	}
	/**
	 * @param lBound the lBound to set
	 */
	public void setlBound(float lBound) {
		this.lBound = lBound;
	}
	
	
	public Channel parsLine(String line, Boolean flag) {
		
		String[] temp = line.split(" ");		
		if (flag) {
			if (temp.length > 2) {
				for (int i = 0; i < temp.length - 1; i++) {
					this.name += temp[i];
				}
				this.roi = Float.parseFloat(temp[temp.length - 1]);
			}else {
				this.name = temp[0];
				this.roi = Float.parseFloat(temp[1]);
			}
		}else {
			temp[0] = temp[0].equals("x") ? "0" : temp[0];
			temp[1] = temp[1].equals("x") ? "0" : temp[1];
			this.lBound = Float.parseFloat(temp[0]);
			this.uBound = Float.parseFloat(temp[1]);
		}		
		return this;
	}
	
}
