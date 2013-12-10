package Tools.Tsp;
import java.util.ArrayList;

/**
 * @author Christine Solnon
 *
 */

public class RegularGraph implements Graph {
	private int mNbVertices;
	private int mMaxArcCost;
	private int mMinArcCost;
	private int[][] mCost; 
	private ArrayList<ArrayList<Integer>> mSucc; 

	/**
	 * Creates a graph such that each vertex is connected to the next <code>d</code> vertices (modulo <code>n</code>) and
	 * such that the cost of each arc is a randomly chosen integer ranging between <code>min</code> and <code>max</code>
	 * @param n a number of vertices such that <code>n > 0</code>
	 * @param d a degree such that <code>0 < d < n</code>
	 * @param min a minimal arc cost such that <code>0 < min</code>
	 * @param max a maximal arc cost such that <code>min < max</code>
	 */
	public RegularGraph(int n, int min, int max, int [][]costs, ArrayList<ArrayList<Integer>> succ){
		mNbVertices = n;
		mMinArcCost = min;
		mMaxArcCost = max;
		mCost = costs;
		mSucc = succ;
	}

	public int getMaxArcCost() {
            return mMaxArcCost;
	}

	public int getMinArcCost() {
            return mMinArcCost;
	}

	public int getNbVertices() {
            return mNbVertices;
	}

	public int[][] getCost(){
            return mCost;
	}

	public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException{
            if ((i<0) || (i>=mNbVertices))
                throw new ArrayIndexOutOfBoundsException();
            int[] tab = new int[mSucc.get(i).size()];
            for(int j=0;j<tab.length;j++){
                tab[j] = mSucc.get(i).get(j);
            }
            return tab;
	}


	public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
		if ((i<0) || (i>=mNbVertices))
			throw new ArrayIndexOutOfBoundsException();
		return mSucc.get(i).size();
	}


}
