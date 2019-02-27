# DSScrabbleProject
The Scrabble is game about spelling word. In each turn, the player could place a letter on the board (20*20) and spell the most valuable words to get a certain score. 

In our system, the player should login with the username, server IP address and server port number. The username should be unique. If username already exists, player is informed and required to change the name. If the server is on, the user offering the right IP address and port number can login, otherwise the user fails to login.

Logged in player can create a room to invite other players to play the game. We only allow one room in the system and the room creator to invite other players. The invited player could accept the invitation or not. Only the room creator could start a game.

During the game, all the players take turns to play. In each turn, the player could pass the game which means he/she doesn’t place a letter, if all the users pass the game, the game is over. When the current player places a letter, he/she could choose the certain word to start a voting. If the rest of the players agree the word is valid, the current player could get the score the same as length of word. Also, he could place a letter and doesn’t start a voting by press the button “next”. If a player who is playing the game close the client, the game will be over. The client GUI will show the score of the game. Then all players could choose to create a room to play another game. If a user login during the game is player, he/she automatically views the game. The client will show the current game to him/her. The game viewer can’t do anything other than viewing the game.

Our system applies client-server architecture and consists of one Scrabble Server and several Scrabble Clients. One server is enough for this simple game. 

We use java RMI to achieve this architecture. RMI is very easy and convenient for distributed system. More important, team members all want to learn something new through this project.

Below is the procedure of our distributed system:

1.	The server is started and publishes the remote object's stub in the registry under the name "Scrabble" (Registers the servant with the binder). 

2.	New clients try to connect to the RMI registry that is running on server and login, passing the Scrabble Client instances to the server.

3.	The server stores the Scrabble Client instances for all clients. 

4.	One client does some operation and call the remote method on server.

5.	This remote method server method callback a remote method on all clients whose instances have already been stored by server. 

6.	This method on client store the parameter locally and refreshes the GUI. 

7.	Repeat steps 4 to 6.

Message transmitting between clients and server is rather simple. It usually includes just a few base type parameters and sometimes arraylist. 

