package ChessPiece;
import MVC.*;

/**
 * Represent the coordinate(col, row) for a tile on a chess board
 * @author tienbui
 *
 */
public class Coordinate {
    private int row;
    private int col;

    /**
     * Initialize a coordinate object with column and row
     * @param col: column position of the tile
     * @param row: row positio of the tile
     */
    public Coordinate(int col, int row) {
        this.row = row;
        this.col = col;
    }

    /**
     * get the row coordinate of the tile
     * @return row coordinate of the tile
     */
    public int getRow(){
        return this.row;
    }

    /**
     * set the row coordinate of the tile
     * @param row row coordinate of the tile
     */
    public void setRow(int row){
        this.row = row;
    }

    /**
     * get the column coordinate of the tile
     * @return column coordinate of the tile
     */
    public int getCol(){
        return this.col;
    }
    
    /**
     * set the column coordinate of the tile
     * @param column column coordinate of the tile
     */
    public void setCol(int col){
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Coordinate)) {
            return false; //other cannot be equal to this as it is not a Coordinate object!
        }
        Coordinate otherCoord = (Coordinate) other; //this will work

        return  this.col == otherCoord.getCol() &&
                this.row == otherCoord.getRow();
    }

    @Override
    public Coordinate clone(){
        return new Coordinate(this.getCol(), this.getRow());
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "iCol=" + col +
                ", iRow=" + row +
                '}';
    }
}
