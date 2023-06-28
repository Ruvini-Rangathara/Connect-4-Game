package lk.ijse.dep.service;

public class AiPlayer extends Player {
    int[][] intBoard =new int[6][5];
    public AiPlayer(Board board) {
        super(board);
    }
    @Override
    public void movePiece(int col) {

        //Selecting a column randomly.
//        do{
//            col=(int)(Math.random()*6);
//        }while (!(board.isLegalMove(col)));

        //Selecting the best column by using ai method.
        //col=board.ai();

        //Selecting the best column with minimax algorithm.
        col= findColumn();

        if(board.isLegalMove(col)){
            board.updateMove(col,Piece.GREEN);
            board.getBoardUI().update(col, false);
            Winner winner = board.findWinner();

            if(winner!=null){
                board.getBoardUI().notifyWinner(winner);
            }else{
                if(!board.existLegalMoves()){
                    board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }
        }
    }

    //findColumn method.
    public int findColumn(){
        Piece[][] pieces = BoardImpl.getPieces();
        for (int col= 0; col < 6; col++) {
            for (int row = 0; row < 5; row++) {
                if (pieces[col][row] == Piece.BLUE) {
                    intBoard[col][row] = -1;
                } else if (pieces[col][row] == Piece.GREEN) {
                    intBoard[col][row] = 1;
                } else if (pieces[row][row] == Piece.EMPTY)
                    intBoard[col][row] = 0;
            }
        }

        int maxEval = (int) Double.NEGATIVE_INFINITY;
        int findCol= 0;
        // i-- column  j---row
        for (int i = 0; i< intBoard.length; i++) {
            for (int j = 0; j <intBoard[0].length; j++) {
                if (intBoard[i][j] == 0) {
                    intBoard[i][j] = 1;
                    int  heuristicVal = minimax(intBoard, 0, false, i, j);
                    intBoard[i][j] = 0;
                    if ( heuristicVal > maxEval) {
                        maxEval =  heuristicVal;
                        findCol = i;
                    }
                }
            }
        }
        return findCol;
    }

    //minimax method.
    private int minimax(int[][] intBoard, int depth, boolean maximizingPlayer, int col, int row){
        int result= checkWin(intBoard);
        if (depth == 4 || result != 0) {
            if (result == 0)
                return 0;
            return checkWin(intBoard);
        }

        int maxEval;
        if (maximizingPlayer) {
            maxEval= (int) Double.NEGATIVE_INFINITY;
            for (int i = 0; i < intBoard.length; i++) {
                for (int j = 0; j < intBoard[0].length; j++) {

                    if (intBoard[i][j] == 0) {
                        intBoard[i][j] = 1;
                        int heuristicVal = minimax(intBoard, depth + 1, false, i, j);
                        intBoard[i][j] = 0;
                        maxEval = Math.max(heuristicVal, maxEval);
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = (int) Double.POSITIVE_INFINITY;
            for (int i = 0; i < intBoard.length; i++) {
                for (int j = 0; j <intBoard[0].length; j++) {
                    if (intBoard[i][j] == 0) {
                        intBoard[i][j] = -1;
                        int heuristicVal = minimax(intBoard, depth + 1, true, i, j);
                        intBoard[i][j] = 0;
                        minEval = Math.min(heuristicVal, minEval);
                    }
                }
            }
            return minEval;
        }
    }


    //BLUE =-1, GREEN =1
    private int checkWin(int[][] pieces) {

        for (int row=0; row<5; row++){
            if(pieces[0][row]==1 && pieces[1][row]==1 && pieces[2][row]==1 && pieces[3][row]==1){
                return 1;
            }else if(pieces[0][row]==-1 && pieces[1][row]==-1 && pieces[2][row]==-1 && pieces[3][row]==-1){
                return -1;
            }else if(pieces[1][row]==1 && pieces[2][row]==1 && pieces[3][row]==1 &&pieces[4][row]==1){
                return 1;
            }else if(pieces[1][row]==-1 && pieces[2][row]==-1 && pieces[3][row]==-1 &&pieces[4][row]==-1){
                return -1;
            }else if(pieces[2][row]==1 && pieces[3][row]==1 && pieces[4][row]==1 &&pieces[5][row]==1){
                return 1;
            }else if (pieces[2][row]==-1 && pieces[3][row]==-1 && pieces[4][row]==-1 &&pieces[5][row]==-1){
                return -1;
            }
        }

        for (int col=0; col<6; col++){
            if(pieces[col][0]==1 && pieces[col][1]==1 && pieces[col][2]==1 &&pieces[col][3]==1){
                return 1;
            }else if(pieces[col][0]==-1 && pieces[col][1]==-1 && pieces[col][2]==-1 &&pieces[col][3]==-1){
                return -1;
            }else if(pieces[col][1]==1 && pieces[col][2]==1 && pieces[col][3]==1 &&pieces[col][4]==1){
                return 1;
            }else if(pieces[col][1]==-1 && pieces[col][2]==-1 && pieces[col][3]==-1 &&pieces[col][4]==-1){
                return -1;
            }
        }
        return 0;
    }


}