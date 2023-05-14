import random
import strutils

proc printPiles(piles: seq[int]) =
  for i, stones in pairs(piles):
    echo "Pile ", i, ": ", stones

proc isValidMove(piles: seq[int], pileIndex, stoneCount: int): bool =
  return pileIndex >= 0 and pileIndex < len(piles) and stoneCount > 0 and stoneCount <= piles[pileIndex]

proc applyMove(piles: var seq[int], pileIndex, stoneCount: int) =
  piles[pileIndex] -= stoneCount

proc isGameOver(piles: seq[int]): bool =
  for stones in piles:
    if stones > 0:
      return false
  return true

proc getComputerMove(piles: seq[int]): (int, int) =
  var validMoves: seq[(int, int)] = @[]
  for i, stones in pairs(piles):
    for j in 1..stones:
      validMoves.add((i, j))
  return validMoves[rand(validMoves.len)]

proc getPlayerMove(piles: seq[int]): (int, int) =
  var validMove = false
  var pileIndex, stoneCount: int
  while not validMove:
    echo "Enter the pile index (0 to ", len(piles) - 1, "): "
    pileIndex = parseInt(stdin.readLine)
    echo "Enter the number of stones to remove: "
    stoneCount = parseInt(stdin.readLine)
    validMove = isValidMove(piles, pileIndex, stoneCount)
    if not validMove:
      echo "Invalid move. Please try again."
  return (pileIndex, stoneCount)

proc nimGame() =
  # const pileCount = 3
  var piles: seq[int] = @[3, 4, 5]
  var currentPlayer = "Player"

  while not isGameOver(piles):
    echo "\nCurrent Game State:"
    printPiles(piles)
    echo currentPlayer, "'s turn."

    var move: (int, int) = (
      if currentPlayer == "Player": getPlayerMove(piles)
      else: getComputerMove(piles))

    applyMove(piles, move[0], move[1])
    currentPlayer = if currentPlayer == "Player": "Computer" else: "Player"

  echo "\nCurrent Game State:"
  printPiles(piles)
  echo currentPlayer, " wins!"

nimGame()
