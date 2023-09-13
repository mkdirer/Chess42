package ChessPiece;
import MVC.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Piece implements IPiece{
    int row, col;
    Player player;
    Rank rank;
    String imgPath;

    /**
     * Initialize a chess piece
     * @param col Column position of the chess piece
     * @param row Row position of the chess piece
     * @param player Player of the piece (black or white))
     * @param rank Rank of the piece
     * @param imgPath Path to image representing the piece stored in local files
     */
    public Piece (int col, int row, Player player, Rank rank, String imgPath) { //
        this.row = row;
        this.col = col;
        this.player = player;
        this.rank = rank;
        this.imgPath = imgPath;
    }

    /**
     * Initialize a chess piece. Path to image is automatically retrieved
     * @param col Column position of the chess piece
     * @param row Row position of the chess piece
     * @param player Player of the piece (black or white))
     * @param rank Rank of the piece
     */
    public Piece(int col, int row, Player player, Rank rank) {
        this.row = row;
        this.col = col;
        this.player = player;
        this.rank = rank;
        this.imgPath = this.retrieveImgPath();
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setRow(int row) throws IllegalArgumentException {
    	if(row < 0 || row > 7) {
    		throw new IllegalArgumentException();
    	}
        this.row = row;
    }

    @Override
    public void setCol(int col) throws IllegalArgumentException {
    	if(col < 0 || col > 7) {
    		throw new IllegalArgumentException();
    	}
        this.col = col;
    }

    @Override
    public Rank getRank() {
        return rank;
    }

    @Override
    public String getImgPath() {
        return imgPath;
    }

    @Override
    public String toString() {
        return "{" +                
                "col=" + col +
                ", row=" + row +
                ", player=" + player +
                ", rank=" + rank +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }

    public abstract Piece clone();

    @Override
    public void printLegalMoves(IChessModel chessModel){
        List<Coordinate> legalMoves = this.getLegalMoves(chessModel);
        System.out.println("-----");
        if (legalMoves == null){
            System.out.print("null");
            return;
        }
        
        System.out.println(
        		legalMoves
        		.stream()
        		.map(Object::toString)
        		.collect(Collectors.joining("\n"))
            );
        
        System.out.println("-----");
    }
    
    @Override
    public boolean equals(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof IPiece)) {
            return false; //other cannot be equal to this as it is not a Coordinate object!
        }
        IPiece otherPiece = (IPiece) other; //this will work

        return  this.col == otherPiece.getCol() &&
                this.row == otherPiece.getRow() &&
                this.rank == otherPiece.getRank() &&
                this.player == otherPiece.getPlayer();
    }

}
