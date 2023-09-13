package ChessPiece;
import MVC.ChessModel;
import MVC.IChessModel;
import Utils.ChessConstants;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    private final static int[] DC = new int[] { 0,  0, +1, -1 }; // first two are regular move, last 2 are attack
    private final static int[] DR = new int[] {+1, +2, +1, +1 }; // first two are regular move, last 2 are attack

    public Pawn(int col, int row, Player player, String imgPath) {
        super(col, row, player, Rank.PAWN, imgPath);
    }

    public Pawn(int col, int row, Player player) {
        super(col, row, player, Rank.PAWN);
    }


    @Override
    public Piece clone() {
        return new Pawn(this.getCol(), this.getRow(), this.getPlayer(), this.getImgPath());
    }

    @Override
    public List<Coordinate> getLegalMoves(IChessModel chessModel) {
        List<Coordinate> legalMoves = new ArrayList<Coordinate>();
        int toRow;
        int toCol;

        for(int i = 0; i < DC.length; i++){
            toCol = this.col + DC[i]*this.player.getDirection();
            toRow = this.row + DR[i]*this.player.getDirection();

            if(CoordUtils.checkValidCoordinate(toCol, toRow)){ // if toRow, toCol is valid
                IPiece pieceAtDest = chessModel.pieceAt(toCol, toRow);

                // normal move. destination must not be blocked
                if(DC[i] == 0 && DR[i] == 1 ) {
                    if(pieceAtDest == null){
                        legalMoves.add(new Coordinate(toCol, toRow));
                    }

                    // TODO: pawn promotion
                }
                // first move, pawn jump
                else if (DC[i] == 0 && DR[i] == 2 && isFirstMove() ){
                    int ColBeforeDest = this.col + 0*this.player.getDirection();
                    int RowBeforeDest = this.row + 1*this.player.getDirection();
                    IPiece pieceBeforeDest = chessModel.pieceAt(ColBeforeDest, RowBeforeDest);
                    // only jump if there is no piece blocking at 1 step before destination

                    if(pieceBeforeDest == null && pieceAtDest == null ){
                        legalMoves.add(new Coordinate(toCol, toRow));
                    }
                }
                // attack move. go diagonally, there must be another enemy piece there
                else if ( (DC[i] == 1 || DC[i] == -1) &&
                           pieceAtDest != null &&
                           this.player != pieceAtDest.getPlayer() ){

                    legalMoves.add(new Coordinate(toCol, toRow));
                }

                // en passant
                else if ( (DC[i] == 1 || DC[i] == -1) &&
                            pieceAtDest == null &&
                            canEnPassant(chessModel) &&
                            toCol == ((ChessModel)chessModel).getEnPassantPawn().getCol() ){

                    legalMoves.add(new Coordinate(toCol, toRow));
                }

            }
        }

        return legalMoves;
    }

    /**
     * Check if this pawn can make enPassant move
     * @param chessModel current state of the chess game
     * @return  if this pawn can make enPassant move
     */
    private boolean canEnPassant(IChessModel chessModel){
        Pawn enPassantPawn = ((ChessModel)chessModel).getEnPassantPawn();
        if (enPassantPawn == null) return false;
        if(! ((this.player == Player.WHITE && this.row == 4) ||
                (this.player == Player.BLACK && this.row == 3) ) ) return false;

        if( enPassantPawn != null &&
            ((this.player == Player.WHITE && this.row == 4) ||
             (this.player == Player.BLACK && this.row == 3) )  &&
                enPassantPawn.getPlayer() != this.player &&
                Math.abs(enPassantPawn.getCol() - this.col) == 1
        ) return true;

        return false;

    }


    /*
     * Check if the pawn is making the first move
     */
    public boolean isFirstMove(){
        return (this.player == Player.WHITE && this.row == 1) ||
                (this.player == Player.BLACK && this.row == 6);
    }

    @Override
    public String retrieveImgPath(){
        if(this.player == Player.WHITE){
            return ChessConstants.wPawn;
        } else if(this.player == Player.BLACK){
            return ChessConstants.bPawn;
        }
        return null;
    }

    @Override
    public String getRepLetter() {
        return (player == Player.WHITE ? "p" : "P");
    }
}
