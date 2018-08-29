package puzzle;

import java.util.Vector;

/**
 * @author Vahid Mavaji
 * @version 1.0 2001
 */
class PuzzleQueue {
    public PuzzleQueue() {
        queue = new Vector();
    }

    public void insert(PuzzleNode p) {
        if (queue.isEmpty()) {
            queue.addElement(p);
            return;
        }
        int i;
        for (i = 0; i < queue.size(); i++) {
            if (p.fCost > ((PuzzleNode) queue.elementAt(i)).fCost) {
                queue.add(i, p);
                return;
            }
        }
        if (i == queue.size()) {
            queue.addElement(p);
        }
        return;
    }

    public PuzzleNode remove() {
        PuzzleNode p = (PuzzleNode) queue.lastElement();
        queue.removeElementAt(queue.size() - 1);
        return p;
    }

    public boolean isEmpty() {
        if (queue.isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }

    private Vector queue;
}