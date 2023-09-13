package MVC;

import ChessPiece.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.Arrays;
import java.util.List;

public class Controller implements IController, MouseListener, MouseMotionListener, ActionListener { //, Runnable
    private IChessModel chessModel;
    private IViewFrame chessView;

    private int fromCol;
    private int fromRow;
    private IPiece movingPiece;
    private Point mouseDragPoint;
    
    // for sending and receiving information via socket
    private final String SOCKET_SERVER_ADDRESS = "localhost"; //"192.168.1.9";
    private final int PORT = 50000;
    private Socket socket;
    private ServerSocket listener;
    private PrintWriter printWriter;
     

    /**
     * Initlize the controller of the Java program
     */
    public Controller(){
        chessModel = new ChessModel();
        chessModel.reset();

        chessView = new ViewFrame(this);

        chessView.setButtonListener(this);
        chessView.setMouseListener(this);
        chessView.setMouseMotionListener(this);
        
        
        // for sending and receiving information via socket
        ((JFrame) chessView).addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) { 
        		// close the input and output stream after closing the game window
        		super.windowClosing(e);
        		try {
        			if(printWriter != null)	printWriter.close();
					if(socket != null) socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        }); 
        
        // print the chess board layout to console
        System.out.println(chessModel);
    }

    @Override
    public IPiece pieceAt(int col, int row) {
        return chessModel.pieceAt(col, row);
    }

    @Override
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow) {
        try{
            chessModel.movePiece(fromCol, fromRow, toCol, toRow);
            chessView.repaint(); // refresh the view to move the chess piece
            System.out.println(chessModel);
            chessView.setHighlightedCoords(null);
            chessView.repaint();
            
            // printWriter to send information between client and server via socket
            if(printWriter != null) {
            	printWriter.println(fromCol + "," + fromRow + "," + toCol + "," + toRow);
            }
            // if game is over, display a message.
            Player winner = chessModel.getWinner();
            System.out.println("from controller. Get winner " + winner);
            if(winner != null ){
                movingPiece = null;
                mouseDragPoint = null;
                playWinningSound();
                JOptionPane.showMessageDialog((JFrame)chessView, "PLAYER " + winner.toString() + " WINS");
            }
        } catch(IllegalStateException ex1){
            ex1.printStackTrace();
            return;
        } catch(IllegalArgumentException ex2) {
        	ex2.printStackTrace();
        	return;
        }
    }

    /**
     * Play a sound when there is a winner (ie when a king is captured)
     */
    private void playWinningSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/res/sound/winning_sound.wav")));
            clip.start();
        }

        catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    /**
     * Reset the game. From IChessModel
     */
    private void reset(){
        chessModel.reset();
        chessView.repaint();
        System.out.println(chessModel);
    }

    @Override
    public boolean isGameOver() {
//        System.out.println("is game over invoked from controller " + chessModel.isGameOver() );
        return chessModel.isGameOver();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        fromCol = pointX2Col(e.getPoint().x);
        fromRow = pointY2Row(e.getPoint().y);
        movingPiece = chessModel.pieceAt(fromCol, fromRow);
        if(isValidTurn(movingPiece) && !isGameOver() ){
            List<Coordinate> legalMoves = movingPiece.getLegalMoves(chessModel);
            chessView.setHighlightedCoords(legalMoves);
            chessView.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int toCol = pointX2Col(e.getPoint().x);
        int toRow = pointY2Row(e.getPoint().y);

        if (fromCol != toCol || fromRow != toRow) {
            movePiece(fromCol, fromRow, toCol, toRow);
//            System.out.println("from mouseRelease controller " + fromCol + "," + fromRow + "," + toCol + "," + toRow);
        }

        this.movingPiece = null;
        this.mouseDragPoint = null;
        chessView.setHighlightedCoords(null);
        chessView.repaint();
    }

    /**
     * Convert the coordinate of the mouse position to coordinate of the board with column and row
     * @param x x horizontal coordinate of the mouse on JPanel
     * @return Column position on the chess board
     */
    private int pointX2Col(int x){
        return (x - BoardPanel.X0) / BoardPanel.CELL_SIZE;
    }

    /**
     * Convert the coordinate of the mouse position to coordinate of the board with column and row
     * @param y vertical coordinate of the mouse on JPanel
     * @return Row position on the chess board
     */
    private int pointY2Row(int y){
        return (y - BoardPanel.Y0) / BoardPanel.CELL_SIZE;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (movingPiece == null) return;
        this.mouseDragPoint = e.getPoint();
//        chessView.repaint();
        if(! isGameOver() && isValidTurn(movingPiece) ){
            chessView.repaint();
        }
    }

    @Override
    public Point getMouseDragPoint() {
        return mouseDragPoint;
    }

    @Override
    public IPiece getMovingPiece() {
        return movingPiece;
    }

    /**
     * If the currently selected chess piece is at the correct player turn
     * @param movingPiece2 the currently selected chess piece 
     * @return If the currently selected chess piece is at the correct player turn
     */
    private boolean isValidTurn(IPiece movingPiece2){
        return chessModel.isValidTurn(movingPiece2);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        	// reset the chess game
            case "Reset Button":
                System.out.println("Reset button pressed");
                reset();
                // close the network socket when resetting the game
                try{
                	if(listener != null) {
                		try {
                			listener.close();
                		} catch (Exception e1) {
                			System.out.println("Server socket closed");
                			e1.printStackTrace();
                		}
                	}
                	if(socket != null) {
                		try {
                			socket.close();
                		} catch (Exception e1) {
                			System.out.println("Client socket closed");
                			e1.printStackTrace();
                		}
                	}                	
                	// enable the socket listener and client connect buttons when resetting
                	((ViewFrame)chessView).bottomPanel.connectButton.setEnabled(true);
                	((ViewFrame)chessView).bottomPanel.listenButton.setEnabled(true); 
                } catch(Exception e1) {
                	e1.printStackTrace();
                }
                break;
                
             // set up the server side of the socket network, enable listening to client
            case "Listen Button":       
            	((ViewFrame)chessView).bottomPanel.connectButton.setEnabled(false);
            	((ViewFrame)chessView).bottomPanel.listenButton.setEnabled(false); 
            	((JFrame)chessView).setTitle("JavaChess - Server Side");
            	runSocketServer();
                // disable button after pressing to avoid re-connecting to client, which cause error
                
                break;
        
            // set up the client side of the socket network, enable connecting to server
            case "Connect Button":
            	((JFrame)chessView).setTitle("JavaChess - Client Side");
            	((ViewFrame)chessView).bottomPanel.connectButton.setEnabled(false);
            	((ViewFrame)chessView).bottomPanel.listenButton.setEnabled(false);
                System.out.println("Connect button pressed");
                runSocketClient();               
                break;
        }
                
                

    }
    
    /**
     * Setup the client side of the socket network. Enable listening to client and send information to client
     */
    private void runSocketClient() {
    	 try {
         	System.out.println("clinet connected to port "+ PORT);
         	socket = new Socket(SOCKET_SERVER_ADDRESS, PORT);
         	Scanner scanner = new Scanner(socket.getInputStream());
         	printWriter = new PrintWriter(socket.getOutputStream(), true);
         	             
         	Executors.newFixedThreadPool(1).execute(new Runnable() {
         		@Override
         		public void run() {
         			 receiveMove(scanner);
         		}
         	});
         	
             
         } catch (Exception ex) {
             ex.printStackTrace();
         }
	}

    /**
     * Setup the client side of the socket network. Enable connecting and sending information to server
     */
	private void runSocketServer() {
    	Executors.newFixedThreadPool(1).execute(new Runnable() {

        	@Override
            public void run() {
                try  {
                	listener = new ServerSocket(PORT);
                	System.out.println("Server listening to port " + PORT);
                	socket = listener.accept();
                	Scanner scanner = new Scanner(socket.getInputStream());
                	printWriter = new PrintWriter(socket.getOutputStream(), true);
                	receiveMove(scanner);       	

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        	
        });
    }

    
    /**
     * To receive the fromCol, fromRow, toCol, toRow information as a String from the client/server side,
     * Convert back to integers and pass to movePiece to enable the move accordingly from the other client/server  side.
     * @param scanner
     */
    private void receiveMove(Scanner scanner) {
    	while(scanner.hasNextLine()) {
        	String moveStr = scanner.nextLine(); // "0, 1, 0, 2"
            String[] moveStrArr = moveStr.split(","); //["0", "1" ,"0", "2"]
            System.out.println("move received: " + Arrays.toString(moveStrArr) );
            int fromCol = Integer.parseInt(moveStrArr[0]);
            int fromRow = Integer.parseInt(moveStrArr[1]);
            int toCol = Integer.parseInt(moveStrArr[2]);
            int toRow = Integer.parseInt(moveStrArr[3]);
            SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
//					movePiece(fromCol, fromRow, toCol, toRow);
					chessModel.movePiece(fromCol, fromRow, toCol, toRow);
					chessView.repaint();
				}
            	
            });
        }  
    }
}
