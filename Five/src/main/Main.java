package main;

import gui.GameFrame;

import java.util.HashMap;
import java.util.Map;

import logger.GameLogger;
import model.AIPlayer;
import model.Board;
import model.BoardHelper;
import model.Cell;
import model.Human;
import model.IPlayer;
import model.Stone;
import utils.GameConstants;
import algorithm.EvaluationCount;

public class Main {
	private GameLogger logger = GameLogger.getInstance();

	public void start() {
		//inial data
		Board board = new Board();
		BoardHelper boardHelper = new BoardHelper(board);
		Object lock = new Object();

		GameFrame gameFrame = null;
		if (GameConstants.START_UI) {
			gameFrame = new GameFrame(boardHelper, lock);
		}

		//init players
		Map<Stone, IPlayer> players = new HashMap<Stone, IPlayer>();
		IPlayer c1 = new AIPlayer(Stone.WHITE, boardHelper);
		players.put(Stone.WHITE, c1);

		IPlayer c2;
		if (GameConstants.AI_VS_AI) {
			c2 = new AIPlayer(Stone.BLACK, boardHelper);
		} else {
			c2 = new Human(Stone.BLACK, gameFrame, lock);
		}
		players.put(Stone.BLACK, c2);

		//loop, black first
		Stone currentStone = Stone.BLACK;
		while(true) {
			IPlayer player = players.get(currentStone);
			final Cell cell = player.getMove();
			if (cell == null) {
				// game over
				GameLogger.getInstance().logInfo(currentStone + " give up!");
				GameLogger.getInstance().logDebug(boardHelper.getBoardStr());
				break;
			}

			boardHelper.move(currentStone, cell);

			if (GameConstants.START_UI) {
				gameFrame.refresh();
			}

			GameLogger.getInstance().logInfo(boardHelper.getBoardStr());
			if (player instanceof AIPlayer) {
				GameLogger.getInstance().logInfo("AI spent: " + AIPlayer.onceTime / 1000.0);
			}

			if (boardHelper.checkWin(cell)) {
				if (GameConstants.START_UI) {
					gameFrame.popWin(cell);
				}
				GameLogger.getInstance().logInfo(currentStone + " win!");
				break;
			}

			if (boardHelper.getProgress().size() == GameConstants.MOVES) {
				System.out.println("break;");
				break;
			}

			currentStone = currentStone.getOpposite();
		}

		double time = AIPlayer.totalTime / 1000.0;
		logger.logInfo("total time:" + time + "s");
		logger.logInfo(EvaluationCount.getCount() / time + "  count/s");

		if (GameConstants.START_UI) {
			gameFrame.dispose();
		}
	}

	public static void main(String[] args) {
		Main game = new Main();
		game.start();
	}
}
