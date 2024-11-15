package ca.cmpt213.mazeapi.dto;

import ca.cmpt213.mazeapi.model.CellLocation;
import ca.cmpt213.mazeapi.model.MazeGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing the game board state.
 * Contains information about walls, visibility, and positions of all game entities
 * (mouse, cats, cheese).
 */
public class ApiBoardDTO {
    public boolean[][] hasWalls;
    public boolean[][] isVisible;
    public ApiLocationDTO mouseLocation;
    public ApiLocationDTO cheeseLocation;
    public List<ApiLocationDTO> catLocations = new ArrayList<>();

    public ApiBoardDTO() {
    }

    public static ApiBoardDTO createFromGame(MazeGame game) {
        ApiBoardDTO board = new ApiBoardDTO();

        // Init arrays based on maze size
        int width = MazeGame.getMazeWidth();
        int height = MazeGame.getMazeHeight();
        board.hasWalls = new boolean[height][width];
        board.isVisible = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                CellLocation cell = new CellLocation(x, y);
                board.hasWalls[y][x] = game.getCellState(cell).isWall();
                board.isVisible[y][x] = game.getCellState(cell).isVisible();
            }
        }

        // Set locations
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                CellLocation loc = new CellLocation(x,y);
                if (game.isMouseAtLocation(loc)) {
                    board.mouseLocation = ApiLocationDTO.createFromCellLocation(loc);
                }
                if (game.isCheeseAtLocation(loc)) {
                    board.cheeseLocation = ApiLocationDTO.createFromCellLocation(loc);
                }
                if (game.isCatAtLocation(loc)) {
                    board.catLocations.add(ApiLocationDTO.createFromCellLocation(loc));
                }
            }
        }

        return board;
    }
}
