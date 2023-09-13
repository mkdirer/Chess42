package Utils;

public abstract class ChessConstants {
    public final static String bBishop = "res/img/" + "Bishop-black" + ".png";
    public final static String wBishop = "res/img/" + "Bishop-white" + ".png";
    public final static String bKing = "res/img/" + "King-black" + ".png";
    public final static String wKing = "res/img/" + "King-white" + ".png";
    public final static String bKnight = "res/img/" + "Knight-black" + ".png";
    public final static String wKnight = "res/img/" + "Knight-white" + ".png";
    public final static String bPawn = "res/img/" + "Pawn-black" + ".png";
    public final static String wPawn = "res/img/" + "Pawn-white" + ".png";
    public final static String bQueen = "res/img/" + "Queen-black" + ".png";
    public final static String wQueen = "res/img/" + "Queen-white" + ".png";
    public final static String bRook = "res/img/" + "Rook-black" + ".png";
    public final static String wRook = "res/img/" + "Rook-white" + ".png";


    public static String[] getPieceImgPaths(){
        String[] out = new String[]{bBishop, wBishop,
                                    bKing, wKing,
                                    bKnight, wKnight,
                                    bPawn, wPawn,
                                    bQueen, wQueen,
                                    bRook, wRook };
        return out;
    }
}
