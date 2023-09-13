package ChessPiece;
import MVC.*;

/**
 * Support methods to check if a coordinate value is valid
 * @param col Column of the coordinate on chess board
 * @param row Row of the coordinate on chess board
 * @return if the coordinate given is valid
 */
public class CoordUtils {
	
	/**
	 * Check if the given coordinate is valid
	 * @param coord Coordinate with (col, row)
	 * @return if the given coordinate is valid
	 */
    public static boolean checkValidCoordinate(Coordinate coord){
        int col = coord.getCol();
        int row = coord.getRow();
        return checkValidCoordinate(col, row);

    }

    /**
     * Check if the given row and column of a Coordinate object is valid
     * @param col Column of the coordinate on chess board
     * @param row Row of the coordinate on chess board
     * @return if the coordinate given is valid
     */
    public static boolean checkValidCoordinate(int col, int row){
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }
}
