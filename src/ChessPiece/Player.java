package ChessPiece;

/**
 * Represent a chess player - black or white
 * @author tienbui
 *
 */
public enum Player{
    WHITE{
    	/**
    	 * Get direction for the Pawn movement
    	 */
        public int getDirection() {
            return +1;
        }

        @Override
        public String toString() {
            return "WHITE";
        }
    }
    ,
    BLACK {
    	/**
    	 * Get direction for the Pawn movement
    	 */
        public int getDirection() {
            return -1;
        }

        @Override
        public String toString() {
            return "BLACK";
        }
    };

	/**
	 * Get direction for the Pawn movement
	 */
    public abstract int getDirection() ;

    @Override
    public abstract String toString() ;
}
