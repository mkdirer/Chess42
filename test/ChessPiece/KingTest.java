package ChessPiece;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Arrays;
import java.util.List;

import MVC.ChessModel;
import MVC.IChessModel;


class KingTest {
	private Piece k1;
	private Piece k2;
	private Piece r1;
	private Piece r2;
	private IChessModel chessModel1;

	@BeforeEach
	void setUp(){
		k1 = new King(3, 5, Player.WHITE); 
		k2 = new King(4, 0, Player.BLACK); 
		r1 = new Rook(3, 4, Player.WHITE); 
		r2 = new Rook(4, 5, Player.BLACK); 
		Piece[] piecesArray = new Piece[] {k1,k2,r1,r2};

		List<IPiece> activePieces = new ArrayList<IPiece>(Arrays.asList(piecesArray));

		chessModel1 = new ChessModel(activePieces);


	}


	@Test
	void activePiecesTest(){
		chessModel1.printActivePieces();
		System.out.println(chessModel1);
		
		assertTrue(chessModel1.getActivePieces().contains(new King(3, 5, Player.WHITE) ));
		assertTrue(chessModel1.getActivePieces().contains(new King(4, 0, Player.BLACK) ));
		assertTrue(chessModel1.getActivePieces().contains(new Rook(3, 4, Player.WHITE) ));
		assertTrue(chessModel1.getActivePieces().contains(new Rook(4, 5, Player.BLACK) ));
	}

	@Test
	void moveTestAdjacentPiece(){
		//TODO: write test for legal move. based on printed moves, print is correct here.
		System.out.println(chessModel1);
		k1.printLegalMoves(chessModel1);
		
		List<Coordinate> legalMovesK1 = k1.getLegalMoves(chessModel1);
		assertTrue(legalMovesK1.contains(new Coordinate(4,6) ));
		assertTrue(legalMovesK1.contains(new Coordinate(4,4) ));
		assertTrue(legalMovesK1.contains(new Coordinate(2,6) ));
		assertTrue(legalMovesK1.contains(new Coordinate(2,4) ));
		assertTrue(legalMovesK1.contains(new Coordinate(4,5) ));
		assertTrue(legalMovesK1.contains(new Coordinate(2,5) ));
		assertTrue(legalMovesK1.contains(new Coordinate(3,6) ));
		
		// cannot go where there's an ally
		assertFalse(legalMovesK1.contains(new Coordinate(3,4) ));
		
		
	}
	
	@Test
	void moveTestBoardBorder(){
		//TODO: write test for legal move. based on printed moves, print is correct here.
		k2.printLegalMoves(chessModel1);
		
		List<Coordinate> legalMovesK2 = k2.getLegalMoves(chessModel1);
		
		assertTrue(legalMovesK2.contains(new Coordinate(5,1) ));
		assertTrue(legalMovesK2.contains(new Coordinate(3,1) ));
		assertTrue(legalMovesK2.contains(new Coordinate(5,0) ));
		assertTrue(legalMovesK2.contains(new Coordinate(3,0) ));
		assertTrue(legalMovesK2.contains(new Coordinate(4,1) ));
		assertTrue(legalMovesK2.size() == 5);
		
	}

}
