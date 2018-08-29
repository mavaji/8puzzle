package puzzle;

/**
 * @author Vahid Mavaji
 * @version 1.0 2001
 */
class PuzzleNode {
    public PuzzleNode(int z) {
        state = new int[z][z];
        parent = null;

    }

    public int[][] state;
    public PuzzleNode parent;
    public int[] empty_place = new int[2];

    public int gCost;
    public int hCost;
    public int fCost;
}