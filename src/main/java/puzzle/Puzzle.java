package puzzle;

/**
 * @author Vahid Mavaji
 * @version 1.0 2001
 */
public class Puzzle {
    private int size;
    private int[][] puzzle;
    private PuzzleQueue queue = new PuzzleQueue();
    private int[] empty_place = new int[2];
    private int[] last_empty = new int[2];

    public Puzzle() {
        size = 3;
        puzzle = new int[size][size];
    }

    public Puzzle(int z) {
        size = z;
        puzzle = new int[size][size];
    }

    public int get_fCost() {
        return get_gCost() + get_hCost();
    }

    public int get_gCost() {
        return 0;
    }

    public int get_hCost() {
        int row, col;
        int h = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (puzzle[i][j] != 0) {
                    row = (puzzle[i][j] - 1) / size;
                    col = (puzzle[i][j] - 1) % size;
                    h += Math.abs(i - row) + Math.abs(j - col);
                }
            }
        }
        return h;
    }

    public void MakeInitialState() {
        final int count = 100;

        int i;
        for (i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puzzle[i][j] = i * size + j + 1;
            }
        }
        puzzle[size - 1][size - 1] = 0;
        int row = size - 1;
        int col = size - 1;

        for (i = 0; i < count; i++) {
            int n = (int) (Math.random() * 4);
            switch (n) {
                case 0:
                    if ((row - 1) >= 0) {
                        puzzle[row][col] = puzzle[row - 1][col];
                        puzzle[row - 1][col] = 0;
                        row--;
                    }
                    break;

                case 1:
                    if ((row + 1) < size) {
                        puzzle[row][col] = puzzle[row + 1][col];
                        puzzle[row + 1][col] = 0;
                        row++;
                    }
                    break;

                case 2:
                    if ((col - 1) >= 0) {
                        puzzle[row][col] = puzzle[row][col - 1];
                        puzzle[row][col - 1] = 0;
                        col--;
                    }
                    break;

                case 3:
                    if ((col + 1) < size) {
                        puzzle[row][col] = puzzle[row][col + 1];
                        puzzle[row][col + 1] = 0;
                        col++;
                    }
                    break;
            }
        }
        empty_place[0] = row;
        empty_place[1] = col;

        last_empty[0] = row;
        last_empty[1] = col;

        PuzzleNode p = new PuzzleNode(size);
        p.gCost = 0;
        p.hCost = get_hCost();
        p.fCost = p.gCost + p.hCost;

        for (i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                p.state[i][j] = puzzle[i][j];
            }
        }

        p.empty_place[0] = row;
        p.empty_place[1] = col;
        queue.insert(p);
        ShowPuzzle(p);
    }

    public void SelectNextState() {
        while (true) {
            if (queue.isEmpty()) {
                return;
            }
            PuzzleNode bestNode = new PuzzleNode(size);
            bestNode = queue.remove();
            if (CheckGoalState(bestNode.state)) {
                ShowPuzzle(bestNode);
                return;
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    puzzle[i][j] = bestNode.state[i][j];
                }
            }
            int row = bestNode.empty_place[0];
            int col = bestNode.empty_place[1];

            if ((row - 1) >= 0) {
                puzzle[row][col] = puzzle[row - 1][col];
                puzzle[row - 1][col] = 0;

                PuzzleNode p = new PuzzleNode(size);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        p.state[i][j] = puzzle[i][j];
                    }
                }
                p.parent = bestNode;
                p.hCost = get_hCost();
                p.gCost = p.parent.gCost + 1;
                p.fCost = (p.parent.fCost > p.gCost + p.hCost) ? p.parent.fCost : p.gCost + p.hCost;
                p.empty_place[0] = row - 1;
                p.empty_place[1] = col;
                queue.insert(p);

                puzzle[row - 1][col] = puzzle[row][col];
                puzzle[row][col] = 0;

            }
            if ((row + 1) < size) {
                puzzle[row][col] = puzzle[row + 1][col];
                puzzle[row + 1][col] = 0;

                PuzzleNode p = new PuzzleNode(size);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        p.state[i][j] = puzzle[i][j];
                    }
                }
                p.parent = bestNode;
                p.hCost = get_hCost();
                p.gCost = p.parent.gCost + 1;
                p.fCost = (p.parent.fCost > p.gCost + p.hCost) ? p.parent.fCost : p.gCost + p.hCost;
                p.empty_place[0] = row + 1;
                p.empty_place[1] = col;
                queue.insert(p);

                puzzle[row + 1][col] = puzzle[row][col];
                puzzle[row][col] = 0;

            }
            if ((col - 1) >= 0) {
                puzzle[row][col] = puzzle[row][col - 1];
                puzzle[row][col - 1] = 0;

                PuzzleNode p = new PuzzleNode(size);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        p.state[i][j] = puzzle[i][j];
                    }
                }
                p.parent = bestNode;
                p.hCost = get_hCost();
                p.gCost = p.parent.gCost + 1;
                p.fCost = (p.parent.fCost > p.gCost + p.hCost) ? p.parent.fCost : p.gCost + p.hCost;
                p.empty_place[0] = row;
                p.empty_place[1] = col - 1;
                queue.insert(p);

                puzzle[row][col - 1] = puzzle[row][col];
                puzzle[row][col] = 0;

            }
            if ((col + 1) < size) {
                puzzle[row][col] = puzzle[row][col + 1];
                puzzle[row][col + 1] = 0;

                PuzzleNode p = new PuzzleNode(size);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        p.state[i][j] = puzzle[i][j];
                    }
                }
                p.parent = bestNode;
                p.hCost = get_hCost();
                p.gCost = p.parent.gCost + 1;
                p.fCost = (p.parent.fCost > p.gCost + p.hCost) ? p.parent.fCost : p.gCost + p.hCost;
                p.empty_place[0] = row;
                p.empty_place[1] = col + 1;
                queue.insert(p);

                puzzle[row][col + 1] = puzzle[row][col];
                puzzle[row][col] = 0;
            }
        }

    }

    public boolean CheckGoalState(int[][] array) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (array[i][j] != (size * i + j + 1) && array[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void ShowPuzzle(PuzzleNode p) {
        while (p != null) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (p.state[i][j] != 0) {
                        System.out.print(p.state[i][j] + " ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
            System.out.println("*********************");
            p = p.parent;
        }
    }

    public static void main(String arg[]) {
        Puzzle puzzle = new Puzzle(3);
        puzzle.MakeInitialState();
        puzzle.SelectNextState();
    }
}






