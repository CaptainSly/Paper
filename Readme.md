A textbased game engine written in javafx for fun. Probably will never get finished.
Not gonna lie, a lot of the code is hacks. It's completely unoptimized and I'm sure a better framework would be better than what I have going on here. JavaFX is not meant for games of any type it seems. 
A lot of values had to be converted to and from ObservableValues and the way I do things, it clutters up and spaghettifies the code quite a bit. It also doesn't help that most of this code I wrote while I was stoned. Though, there are a few bangers in here that make it look like I know what I'm doing. This project is where all my ambitions go to die. 

Using The Following Libraries
=============================

[JavaFX](https://openjfx.io/) - 15.0.1

[FontAwesomeFX Commons](https://bitbucket.org/Jerady/fontawesomefx) 9.1.2 | FontAwesome 4.7.0-9.1.2

[ControlsFX](https://github.com/controlsfx/controlsfx) 11.1.0

[DiceNotation](https://github.com/Bernardo-MG/dice-notation-java) - 2.1.2

[GSON](https://github.com/google/gson) - 2.8.7

[LuaJ](https://github.com/luaj/luaj) - 3.0.1


Changelog
=========



version 0.0.2

* Added the basics for buying and selling items
* Added a Timer for actions
* Fixed Stats Pane not showing stat updates
* Working on a whole plethora of new things
	

Version 0.0.3
	
* Working on NPCs
* Refactored most of the WorldRegion into it's own sub regions
* Working on up towards Crafting
* Scratchpadding Character Race, Classes, Factions
* Implemented lootlists (Probably needs to be fixed will figure out later)
* Working on more Actions (Smithing, Battle, Crafting) 
* Fixed bug where buying items and selling them left ghost items in the list
* Implementing The Journal Alert
* Added Generic ItemTransfer Alert/Dialog
* Lots and Lots of Prototyping
* Equipment can be equipped to the player by dragging the item from the list to the correct equipment slot, and they provide a stat boost.
* Implemented a level up screen, takes in account leveling up repeatedly from a lot of exp gain. Not balanced what so ever, may have bugs. Can allot skill points to skills
* Fixed Bug Inv01, Implemented Sorting based on the ItemSlot empty value (Thank you Boolean.compare(x,y))
* Constantly Refractoring random things and renaming files
* Working on the Battle System. 
* Refactored the Dialog classes that extend Alert to have Alert in their name instead of Dialog
* Prototyping Enemies, beastiary

BUGS
====

BUG - Inv01 - FIXED
* If you equip equipment or toss an item while in the middle of an action, an item will be thrown into the first empty slot and create double ItemSlots. (Reorder the list when an Item is taken out so that all empty slots are moved to the bottom) 

TODO List
=========

* [x] Some type of list sorting for the Inventories. They can be sorted any way as long as empty ItemSlots are placed at the bottom (Should fix bug Inv01) -- COMPLETE (Thank god for Boolean.compare(x, y))
* [ ] Implement The Journal Alert Class -- IN PROGRESS
* [ ] Implement The Player Creation Dialog
* [ ] Implement The Character Class, Character Race and the Faction classes -- IN PROGRESS
* [ ] Implement A Beastiary - Goes hand in hand with the battle scene and enemies -- IN PROGRESS
* [ ] Implement The Map Class
* [x] Implement Dragging and Dropping of Equipment from the inventory to the equipment slots -- COMPLETE
* [ ] Implement Lua Scripting and create a basic Lua Library for the project.
* [ ] Implement Magic System
* [ ] Implement Stamina Requirement to move from location to location, a player has to rest before being able to move to different locations, (less spammy)
* [ ] Work on implementing the rest of the data inside the cdb file -- IN PROGRESS (See Things to Potentially work on)
* [x] Work on Player Level Up Method -- COMPLETE (Not balanced what so ever can be cheated really easily, but the node should scale if I decide to add more skills)
* [ ] Implement Balancing within the Skills, Stats, etc. -- IN PROGRESS ETERNAL
* [ ] Implement Battle Action and a battle state machine -- IN PROGRESS
* [ ] Implement Crafting -- IN PROGRESS
* [ ] Implement Questing
* [ ] Implement a tool requirement to Resource Gathering Actions
* [ ] Finish Implementing Item/Equipment Rarity
* [ ] Implement Saving and Loading
* [ ] Implement Options panel 
* [ ] Implement Inspection Panel
* [ ] Implement the 'Pen Editor'. Get it, cause the "engine" is called Paper. Pen and Paper. It's funny. 
	
Things to potentially work on
=============================

* Maybe switch to using FXGL, currently using just JavaFX, and FXGL was built to create games with using JavaFX. Shouldn't be entirely too hard to switch over. 

* Try to change how the CastleDB file is being loaded, either creating a custom deserializer or refactor the code into a easier to use method, currently it's all done with a JsonReader and literally stepping through the file line by line and extracting the relevant data. 

* Think about going for just straight json files for loading instead of the entire CastleDB file, which would allow for more precise creation of objects and entities. Or a combination of the two where certain data can require both. i.e. specific equipment and what not

	
ScreenShots
===========
![TestImage](test.png)