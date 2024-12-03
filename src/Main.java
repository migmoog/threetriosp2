import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ThreeTriosController;
import model.ThreeTrios;
import model.ThreeTriosGame;
import model.actor.Actor;
import model.actor.ComputerPlayer;
import model.actor.Player;
import model.actor.strategies.HighestSpread;
import model.actor.strategies.PickCorners;
import view.GUIThreeTriosView;
import view.SimpleThreeTriosTextView;
import view.ThreeTriosView;

/**
 * Entry point of a game of Three Trios.
 */
public class Main {

  /**
   * Entry point with arguments to set grid and deck from config files as well as
   * establishing the types of players.
   * Player options are 'human' for a human-controlled player, or the class name of the
   * strategy for a computer-controlled player (i.e. 'HighestSpread', 'PickCorners')
   *
   * @param args grid config file, deck config file, player1, player2 ...
   */
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Must provide a file paths to grid and deck");
      return;
    }
    List<Actor> players = new ArrayList<>();
    Map<Actor, ThreeTriosView> viewMap = new HashMap<>();
    Map<Actor, ThreeTriosController> controllerMap = new HashMap<>();

    ThreeTriosGame model = new ThreeTrios();
    TTConfigReader conf = new TTConfigReader();
    try {
      conf.readGrid(new FileReader(args[0]));
      conf.readDeck(new FileReader(args[1]));
    } catch (FileNotFoundException e) {
      System.out.println("Could not find files.");
    }
    for (int i = 2; i < args.length; i++) {
      switch (args[i].toLowerCase()) {
        case "human":
          players.add(new Player());
          break;
        case "highestspread":
          players.add(new ComputerPlayer(model, List.of(new HighestSpread(), new PickCorners())));
          break;
        case "pickcorners":
          players.add(new ComputerPlayer(model, List.of(new PickCorners())));
          break;
        default:
          throw new IllegalStateException("invalid type");
      }
    }

    conf.readActors(players);
    conf.start(model, false);

    for (Actor player : players) {
      if (player instanceof Player) {
        viewMap.put(player, new GUIThreeTriosView(model, 720, 720));
      } else if (player instanceof ComputerPlayer) {
        viewMap.put(player, new SimpleThreeTriosTextView(model,
                new InputStreamReader(System.in), System.out));
      }

      controllerMap.put(player, new ThreeTriosController(player, viewMap.get(player), model));

      if (player instanceof ComputerPlayer) {
        ((ComputerPlayer) player).setController(controllerMap.get(player));
      }
    }

    conf.start(model, false);
  }
}
