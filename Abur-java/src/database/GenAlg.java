package database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex
 *
 */
public class GenAlg {
	private List<Chromosone> population;
	private String start_date;
	private float days;
	private List<Float> fitness;
	private float values[];
	private String game_id;
	private int Generation; 
	
	public GenAlg(String y) {
		this.population = new ArrayList<Chromosone>();
		this.fitness = new ArrayList<Float>();
		this.values=new float[10];
		for(int i=0; i<100; i++) {
			Chromosone x = new Chromosone();
			this.population.add(x);
			this.fitness.add(0.0f);
		}
		this.game_id=y;
	}
	
	public void getDatesAndValues() throws SQLException{
		Connection con = Database.getConnection();
        Statement stms = con.createStatement();
        ResultSet result = stms.executeQuery("SELECT max(added_at) - min(added_at) FROM changes where game_id = " + this.game_id);
        result.next();
        this.days = result.getFloat(1);
        stms = con.createStatement();
        result = stms.executeQuery("SELECT min(added_at) FROM changes where game_id = " + this.game_id );
        result.next();
        this.start_date = result.getString(1);
        if(this.start_date == null) {
        	stms = con.createStatement();
            result = stms.executeQuery("SELECT added_at FROM games where id = " + this.game_id );
            result.next();
            this.start_date = result.getString(1);
        }
        int i=0;
        DecimalFormat d= new DecimalFormat("0.00");
        String sql;
        if(this.days == 0.0f ) {
        	this.days = 1.0f;
        }
//        System.out.println(this.days);
//        System.out.println(this.start_date);
        ResultSet res;
        sql="SELECT price FROM games where id = " + this.game_id;
        res = stms.executeQuery(sql);
        if(res.next()) {
        	float x = res.getFloat(1);
        	System.out.print(x);
        	for(int j=0;j<10;j++)
        		values[j]=(float)x;
        }
        for(i=1;i<11;i++) {
        	sql="SELECT price FROM changes where game_id = " + this.game_id + "and added_at<(to_date('" + this.start_date.substring(0, this.start_date.indexOf(' ')) + "','yyyy-mm-dd') + " + d.format(this.days/10*i)+") order by added_at desc";
//        	System.out.print(sql);
        	res = stms.executeQuery(sql);
        	if(res.next())
        		values[i-1]=res.getFloat(1);
        }
	}
	
	public void eval() {
		float x;
		for(int i=0;i<100;i++) {
			Float y = new Float(0.0f);
			this.fitness.add(y);
			for(int j=0;j<9;j++) {
//				System.out.println(this.values[j]);
//				System.out.println(population.get(i).eval(j));
//				System.out.println(this.fitness.get(i));
				x=this.fitness.get(i)+Math.abs(population.get(i).eval(j)-this.values[j]);
				this.fitness.set(i, x);
			}
		}
	}
	
	public void select() {
		float fitness_total = 0.0f;
		float chance[]=new float[100];
		float wheel[]=new float[101];
		double r;
		List<Chromosone> new_pop= new ArrayList<Chromosone>();
		wheel[0]=0.0f;
		int j=0;
		for(int i=0;i<100;i++)
			fitness_total+=this.fitness.get(i);
		for(int i=0;i<100;i++) {
			chance[i]=this.fitness.get(i)/fitness_total;
			wheel[i+1]=wheel[i]+chance[i];
		}
		for(int i=0;i<100;i++) {
			j=0;
			r=Math.random();
			while(r<wheel[j]&&j<99)
				j++;
			new_pop.add(this.population.get(j));
		}
		this.population=new_pop;
	}
	
	public void interchange() {
		Chromosone x,y;
		for(int i=0;i<99;i++)
			for(int j=i+1;j<100;j++) {
				x=this.population.get(i);
				y=this.population.get(j);
				x.mutation();
				y.mutation();
				x.crossover(y);
				this.population.set(i,x);
				this.population.set(j,y);
			}
	}
	
	public float exit() {
		float ret=0.0f;
		for(int i=0;i<100;i++)
			ret+=this.population.get(i).eval(9);
		return ret*10;
	}
	
	public float start() {
		this.Generation=100;
		this.eval();
		while(this.Generation>0) {
			this.select();
			this.interchange();
			this.eval();
			this.Generation--;
		}
		return this.exit();
	}
}
