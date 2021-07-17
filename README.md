# RESTful API backend for Tic-Tac-Toe

##Introduction
This project includes a simple backend implementation for Tic-Tac-Toe done with Java and Springboot.

Read more about the game: https://en.wikipedia.org/wiki/Tic-tac-toe

The API serves endoints for,
* Session management
  * Create session (`/tictactoe/session/create`)
    > Creates a new session with a game board. Returns session with a sessionId, timestamp and game board.
    If maximum number of active sessions has been reached and no timed out sessions found, returns HTTP 409.
  * Get session (`/tictactoe/session/get/{sessionId}`)
    > If an active session with given id is found then returns the session. Otherwise, returns HTTP 409.
* Game management
  * Strike (`/tictactoe/game/strike`)
    > Records a player strike on the game board. If the strike is invalid, previous state is returned
    with an error message. If the strike is valid, game board is updated, evaluated and returned with
    updated status.

## Build  & Run
### Backend
Clone the repository
```
git clone https://github.com/pubudusitinamaluwa/tic-tac-toe-api
```
Assemble the jar
```
cd tic-tac-toe-api
./gradlew clean assemble
```
A jar file will be created in `./build/lib`. Execute the jar file
```
java -jar build/libs/tic-tac-toe-api-1.0.jar
```
### Frontend
Please follow the build & run steps in this repo [tic-tac-toe](https://github.com/pubudusitinamaluwa/tic-tac-toe)

### Docker Compose
ToDo
