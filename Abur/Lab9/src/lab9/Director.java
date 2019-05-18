/**
 * 
 */
package lab9;

/**
 * @author Alex
 *
 */
public class Director extends Person {
	private int id;
	private String name;
	public Director() {
	}
	public Director(int i,String n) {
		this.id=i;
		this.name=n;
	}
	
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id=id;
		
	}

	@Override
	public String getNume() {
		return this.name;
	}

	@Override
	public void setNume(String nume) {
		this.name=nume;
	}

}
