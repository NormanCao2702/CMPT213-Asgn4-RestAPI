package ca.cmpt213.mazeapi.dto;

import ca.cmpt213.mazeapi.model.CellLocation;

/**
 * Data Transfer Object representing a location in the maze.
 * Used to transfer coordinate information between the server and client.
 * Contains x and y coordinates and conversion method from model's CellLocation.
 */
public class ApiLocationDTO {
    public int x;
    public int y;

    public ApiLocationDTO() {
    }

    public ApiLocationDTO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Factory method to convert from model's CellLocation
    public static ApiLocationDTO createFromCellLocation(CellLocation cell) {
        return new ApiLocationDTO(cell.getX(), cell.getY());
    }
}
