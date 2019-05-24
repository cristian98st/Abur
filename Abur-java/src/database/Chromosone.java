/**
 * 
 */
package database;

/**
 * @author Alex
 *
 */
public class Chromosone {
	private boolean bits[];
	private static final float CROSS_RATE = (float) 0.25;
	private static final float MUT_RATE = (float) 0.05;
	
	public Chromosone() {
		this.bits = new boolean[70];
		for(int i=0;i<70;i++)
			this.bits[i]=(Math.random()<0.5);
	}
	
	public void setBit(int pos,boolean val) {
		this.bits[pos]=val;
	}
	
	public boolean[] getBits() {
		return this.bits;
	}
	
	public void crossover(Chromosone x) {
		boolean other_bits [] = x.getBits();
		boolean sw;
		for(int i=0;i<70;i++)
			if(Math.random()<CROSS_RATE) {
				sw = other_bits[i];
				x.setBit(i, this.bits[i]);
				this.bits[i] = sw;
			}
	}
	
	public float eval(int nr) {
		float ret=0;
		for(int j=nr*7;j<(7+(nr*7));j++) {
			if(this.bits[j])
				ret+=Math.pow(2, j-nr);
		}
		return ret;
	}
	
	public void mutation() {
		for(int i=0;i<70;i++)
			if(Math.random()<MUT_RATE) {
				this.bits[i] = !this.bits[i];
			}
	}

}
