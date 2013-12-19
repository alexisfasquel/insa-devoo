package Tools.Tsp;
import java.util.ArrayList;

/**
 * @author Christine Solnon
 *
 */

public class RegularGraph1 implements Graph {
	private final int mNbVertices;
	private int mMaxArcCost = Integer.MIN_VALUE;
	private int mMinArcCost = Integer.MAX_VALUE;
	private int[][] mCost; 
	private ArrayList<ArrayList<Integer>> mSucc;

	/**
	 * Creates a graph such that each vertex is connected to the next <code>d</code> vertices (modulo <code>n</code>) and
	 * such that the cost of each arc is a randomly chosen integer ranging between <code>min</code> and <code>max</code>
	 * @param nbVertices a number of vertices such that <code>n > 0</code>
	 */
	public RegularGraph1(int nbVertices){
            mNbVertices = nbVertices;
            mCost = new int[nbVertices][nbVertices];
            mSucc = new ArrayList<>();
            for (int i = 0; i < nbVertices; i++) {
                mSucc.add(new ArrayList<Integer>());
            }
	}

        public void addCost(int i, int j, int cost) {
            mCost[i][j] = cost;
            if(mMaxArcCost < cost) {
                mMaxArcCost = cost;
            }
            if (mMinArcCost > cost) {
                mMinArcCost = cost;
            } 
       }
        
        public void addSucc(int target, int succ) throws ArrayIndexOutOfBoundsException{
            mSucc.get(target).add(succ);
        }
        
        
        @Override
	public int getMaxArcCost() {
            return mMaxArcCost;
	}

        @Override
	public int getMinArcCost() {
            return mMinArcCost;
	}

        @Override
	public int getNbVertices() {
            return mNbVertices;
	}

        @Override
	public int[][] getCost(){
            return mCost;
	}
        
        @Override
	public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
            int[] tab = new int[mSucc.get(i).size()];
            for(int j=0;j<tab.length;j++){
                tab[j] = mSucc.get(i).get(j);
            }
            return tab;
	}


        @Override
	public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
		return mSucc.get(i).size();
	}


}
