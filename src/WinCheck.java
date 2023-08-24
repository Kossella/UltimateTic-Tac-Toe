public class WinCheck implements GameObject {
    private final Game game;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x3;
    private int y3;

    public WinCheck(Game game) {
        this.game = game;
    }

/*
    Koordynaty na planszach (lokalnej i globalnej)

        0,0   1,0   2,0
        0,1   1,1   2,1
        0,2   1,2   2,2
*/

    public boolean isGlobalWinner(Player player) {
        return isGlobalWinningLine(player, 0, 0, 0, 1, 0, 2)
                || isGlobalWinningLine(player, 1, 0, 1, 1, 1, 2)
                || isGlobalWinningLine(player, 2, 0, 2, 1, 2, 2)
                || isGlobalWinningLine(player, 0, 0, 1, 0, 2, 0)
                || isGlobalWinningLine(player, 0, 1, 1, 1, 2, 1)
                || isGlobalWinningLine(player, 0, 2, 1, 2, 2, 2)
                || isGlobalWinningLine(player, 0, 0, 1, 1, 2, 2)
                || isGlobalWinningLine(player, 0, 2, 1, 1, 2, 0);
    }

    public boolean isLocalWinner(Player player, LocalBoard localBoard) {
        return isLocalWinningLine(player, localBoard, 0, 0, 0, 1, 0, 2)
                || isLocalWinningLine(player, localBoard, 1, 0, 1, 1, 1, 2)
                || isLocalWinningLine(player, localBoard, 2, 0, 2, 1, 2, 2)
                || isLocalWinningLine(player, localBoard, 0, 0, 1, 0, 2, 0)
                || isLocalWinningLine(player, localBoard, 0, 1, 1, 1, 2, 1)
                || isLocalWinningLine(player, localBoard, 0, 2, 1, 2, 2, 2)
                || isLocalWinningLine(player, localBoard, 0, 0, 1, 1, 2, 2)
                || isLocalWinningLine(player, localBoard, 0, 2, 1, 1, 2, 0);
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getX3() {
        return x3;
    }

    public int getY3() {
        return y3;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void update() {

    }

    private boolean isGlobalWinningLine(Player player, int x1, int y1, int x2, int y2, int x3, int y3) {
        LocalBoard board1 = game.getGlobalBoard().getLocalBoardAt(x1, y1);
        LocalBoard board2 = game.getGlobalBoard().getLocalBoardAt(x2, y2);
        LocalBoard board3 = game.getGlobalBoard().getLocalBoardAt(x3, y3);
        return isLocalWinner(player, board1) && isLocalWinner(player, board2) && isLocalWinner(player, board3);
    }

    private boolean isLocalWinningLine(Player player, LocalBoard localBoard, int x1, int y1, int x2, int y2, int x3, int y3) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        LocalCell cell1 = localBoard.getCellAt(x1, y1);
        LocalCell cell2 = localBoard.getCellAt(x2, y2);
        LocalCell cell3 = localBoard.getCellAt(x3, y3);
        return cell1.getOwner() == player && cell2.getOwner() == player && cell3.getOwner() == player;
    }
}
