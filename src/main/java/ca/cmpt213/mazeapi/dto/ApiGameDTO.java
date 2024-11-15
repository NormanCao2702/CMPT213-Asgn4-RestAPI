package ca.cmpt213.mazeapi.dto;

import ca.cmpt213.mazeapi.model.MazeGame;

/**
 * Data Transfer Object representing the game state.
 * Contains game progress information including win/loss status,
 * cheese collection progress, and game identification.
 */
public class ApiGameDTO {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    public ApiGameDTO() {
    }

    public static ApiGameDTO createFromGame(MazeGame game, int gameNumber) {
        ApiGameDTO dto = new ApiGameDTO();

        dto.gameNumber = gameNumber;
        dto.isGameLost = game.hasUserLost();
        dto.isGameWon = game.hasUserWon();
        dto.numCheeseFound = game.getNumberCheeseCollected();
        dto.numCheeseGoal = game.getNumberCheeseToCollect();

        return dto;
    }
}
