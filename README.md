## Step 1: Set Up Spring Boot Project
* Goal: Create a foundational Spring Boot project to support the REST API. 
* Tasks:
1. Create a new Spring Boot project using Spring Initializr, including dependencies:
* Spring Web (to build the REST API).
* Spring Boot DevTools (for rapid development).
2. Set up the /public directory and copy the provided front-end files into it. 
3. Run the project to ensure the front-end serves correctly at http://localhost:8080.

## Step 2: Prepare Backend Data Structures
* **Goal:** Integrate the game logic classes from Assignment 3 into the new Spring Boot project. 
* **Tasks:**
1. Copy all relevant model classes (MazeGame, Maze, Cat, CellLocation, CellState, PathFinder, etc.) into the Spring Boot project under a model package.
2. Verify the classes compile without errors.
3. Make minimal modifications, if needed, to adapt the game model for the REST API (e.g., adding helper methods).

## Step 3: Define Data Transfer Objects (DTOs)
* **Goal:** Create DTOs to transfer data between the backend and front-end.
* **Tasks:**
1. **ApiGameDTO:** Represents the game's state (e.g., player position, number of cheese collected, etc.).
2. **ApiBoardDTO:** Represents the board state (e.g., visibility, walls, positions of cats, player, and cheese).
3. **ApiLocationDTO:** Encodes the location of entities like the player, cheese, and cats.
4. Implement static factory methods in the DTOs to convert data from the game model into DTOs.

## Step 4: Implement REST Endpoints
* Goal: Develop endpoints based on the assignment requirements. 
* Tasks:
### Basic Endpoints
1. GET /api/about: Returns your name as a string. 
2. GET /api/games: Returns a list of ApiGameDTO objects for all games. 
3. POST /api/games: Creates a new game and returns the created ApiGameDTO.
### Game-Specific Endpoints
4. GET /api/games/{id}: Returns the ApiGameDTO for the specified game ID.
5. GET /api/games/{id}/board: Returns the ApiBoardDTO for the board of the specified game ID.
### Game Actions
6. POST /api/games/{id}/moves: Handles player or cat moves (e.g., "MOVE_UP", "MOVE_CATS"). 
7. POST /api/games/{id}/cheatstate: Implements cheats like "1_CHEESE" or "SHOW_ALL".
### Error Handling
* Return appropriate HTTP status codes:
  * `404 Not Found` for invalid game IDs. 
  * `400 Bad Request` for invalid moves or cheats.
## Step 5: Integrate Game Logic
* **Goal:** Connect the REST endpoints to the game logic.
* **Tasks**:
1. Use a `GameController` class to handle the REST requests.
2. Maintain a list of `MazeGame` instances in memory to track active games.
3. Implement methods to translate API inputs into actions on the game model. 
4. Convert game state into DTOs to return as responses.

## Step 6: Test Functionality
* **Goal:** Ensure the API works correctly with the provided front-end. 
* **Tasks:**
1. Test the endpoints using Postman or a similar tool. 
2. Open the front-end in a browser and interact with the game. 
3. Verify that all interactions (e.g., creating games, moving, cheating) behave as expected. 
4. Debug and fix any issues with API responses or integration.

## Step 7: Finalize and Submit
* Goal: Prepare the project for submission.
* Tasks:
1. Clean up the code:
* Remove any debugging logs or unused code.
* Add JavaDoc comments to all public methods.
2. Write a brief README file explaining how to run the project and test the API.
3. Ensure all files (front-end and back-end) are properly organized in the project structure.