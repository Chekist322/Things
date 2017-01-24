package gomoku;

import java.util.*;
import javax.swing.JFrame;

public abstract class GameSearch extends JFrame {

	//public static final boolean DEBUG = false;
    /*
     * Note: the abstract Position also needs to be
     *       subclassed to write a new game program.
     */
    /*
     * Note: the abstract class Move also needs to be subclassed.
     *
     */

	public static boolean PROGRAM = false;
	public static boolean HUMAN = true;

	/**
	 *  Notes:  PROGRAM false -1,  HUMAN true 1
	 */

    /*
     * Abstract methods:
     */

	public abstract boolean drawnPosition(Position p);
	public abstract boolean wonPosition(Position p, boolean player);
	public abstract float positionEvaluation(Position p, boolean player);
	public abstract Position [] possibleMoves(Position p, boolean player);
	public abstract boolean reachedMaxDepth(int depth);

    /*
     * Search utility methods:
     */

	protected Vector alphaBeta(int depth, Position p, boolean player) {
		Vector v = alphaBetaHelper(depth, p, player, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY);
		return v;
	}

	protected Vector alphaBetaHelper(int depth, Position p, boolean player, float alpha, float beta) {
		if (reachedMaxDepth(depth)) {
			Vector v = new Vector(2);
			float value = positionEvaluation(p, player);
			v.addElement(new Float(value));
			v.addElement(null);
			return v;
		}
		Vector best = new Vector();
		Position [] moves = possibleMoves(p, player);
		for (int i=0; i<moves.length; i++) {
			Vector v2 = alphaBetaHelper(depth + 1, moves[i], !player, -beta, -alpha);

			float value = -((Float)v2.elementAt(0)).floatValue();
			if (value > beta) {
				beta = value;
				best = new Vector();
				best.addElement(moves[i]);
				Enumeration enum2 = v2.elements();
				enum2.nextElement(); // skip previous value
				while (enum2.hasMoreElements()) {
					Object o = enum2.nextElement();
					if (o != null) best.addElement(o);
				}
			}
			if (beta >= alpha) {
				break;
			}
		}
		Vector v3 = new Vector();
		v3.addElement(new Float(beta));
		Enumeration enum2 = best.elements();
		while (enum2.hasMoreElements()) {
			v3.addElement(enum2.nextElement());
		}
		return v3;
	}
}