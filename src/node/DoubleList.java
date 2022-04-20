package node;

import pieces.Pieces;

public class DoubleList {

    Node start;
    Node end;
    int size;

    public void setStart(Pieces piece){
        Node node = new Node();
        node.piece = piece;
        node.previous = null;
        node.next = start;

        if(start != null){
            start.previous = node;
        }
        start = node;

        if(size == 0){
            end = start;
        }
        size++;
    }

    public Pieces removeStart(){
        if(start == null){
            return null;
        }
        Pieces removedPiece  = start.piece;
        start = start.next;
        if(start != null){
            start.previous = null;
        }else{
            end = null;
        }
        size--;
        return removedPiece;
    }

    public void setEnd(Pieces piece){
        Node node = new Node();

        node.piece = piece;
        node.next = null;
        node.previous = end;
        if(end != null){
            end.next = node;
        }
        end = node;
        if(size == 0){
            start = end;
        }
        size++;
    }

    public Pieces removeEnd(){
        if(end == null){
            return null;
        }
        Pieces removedPiece = end.piece;
        end = end.previous;
        if(end != null){
            end.next = null;
        }else{
            start = null;
        }
        size--;
        return removedPiece;
    }

    public Pieces removePieces(int index){
        if((index < 0) || (index >= size) || (start == null)){
            return null;
        }else if( index == 0){
            return removeStart();
        }else if(index == size -1){
            return removeEnd();
        }
        Node thisPosition = start;
        for (int i=0; i< index; i++){
            thisPosition = thisPosition.next;
        }
        if(thisPosition.previous != null){
            thisPosition.previous.next = thisPosition.next;
        }
        if(thisPosition.next !=null){
            thisPosition.next.previous = thisPosition.previous;
        }
        size--;
        return thisPosition.piece;
    }

    public Pieces returnPiece(int index){
        Node thisPosition = start;
        for (int i=0; i<index; i++){
            thisPosition = thisPosition.next;
        }
        return thisPosition.piece;
    }

    public Pieces getStart() {
        return start.piece;
    }

    public Pieces getEnd() {
        return end.piece;
    }

    public int getSize(){
        return size;
    }

    public String toStringBoard(){
        String piece= "The Board = (" + size + ") ";
        Node thisPosition = start;
        while(thisPosition != null){
            piece += thisPosition.piece;
            thisPosition = thisPosition.next;
        }
        return piece;
    }

    public String toStringPlayer(){
        String piece= "Player Pieces = (" + size + ") ";
        Node thisPosition = start;
        while(thisPosition != null){
            piece += thisPosition.piece;
            thisPosition = thisPosition.next;
        }
        return piece;
    }



}
