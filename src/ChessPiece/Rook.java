package ChessPiece;
import MVC.IChessModel;
import Utils.ChessConstants;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private final static int[] DC = new int[] {+1, -1,  0,  0};
    private final static int[] DR = new int[] { 0,  0, +1, -1};

    public Rook(int col, int row, Player player, String imgPath) {
        super(col, row, player, Rank.ROOK, imgPath);
    }

    public Rook(int col, int row, Player player) {
        super(col, row, player, Rank.ROOK);
    }

    @Override
    public Piece clone() {
        return new Rook(this.getCol(), this.getRow(), this.getPlayer(), this.getImgPath());
    }


    @Override
    public List<Coordinate> getLegalMoves(IChessModel chessModel){
        List<Coordinate> legalMoves = new ArrayList<Coordinate>();

        for(int i = 0; i < DC.length; i++){
            int toRow = this.row;
            int toCol = this.col;

            while (CoordUtils.checkValidCoordinate(toCol, toRow)){
                toCol += DC[i];
                toRow += DR[i];

                if(CoordUtils.checkValidCoordinate(toCol, toRow)){
                    IPiece targetPiece = chessModel.pieceAt(toCol, toRow);

                    if(targetPiece == null){ // if toCol, toRow is empty
                        legalMoves.add(new Coordinate(toCol, toRow));
                    } else { // if there is a piece on the way, cannot move forward
                        // if target piece is ally, cannot go. break
                        // if target piece is enemy, can go. break
                        if(targetPiece.getPlayer() != this.player){
                            legalMoves.add(new Coordinate(toCol, toRow)); // attack move
                        }
                        break;
                    }
                }
            }

        }
        return legalMoves;
    }
    @Override
    public String retrieveImgPath(){
        if(this.player == Player.WHITE){
            return ChessConstants.wRook;
        } else if(this.player == Player.BLACK){
            return ChessConstants.bRook;
        }
        return null;
    }

    @Override
    public String getRepLetter() {
        return (player == Player.WHITE ? "r" : "R");
    }
}
