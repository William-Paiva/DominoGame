package main;


import node.DoubleList;
import pieces.Pieces;
import players.Players;
import java.util.Random;
import java.util.Scanner;

public class StartApp {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    DoubleList allPieces = new DoubleList();
    DoubleList cpuPieces = new DoubleList();
    DoubleList playerPieces = new DoubleList();
    DoubleList board = new DoubleList();
    int cpuPiecesEnds = 14;
    int playerPiecesEnds = 14;
    int countToWin = 0;

    public void letsPlay(){               //playing

        Players player = createPlayer();

        createPieces();

        randomPieces();

        int playFirst = whoPlayFirst();

        if(playFirst == 1){
            while(countToWin != 2 || cpuPiecesEnds == 0 || playerPiecesEnds == 0){
                cpuPlaying();
                System.out.println(board.toStringBoard());
                humanPlaying(player);
            }
            if(cpuPiecesEnds < playerPiecesEnds){
                System.out.println("\nAND THE WINNER IS...\n");
                System.out.println("☺☺☺ -> CPU <- ☺☺☺");
            }else{
                System.out.println("\nAND THE WINNER IS...\n");
                System.out.println("☺☺☺ -> "+player+" <- ☺☺☺");
            }

        }else if(playFirst == 2){
            while(countToWin != 2 || cpuPiecesEnds == 0 || playerPiecesEnds == 0){
                humanPlaying(player);
                cpuPlaying();
                System.out.println(board.toStringBoard());
            }
            if(cpuPiecesEnds < playerPiecesEnds){
                System.out.println("\nAND THE WINNER IS...\n");
                System.out.println("☺☺☺ -> CPU <- ☺☺☺");
            }else{
                System.out.println("\nAND THE WINNER IS...\n");
                System.out.println("☺☺☺ -> "+player+" <- ☺☺☺");
            }
        }
    }
    
    public Players createPlayer(){                      //create a player;
        System.out.println("Now, enter your name: ");
        Players player = new Players(scanner.nextLine());
        return player;
    }

    public void createPieces(){                         //create pieces;
        int k=0;
        for (int i=0; i<7; i++){
            for (int j=k; j<7; j++){
                Pieces piece = new Pieces(i, j);
                allPieces.setStart(piece);
            }
            k++;
        }
    }

    public int whoPlayFirst(){                              //decide who plays first
        int playFirst = random.nextInt(1,3);
        return  playFirst;
    }

    public void randomPieces(){                             //random pieces distribution
        int end = 28;
        for (int i=0; i<14; i++){
            int randomIndex = random.nextInt(0, end);
            cpuPieces.setStart(allPieces.removePieces(randomIndex));
            end--;
        }
        playerPieces = allPieces;
    }

    public void cpuPlaying(){                                   //cpu playing
        DoubleList forTheLeftSideList = new DoubleList();
        DoubleList forTheRightSideList = new DoubleList();
        int randomIndex;
        if(board.getSize() == 0){
            randomIndex = random.nextInt(0, cpuPiecesEnds);
            board.setStart(cpuPieces.removePieces(randomIndex));
            cpuPiecesEnds--;
        }else{
            for(int i=0; i<cpuPiecesEnds; i++) {                              //it's a solution that makes cpu use
                Pieces returnedPiece = cpuPieces.returnPiece(i);              //the pieces with more equal integers
                if (checkLeftSideOfPieceWithRightSideOfBoard(returnedPiece) ||
                        checkRightSideOfPieceWithRightSideOfBoard(returnedPiece)) {
                    forTheRightSideList.setEnd(returnedPiece);
                } else if (checkRightSideOfPieceWithLeftSideOfBoard(returnedPiece) ||
                        checkLeftSideOfPieceWithLeftSideOfTheBoard(returnedPiece)) {
                    forTheLeftSideList.setStart(returnedPiece);
                }
            }
            int forTheRightSideSize = forTheRightSideList.getSize();
            int forTheLeftSideSize = forTheLeftSideList.getSize();
            if(forTheRightSideSize > forTheLeftSideSize){
                randomIndex = random.nextInt(0, forTheRightSideSize);
                Pieces chosenPiece = forTheRightSideList.returnPiece(randomIndex);
                if(checkLeftSideOfPieceWithRightSideOfBoard(chosenPiece)){
                    board.setEnd(chosenPiece);
                    cpuPieces.removePieces(randomIndex);
                    cpuPiecesEnds--;
                    countToWin = 0;
                }else if(checkRightSideOfPieceWithRightSideOfBoard(chosenPiece)){
                    Pieces invertPiece = new Pieces(chosenPiece.getRightSide(), chosenPiece.getLeftSide());
                    board.setEnd(invertPiece);
                    cpuPieces.removePieces(randomIndex);
                    cpuPiecesEnds--;
                    countToWin = 0;
                }
            }else if(forTheLeftSideSize > forTheRightSideSize){
                randomIndex = random.nextInt(0, forTheLeftSideSize);
                Pieces chosenPiece = forTheLeftSideList.returnPiece(randomIndex);
                if(checkRightSideOfPieceWithLeftSideOfBoard(chosenPiece)){
                    board.setStart(chosenPiece);
                    cpuPieces.removePieces(randomIndex);
                    cpuPiecesEnds--;
                    countToWin = 0;
                }else if(checkLeftSideOfPieceWithLeftSideOfTheBoard(chosenPiece)){
                    Pieces invertPiece = new Pieces(chosenPiece.getRightSide(), chosenPiece.getLeftSide());
                    board.setStart(invertPiece);
                    cpuPieces.removePieces(randomIndex);
                    cpuPiecesEnds--;
                    countToWin = 0;
                }
            }else{
                System.out.println("CPU hasn't compatible pieces! Passing CPU's turn...");}
                countToWin++;
        }
    }



    public void humanPlaying(Players player){                       //player playing
        int count = 0;
        while(count == 0) {
            int option = playerOptionsMenu(player);
            if (option == 1) {
                System.out.println("Choose your piece typing 1 for the first one, 2 for the second, and so on: ");
                System.out.println(playerPieces.toStringPlayer());
                System.out.println(board.toStringBoard());
                int indexOfChosenPiece = scanner.nextInt() - 1;
                Pieces chosenPiece = playerPieces.returnPiece(indexOfChosenPiece);
                if (board.getSize() == 0) {
                    board.setStart(chosenPiece);
                    playerPieces.removePieces(indexOfChosenPiece);
                    playerPiecesEnds--;
                    count++;
                } else {
                    int subOption = sideOptionsMenu();
                    if (subOption == 1) {
                        if (checkRightSideOfPieceWithLeftSideOfBoard(chosenPiece)) {
                            board.setStart(chosenPiece);
                            playerPieces.removePieces(indexOfChosenPiece);
                            playerPiecesEnds--;
                            countToWin = 0;
                            count++;
                        } else if (checkLeftSideOfPieceWithLeftSideOfTheBoard(chosenPiece)) {
                            Pieces invertPiece = new Pieces(chosenPiece.getRightSide(), chosenPiece.getLeftSide());
                            board.setStart(invertPiece);
                            playerPieces.removePieces(indexOfChosenPiece);
                            playerPiecesEnds--;
                            countToWin = 0;
                            count++;
                        }else{
                            System.out.println("Incompatible Piece! Try another option...\n");
                        }
                    }else if(subOption == 2){
                        if(checkLeftSideOfPieceWithRightSideOfBoard(chosenPiece)){
                            board.setEnd(chosenPiece);
                            playerPieces.removePieces(indexOfChosenPiece);
                            playerPiecesEnds--;
                            countToWin = 0;
                            count++;
                        }else if(checkRightSideOfPieceWithRightSideOfBoard(chosenPiece)){
                            Pieces invertPiece = new Pieces(chosenPiece.getRightSide(), chosenPiece.getLeftSide());
                            board.setEnd(invertPiece);
                            playerPieces.removePieces(indexOfChosenPiece);
                            playerPiecesEnds--;
                            countToWin = 0;
                            count++;
                        }else{
                            System.out.println("Incompatible Piece! Try another option...\n");
                        }
                    }
                }
            } else if (option == 2) {
                System.out.println("Ok! Passing your turn...\n");
                count++;
                countToWin++;
            }
        }
    }

    public boolean checkLeftSideOfPieceWithLeftSideOfTheBoard(Pieces chosenPiece){
        int leftSideOfPiece = chosenPiece.getLeftSide();
        int leftSideOfBoard = board.getStart().getLeftSide();
        if(leftSideOfPiece == leftSideOfBoard ){
            return true;
        }
        return false;
    }

    public boolean checkLeftSideOfPieceWithRightSideOfBoard(Pieces chosenPiece){
        int leftSideOfPiece = chosenPiece.getLeftSide();
        int rightSideOfBoard = board.getEnd().getRightSide();
        if(leftSideOfPiece == rightSideOfBoard ){
            return true;
        }
        return false;
    }

    public boolean checkRightSideOfPieceWithRightSideOfBoard(Pieces chosenPiece){
        int rightSideOfPiece = chosenPiece.getRightSide();
        int rightSideOfBoard = board.getEnd().getRightSide();
        if(rightSideOfPiece == rightSideOfBoard){
            return true;
        }
        return false;
    }

    public boolean checkRightSideOfPieceWithLeftSideOfBoard(Pieces chosenPiece){
        int rightSideOfPiece = chosenPiece.getRightSide();
        int leftSideOfBoard = board.getStart().getLeftSide();
        if(rightSideOfPiece == leftSideOfBoard){
            return true;
        }
        return false;
    }

    public int playerOptionsMenu(Players player){
        System.out.println("\nYour time "+ player+ ". Make your move!");
        System.out.println("So, choose one option below:\n ");
        System.out.println("1. Choose a piece to play\n2. Pass turn\n ");
        int option = scanner.nextInt(); scanner.nextLine();
        return option;
    }

    public  int sideOptionsMenu(){
        System.out.println("Ok! Now choose the side you'll put the piece: ");
        System.out.println("1. Left\n2. Right\n");
        int option = scanner.nextInt(); scanner.nextLine();
        return option;
    }
}
