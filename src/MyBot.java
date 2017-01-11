import java.util.ArrayList;
import java.util.List;

public class MyBot {
    final static InitPackage iPackage = Networking.getInit();
    final static GameMap gameMap = iPackage.map;
    final static int myID = iPackage.myID;

    public static void main(String[] args) throws java.io.IOException {
        Networking.sendInit("Keres");

        while(true) {
            List<Move> moves = new ArrayList<Move>();
            Networking.updateFrame(gameMap);

            for (int y = 0; y < gameMap.height; y++) {
                for (int x = 0; x < gameMap.width; x++) {
                    final Location location = gameMap.getLocation(x, y);
                    final Site site = location.getSite();
                    final Site north = north(location);
                    final Site south = south(location);
                    final Site west = west(location);
                    final Site east = east(location);
                    Move mN = new Move(location, Direction.NORTH);
                    Move mS = new Move(location, Direction.SOUTH);
                    Move mW = new Move(location, Direction.WEST);
                    Move mE = new Move(location, Direction.EAST);
                    Move mSS = new Move(location, Direction.STILL);
                    if(site.owner == myID) {
                        if(north.owner != myID) {
                            if(north.strength < site.strength) {
                                moves.add(mN);
                            }
                            else {
                                moves.add(mSS);
                            }
                        }
                        else if(south.owner != myID) {
                            if(south.strength < site.strength) {
                                moves.add(mS);
                            }
                            else {
                                moves.add(mSS);
                            }
                        }
                        else if(west.owner != myID) {
                            if(west.strength < site.strength) {
                                moves.add(mW);
                            }
                            else {
                                moves.add(mSS);
                            }

                        }
                        else if(east.owner != myID) {
                            if(east.strength < site.strength) {
                                moves.add(mE);
                            }
                            else {
                                moves.add(mSS);
                            }
                        }
                        else {
                            moves.add(mN);
                        }
                    }
                }
            }
            Networking.sendFrame(moves);
        }
    }

    public static Site north(Location loc) {
        int x = loc.getX();
        int y = loc.getY() - 1;
        if(y < 0) {
            y = gameMap.height - 1;
        }
        return gameMap.getLocation(x,y).getSite();
    }
    public static Site south(Location loc) {
        int x = loc.getX();
        int y = loc.getY() + 1;
        if(y >= gameMap.height) {
            y = 0;
        }
        return gameMap.getLocation(x,y).getSite();
    }
    public static Site west(Location loc) {
        int x = loc.getX() - 1;
        int y = loc.getY();
        if(x < 0) {
            x = gameMap.width - 1;
        }
        return gameMap.getLocation(x,y).getSite();
    }
    public static Site east(Location loc) {
        int x = loc.getX() + 1;
        int y = loc.getY();
        if(x >= gameMap.width) {
            x = 0;
        }
        return gameMap.getLocation(x,y).getSite();
    }
}
