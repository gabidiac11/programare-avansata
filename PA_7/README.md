
# Lab 7
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

### Compusory

#### - Create classes in order to model the problem. You may assume that all possible tokens are initially available, having random values.
#### - Each player will have a name and they must perform in a concurrent manner, extracting repeatedly tokens from the board.
#### - Simulate the game using a thread for each player.
#### - Pay attention to the synchronization of the threads when extracting tokens from the board.

   An exemple is used in `pa.lab7.compulsory.Main`. The code has a package `pa.lab7.game`, where there are the following classes: 
      - `Board`, `Player` and `Tokens`. Board has a list of tokens of this form: t1=(i1,i2), t2=(i2,i3),...,tk=(ik,i1), where the number of such groups is given in the constructor. A Token instance is composed of start and end properties. 
      Player has a its list of tokens that is empty at start and a random name. Player and Board comunicates using the synchronized method Board.giveTokens(List<Token> playerTokens) that extracts tokens from board and returns it to the player in the Player.run() method.
      - `Game`, has a method `playGameGenericSetup`, that creates board and player, and starts the game.
  
  A random thread sleep occurs for each player after it has received tokens.
  
   The results are printed as the players get token, and when the game is finshed:
   
   ````
   Apr 10, 2021 8:57:26 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(6 - 7)
-------------------

Apr 10, 2021 8:57:26 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(29 - 30)
-------------------

Apr 10, 2021 8:57:28 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(5 - 6),(7 - 8)
-------------------

Apr 10, 2021 8:57:30 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(4 - 5),(8 - 9)
-------------------

Apr 10, 2021 8:57:30 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(28 - 29)
-------------------

Apr 10, 2021 8:57:31 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(3 - 4),(9 - 10)
-------------------

Apr 10, 2021 8:57:32 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(2 - 3),(10 - 11)
-------------------

Apr 10, 2021 8:57:34 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(1 - 2),(11 - 12)
-------------------

Apr 10, 2021 8:57:34 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(27 - 28)
-------------------

Apr 10, 2021 8:57:37 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(0 - 1),(12 - 13)
-------------------

Apr 10, 2021 8:57:39 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(26 - 27)
-------------------

Apr 10, 2021 8:57:42 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(13 - 14)
-------------------

Apr 10, 2021 8:57:43 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(14 - 15)
-------------------

Apr 10, 2021 8:57:44 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(25 - 26)
-------------------

Apr 10, 2021 8:57:46 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(24 - 25)
-------------------

Apr 10, 2021 8:57:47 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(15 - 16)
-------------------

Apr 10, 2021 8:57:49 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(23 - 24)
-------------------

Apr 10, 2021 8:57:51 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(16 - 17)
-------------------

Apr 10, 2021 8:57:54 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(17 - 18)
-------------------

Apr 10, 2021 8:57:54 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(22 - 23)
-------------------

Apr 10, 2021 8:57:55 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(18 - 19)
-------------------

Apr 10, 2021 8:57:56 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(21 - 22)
-------------------

Apr 10, 2021 8:57:56 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for mirtha.dickinson:
(19 - 20)
-------------------


=========--------================GAME-FINISHED================--------=========
Apr 10, 2021 8:57:58 PM pa.lab7.game.Player printPlayerGotTokens
INFO: 

NEW TOKENS for scott.bins:
(20 - 21)
-------------------

Apr 10, 2021 8:58:00 PM pa.lab7.game.Player printScore
INFO: 
mirtha.dickinson has won 20 points:
(0 - 1),(1 - 2),(2 - 3),(3 - 4),(4 - 5),(5 - 6),(6 - 7),(7 - 8),(8 - 9),(9 - 10),(10 - 11),(11 - 12),(12 - 13),(13 - 14),(14 - 15),(15 - 16),(16 - 17),(17 - 18),(18 - 19),(19 - 20)


Apr 10, 2021 8:58:01 PM pa.lab7.game.Player printScore
INFO: 
scott.bins has won 10 points:
(20 - 21),(21 - 22),(22 - 23),(23 - 24),(24 - 25),(25 - 26),(26 - 27),(27 - 28),(28 - 29),(29 - 30)

 
 ```
