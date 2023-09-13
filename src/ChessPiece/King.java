package ChessPiece;
import MVC.IChessModel;
import Utils.ChessConstants;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    private final static int[] DC = new int[] {+1, +1, -1, -1, +1, -1,  0, 0};
    private final static int[] DR = new int[] {+1, -1, +1, -1,  0,  0, +1, -1};

    public King(int col, int row, Player player, String imgPath) {
        super(col, row, player, Rank.KING, imgPath);
    }

    public King(int col, int row, Player player) {
        super(col, row, player, Rank.KING);
    }

    @Override
    public Piece clone() {
        return new King(this.getCol(), this.getRow(), this.getPlayer(), this.getImgPath());
    }

    @Override
    public List<Coordinate> getLegalMoves(IChessModel chessModel) {
        List<Coordinate> legalMoves = new ArrayList<Coordinate>();
        int toRow;
        int toCol;

        for(int i = 0; i < DC.length; i++){
            toCol = this.getCol() + DC[i];
            toRow = this.getRow() + DR[i];

            if(CoordUtils.checkValidCoordinate(toCol, toRow)){ // if toRow,toCol is valid
                IPiece targetPiece = chessModel.pieceAt(toCol, toRow);

                if (targetPiece == null){ // if empty tile, can go
                    legalMoves.add(new Coordinate(toCol, toRow));
                } else {
                    if(this.player != targetPiece.getPlayer() ){ // capture an enemy piece
                        legalMoves.add(new Coordinate(toCol, toRow));
                    }
                    // if the piece at target location is the same ally, cannot go
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String retrieveImgPath(){
        if(this.player == Player.WHITE){
            return ChessConstants.wKing;
        } else if(this.player == Player.BLACK){
            return ChessConstants.bKing;
        }
        return null;
    }

    @Override
    public String getRepLetter() {
        return (player == Player.WHITE ? "k" : "K");
    }
}
