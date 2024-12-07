## Command line Arguments:

`[path/to/board_config.properties] [path/to/deck_config.properties] [player_1_type] [view_1_type] [player_2_type] [view_2_type]  ... [player_n_type] [view_n_type]`

#### Types:

`[player_i_type]`'s:
- `Human`- human controlled player
- `HighestSpread` - AI controlled player who prioritizes flipping the msot cards every turn
- `PickCorners` - AI controlled player who prioritizes picking corners

`[view_i_type]`'s:
- `PastelGUI` - A GUI view with lighter color tones (our GUI)
- `ContrastGUI` - A GUI with high-contrast colors (provider's GUI)
- `Text` - A console-based textual view of the game.
- `SimpleText` - A smaller version of the console-bsaed textual view. 

---

We were able to get our entire model to work with the provider's code.

## Changes from Provider Code:

- Only modified a few imports to ensure all packages were found properly
- Per provider's request, we moved a single method from the read-only model interface to the mutable-model interface.