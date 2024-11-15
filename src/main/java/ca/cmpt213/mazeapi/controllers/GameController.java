package ca.cmpt213.mazeapi.controllers;

import ca.cmpt213.mazeapi.dto.ApiBoardDTO;
import ca.cmpt213.mazeapi.dto.ApiGameDTO;
import ca.cmpt213.mazeapi.exceptions.GameNotFoundException;
import ca.cmpt213.mazeapi.model.CellLocation;
import ca.cmpt213.mazeapi.model.MazeGame;
import ca.cmpt213.mazeapi.model.MoveDirection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for the Maze Game.
 * Handles game creation, state management, moves, and cheats.
 * Supports multiple concurrent games and maintains game state between requests.
 */
@RestController
@RequestMapping("/api")
public class GameController {
    private final List<MazeGame> games = new ArrayList<>();

    @GetMapping("/about")
    public String getAbout() {
        return "Tran Quang Ngoc Cao";
    }

    @PostMapping("/games")
    public ResponseEntity<ApiGameDTO> createNewGame() {
        MazeGame newGame =  new MazeGame();
        games.add(newGame);
        int gameId = games.size() - 1; // Use index as game ID

        ApiGameDTO gameDTO = ApiGameDTO.createFromGame(newGame, gameId);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }

    @GetMapping("/games")
    public List<ApiGameDTO> getAllGames() {
        List<ApiGameDTO> gameDTOs = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            gameDTOs.add(ApiGameDTO.createFromGame(games.get(i), i));
        }
        return gameDTOs;
    }

    @GetMapping("/games/{id}")
    public ApiGameDTO getGame(@PathVariable("id") int gameId) {
        MazeGame game = getGameById(gameId);
        return ApiGameDTO.createFromGame(game, gameId);
    }

    @GetMapping("/games/{id}/board")
    public ApiBoardDTO getGameBoard(@PathVariable("id") int gameId) {
        MazeGame game = getGameById(gameId);
        return ApiBoardDTO.createFromGame(game);
    }

    @PostMapping("/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeMove(@PathVariable("id") int gameId, @RequestBody String moveCommand) {
        MazeGame game = getGameById(gameId);

        if (game.hasUserWon() || game.hasUserLost()) {
            throw new IllegalStateException("Game has ended");
        }

        if (moveCommand.equals("MOVE_CATS")) {
            game.doCatMoves();
            return;
        }

        MoveDirection direction = convertMoveCommand(moveCommand);
        if (!game.isValidPlayerMove(direction)) {
            throw new IllegalArgumentException("Invalid move: would hit wall");
        }

        game.recordPlayerMove(direction);
    }

    @PostMapping("/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void applyCheat(@PathVariable("id") int gameId, @RequestBody String cheatCommand) {
        MazeGame game = getGameById(gameId);

        switch (cheatCommand) {
            case "1_CHEESE":
                game.setNumberCheeseToCollect(1);
                break;
            case "SHOW_ALL":
                makeAllCellsVisible(game);
                break;
            default:
                throw new IllegalArgumentException("Invalid cheat command: " + cheatCommand);
        }
    }

    // ------------ Helpers -------------
    private MazeGame getGameById(int gameId) {
        if (gameId < 0 || gameId >= games.size()) {
            throw new GameNotFoundException("Game not found with id: " + gameId);
        }
        return games.get(gameId);
    }

    private void makeAllCellsVisible(MazeGame game) {
        int width = MazeGame.getMazeWidth();
        int height = MazeGame.getMazeHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                CellLocation cell = new CellLocation(x, y);
                game.recordCellVisible(cell);
            }
        }
    }

    private MoveDirection convertMoveCommand(String moveCommand) {
        return switch (moveCommand) {
            case "MOVE_UP" -> MoveDirection.MOVE_UP;
            case "MOVE_DOWN" -> MoveDirection.MOVE_DOWN;
            case "MOVE_LEFT" -> MoveDirection.MOVE_LEFT;
            case "MOVE_RIGHT" -> MoveDirection.MOVE_RIGHT;
            default -> throw new IllegalArgumentException("Invalid move command: " + moveCommand);
        };
    }
}
