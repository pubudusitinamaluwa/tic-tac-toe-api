# RESTful API backend for Tic-Tac-Toe

## Introduction

This project includes a simple backend implementation for Tic-Tac-Toe done with Java and Springboot.

Read more about the game: https://en.wikipedia.org/wiki/Tic-tac-toe

The API serves endoints for,
* Session management
  * Create session (`/tictactoe/session/create`)
    > Creates a new session with a game board. Returns session with a sessionId, timestamp and game board.
    If maximum number of active sessions has been reached and no timed out sessions found, returns HTTP 409.
    
    Sample Response:
    
    `HTTP 200`
    ```json
    {
      "sessionId": "26db973a-56c4-450d-aa91-c7f5070eacbc",
      "lastActiveTs": 1626598716837,
      "gameBoard": {
          "board": [
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null
          ],
          "gameStatus": "ACTIVE",
          "nextStriker": "X",
          "winner": "NONE"
      }
    }
    ```
    `HTTP 409`
    ```json
    {
      "status": "CONFLICT",
      "message": "Max active sessions (50) reached. Unable to create new sessions. Increase game.sessions.max to increase max sessions."
    }
    ```
  * Get session (`/tictactoe/session/get/{sessionId}`)
    > If an active session with given id is found then returns the session.
    
    Sample Response:
    
    `HTTP 200`
    ```json
    {
      "sessionId": "26db973a-56c4-450d-aa91-c7f5070eacbc",
      "lastActiveTs": 1626598716837,
      "gameBoard": {
          "board": [
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null
          ],
          "gameStatus": "ACTIVE",
          "nextStriker": "X",
          "winner": "NONE"
      }
    }
    ```
    `HTTP 404`
    ```json
    {
      "status": "NOT_FOUND",
      "message": "Session id 26db973a-56c4-450d-aa91-c7f5070eacbd not found."
    }
    ```
* Game management
  * Strike (`/tictactoe/game/strike`)
    > Records a player strike on the game board. If the strike is invalid, previous state is returned
    with an error message. If the strike is valid, game board is updated, evaluated and returned with
    updated status.
    
    Request Parameters: 
      * String sessionId - Unique id of the game session
      * Player striker - Player (X or O)
      * int index - Marking index of the board.
    
        | 0 | 1 | 2 |
        |:---:|:---:|:---:|
        | 3 | 4 | 5 |
        | 6 | 7 | 8 |
  
    Sample Response:
    
    `HTTP 200`
    ```json
    {
      "success": true,
      "gameStatus": "ACTIVE",
      "nextStriker": "O",
      "board": [
          "X",
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null
      ],
      "winner": "NONE",
      "message": "Next striker: O"
    }
    ```
    `HTTP 409`
    ```json
    {
      "status": "CONFLICT",
      "message": "Session does not exists. id: 26db973a-56c4-450d-aa91-c7f5070eacbc"
    }
    ```

## Build  & Run

### Backend
#### With Jar:
Clone the repository
```shell
git clone https://github.com/pubudusitinamaluwa/tic-tac-toe-api
```
Assemble the jar
```shell
cd tic-tac-toe-api
./gradlew clean assemble
```
A jar file will be created in `./build/libs`. Execute the jar file
```shell
java -jar build/libs/tic-tac-toe-api-1.0.jar
```
#### With Docker:
Build the image
```shell
docker build -t tic-tac-toe:latest .
```
Run container and bind to port 8080 on host
```shell
docker container run -d --rm --name tic-tac-toe-api -p 8080:8080 tic-tac-toe-api:latest
```
### Frontend
Please follow the build & run steps in this repo [tic-tac-toe](https://github.com/pubudusitinamaluwa/tic-tac-toe)
