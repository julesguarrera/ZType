import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

// Represents the world 
class ZTypeWorld extends World {
  ILoWord words; // represents the list of words on the screen
  Random rand; // represents the random seed
  long score; // represents the current score of the game
  int level; // represents the current level of the game
  int speed; // represents the current speed the words are falling down at
  int tick; // represents how many ticks have passed since the beginning of the game

  ZTypeWorld() {
    this(new Random());
  }

  ZTypeWorld(Random rand) {
    this.words = new MtLoWord();
    this.rand = rand;
    this.score = 0;
    this.level = 1;
  }

  ZTypeWorld(ILoWord words, Random rand, long score, int level, int tick) {
    this.rand = rand;
    this.score = score;
    this.level = level;
    this.speed = level;
    this.words = words;
    this.tick = tick;
  }

  // draws the world scene
  public WorldScene makeScene() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (this.score == 10) {
      return (this.words.draw(new WorldScene(ILoWord.WORLD_WIDTH, ILoWord.WORLD_HEIGHT)))
          .placeImageXY(new CircleImage(20, OutlineMode.SOLID, Color.MAGENTA), ILoWord.PLAYER_X,
              ILoWord.PLAYER_Y)
          .placeImageXY(new TextImage("Score: " + this.score, 15, Color.BLACK), 50, 785)
          .placeImageXY(new TextImage("Level: " + this.level, 15, Color.BLACK), 550, 785);
    }
    else if (this.score >= Math.pow(2, this.level - 1) * 10
        && this.score <= ((Math.pow(2, this.level - 1)) * 10) + (2 * (this.level - 1))) {
      return (this.words.draw(new WorldScene(ILoWord.WORLD_WIDTH, ILoWord.WORLD_HEIGHT)))
          .placeImageXY(new CircleImage(20, OutlineMode.SOLID, Color.MAGENTA), ILoWord.PLAYER_X,
              ILoWord.PLAYER_Y)
          .placeImageXY(new TextImage("Score: " + this.score, 15, Color.BLACK), 50, 785)
          .placeImageXY(new TextImage("Level: " + this.level, 15, Color.BLACK), 550, 785)
          .placeImageXY(new TextImage("Level " + this.level, 50, Color.BLACK),
              ILoWord.WORLD_WIDTH / 2 - 10, ILoWord.WORLD_HEIGHT / 2);
    }
    else {
      return (this.words.draw(new WorldScene(ILoWord.WORLD_WIDTH, ILoWord.WORLD_HEIGHT)))
          .placeImageXY(new CircleImage(20, OutlineMode.SOLID, Color.MAGENTA), ILoWord.PLAYER_X,
              ILoWord.PLAYER_Y)
          .placeImageXY(new TextImage("Score: " + this.score, 15, Color.BLACK), 50, 785)
          .placeImageXY(new TextImage("Level: " + this.level, 15, Color.BLACK), 550, 785);
    }
  }

  // Move the words on the scene. Adds a new words at a random location every 20th
  // tick of the clock
  public World onTick() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (this.score >= Math.pow(2, this.level) * 10) {
      return new ZTypeWorld(this.words, this.rand, this.score, this.level + 1, this.tick + 1);
    }
    else if (this.tick % (20 - (this.level - 1)) == 0) {
      String test = (new Utils().generator(this.rand, this.level));
      ILoWord add = this.words.addToEnd(new InactiveWord(test, rand.nextInt(580) + 10, 50));

      return new ZTypeWorld(add.move(this.level), this.rand, this.score, this.level, this.tick + 1);
    }
    else {
      return new ZTypeWorld(this.words.move(this.level), this.rand, this.score, this.level,
          this.tick + 1);
    }
  }

  // checks if a game is ovwr
  public WorldEnd worldEnds() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (words.gameOver()) {
      return new WorldEnd(true, this.makeAFinalScene());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  public WorldScene makeAFinalScene() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return (new WorldScene(ILoWord.WORLD_WIDTH, ILoWord.WORLD_HEIGHT))
        .placeImageXY(new TextImage("Game over", 24, Color.RED), ILoWord.WORLD_WIDTH / 2,
            ILoWord.WORLD_HEIGHT / 2 - 20)
        .placeImageXY(new TextImage("Final Score: " + this.score, 15, Color.BLACK),
            ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 + 5)
        .placeImageXY(new TextImage("Level: " + this.level, 15, Color.BLACK),
            ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 + 25);
  }

  public ZTypeWorld onKeyEvent(String key) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    long score = 0;
    ZTypeWorld worldCreation = new ZTypeWorld(this.words.checkAndReduce(key), this.rand, this.score,
        this.level, this.tick);
    if (worldCreation.words.filterMaybe() && worldCreation.words.firstEmpty()) {
      score = worldCreation.score + (2 * this.level);
    }
    else {
      score = worldCreation.score;
    }
    return new ZTypeWorld(worldCreation.words.filterOutEmpties(), worldCreation.rand, score,
        worldCreation.level, this.tick);

  }

  /*
   * Classwide Template:
   * 
   * Fields:
   * 
   * ... this.words ... -- ILoWord ... this.rand ... -- Random ... this.score
   * ...-- long ... this.level ... -- int ... this.speed ... -- int ... this.tick
   * ... -- int
   * 
   * Methods:
   * 
   * ... this.makeScene()... -- WorldScene ... this.onTick() ... -- World ...
   * this.worldEnds() ... -- WorldEnd ... this.makeAFinalScene() ... -- WorldScene
   * ... this.onKeyEvent(String key) ... -- ZTypeWorld
   * 
   * Methods of Fields:
   * 
   * ... this.words.checkAndrReduce(String character) ... -- ILoWord ...
   * this.words.addToEnd(IWord word) ... -- ILoWord ...
   * this.words.filterOutEmpties() ... -- ILoWord ... this.words.newActive(String
   * character) ... -- ILoWord ... this.words.nullChar(String character) ... --
   * boolean ... this.words.getActive(String character) ... -- ActiveWord ...
   * this.words.filterMayber() ... -- boolean ... this.words.move(int level) ...
   * -- ILoWord ... this.words.firstEmpty() ... -- boolean ...
   * this.words.gameOver() ... -- boolean ... this.words.draw(WordScene world) ...
   * -- WorldScene
   * 
   * 
   */
}

class Utils {
  String letters = "abcdefghijklmnopqrstuvwxyz";

  // generates a random word
  String generator(Random rand) {
    return this.generatorHelp(rand.nextInt(4) + 2, rand, "");
  }

  String generator(Random rand, int level) {
    return this.generatorHelp(rand.nextInt(level + 2) + 1, rand, "");
  }

  // accumulates a string of random letters
  String generatorHelp(int length, Random rand, String acc) {
    if (length > 0) {
      return generatorHelp(length - 1, rand, acc + letterGen(rand.nextInt(25)));
    }
    else {
      return acc;
    }
  }

  // generates a random letter
  String letterGen(int num) {
    return letters.substring(num, num + 1);
  }
}

// represents a list of words
interface ILoWord {

  int WORLD_HEIGHT = 800;

  int WORLD_WIDTH = 600;

  int PLAYER_X = 290;

  int PLAYER_Y = 750;

  // takes a 1 letter long string and removes that letter from the first spot of
  // all the active words in a list whos letters start with the given string
  ILoWord checkAndReduce(String character);

  // add word to end of list
  ILoWord addToEnd(IWord word);

  // filters out empty strings
  ILoWord filterOutEmpties();

  // makes new active word
  ILoWord newActive(String character);

  // checks if null char
  boolean nullChar(String character);

  // finds active word
  ActiveWord getActive(String character);

  // boolean for filter
  boolean filterMaybe();

  // moves a list of words
  ILoWord move(int level);

  // checks if first empty
  boolean firstEmpty();

  // checks if any words in a list have gone below the screen
  boolean gameOver();

  // draws a list of words on a worldscene
  WorldScene draw(WorldScene world);
}

// represents an empty list of words
class MtLoWord implements ILoWord {

  // checks if the first is empty
  public boolean firstEmpty() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return false;
  }

  // checks if any words in a list have gone below the screen
  public boolean gameOver() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return false;
  }

  // boolean for filter
  public boolean filterMaybe() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return false;
  }

  // checks if string s equals any first letters in the list and remove that first
  // letter
  // from any active words that do (list is empty so return this)
  public ILoWord checkAndReduce(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this;
  }

  // takes an empty list and adds IWord w to it
  public ILoWord addToEnd(IWord word) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return new ConsLoWord(word, this);
  }

  // filters the empty strings "" out of an empty list (returns this)
  public ILoWord filterOutEmpties() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this;
  }

  // returns world as the list of words is empty and there is none to draw
  public WorldScene draw(WorldScene world) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return world;
  }

  // makes a new active word
  public ILoWord newActive(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this;
  }

  // checks if null is char
  public boolean nullChar(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return false;
  }

  // finds active word
  public ActiveWord getActive(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    throw new IllegalArgumentException("Shouldn't be here");
  }

  // returns the same list of words (empty) as there are no words to move
  public ILoWord move(int level) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this;
  }

  /*
   * Classwide Template:
   * 
   * Fields:
   * 
   * 
   * 
   * Methods:
   * 
   * 
   * ... this.checkAndrReduce(String character) ... -- ILoWord ...
   * this.addToEnd(IWord word) ... -- ILoWord ... this.filterOutEmpties() ... --
   * ILoWord ... this.newActive(String character) ... -- ILoWord ...
   * this.nullChar(String character) ... -- boolean ... this.getActive(String
   * character) ... -- ActiveWord ... this.filterMayber() ... -- boolean ...
   * this.move(int level) ... -- ILoWord ... this.firstEmpty() ... -- boolean ...
   * this.gameOver() ... -- boolean ... this.draw(WordScene world) ... --
   * WorldScene
   * 
   * 
   */

}

// represents a cons list of words
class ConsLoWord implements ILoWord {
  IWord first;
  ILoWord rest;

  ConsLoWord(IWord first, ILoWord rest) {
    this.first = first;
    this.rest = rest;
  }

  // boolean for filter
  public boolean filterMaybe() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this.first.blankString() || this.rest.filterMaybe();
  }

  // checks if first empty
  public boolean firstEmpty() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this.first.blankString();
  }

  // takes a 1 letter long string and removes that letter from the first spot of
  // all the active words in a list whos letters start with the given string
  public ILoWord checkAndReduce(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (first.isActive()) {
      return new ConsLoWord(first.wordInfo(character), this.rest);
    }
    else if (!this.nullChar(character)) {
      return this;
    }
    else {
      return new ConsLoWord(this.getActive(character), (this.newActive(character)));
    }
  }

  // makes a new active word
  public ILoWord newActive(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (this.first.nullChar(character)) {
      return new ConsLoWord(new InactiveWord("", 0, 0), this.rest);
    }
    return new ConsLoWord(this.first, this.rest.newActive(character));
  }

  // finds an active word
  public ActiveWord getActive(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (this.first.nullChar(character)) {
      return first.createActive();
    }
    else {
      return rest.getActive(character);
    }
  }

  // checks if null char
  public boolean nullChar(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return first.nullChar(character) || rest.nullChar(character);
  }

  // moves a list of words
  public ILoWord move(int level) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return new ConsLoWord(this.first.moveWord(level), this.rest.move(level));
  }

  // add word to end of list
  public ILoWord addToEnd(IWord word) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     * Fields on Parameters:
     * 
     * word.
     * 
     */
    return new ConsLoWord(first, rest.addToEnd(word));
  }

  // filters out empty strings
  public ILoWord filterOutEmpties() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (first.blankString()) {
      return rest.filterOutEmpties();
    }
    else {

      return new ConsLoWord(first, rest.filterOutEmpties());
    }
  }

  // draws a list of words on a world scene
  public WorldScene draw(WorldScene world) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return rest.draw(first.drawHelp(world));
  }

  // checks if any words in a list have gone below the screen
  public boolean gameOver() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return first.belowScreen() || rest.gameOver();
  }

}

/*
 * Classwide Template:
 * 
 * Fields:
 * 
 * ... this.first ... -- IWord ... this.rest ... -- ILoWord
 * 
 * 
 * Methods:
 * 
 * 
 * ... this.checkAndrReduce(String character) ... -- ILoWord ...
 * this.addToEnd(IWord word) ... -- ILoWord ... this.filterOutEmpties() ... --
 * ILoWord ... this.newActive(String character) ... -- ILoWord
 * ...this.nullChar(String character) ... -- boolean ... this.getActive(String
 * character) ... -- ActiveWord ... this.filterMayber() ... -- boolean
 * ...this.move(int level) ... -- ILoWord ... this.firstEmpty() ... -- boolean
 * ...this.gameOver() ... -- boolean ... this.draw(WordScene world) ...
 * --WorldScene
 * 
 * Methods on Fields:
 * 
 * ... this.rest.checkAndrReduce(String character) ... -- ILoWord ...
 * this.rest.addToEnd(IWord word) ... -- ILoWord ...
 * this.rest.filterOutEmpties() ... -- ILoWord ... this.rest.newActive(String
 * character) ... -- ILoWord ... this.rest.nullChar(String character) ... --
 * boolean ... this.rest.getActive(String character) ... -- ActiveWord ...
 * this.rest.filterMayber() ... -- boolean ... this.rest.move(int level) ... --
 * ILoWord ... this.rest.firstEmpty() ... -- boolean ... this.rest.gameOver()
 * ... -- boolean ... this.rest.draw(WordScene world) ... -- WorldScene
 * 
 * 
 * ... this.first.drawHelp(WorldScene World) ... -- WorldScene ...
 * this.first.isActive() ... -- boolean ... this.first.compare(IWord comparer)
 * ... -- boolean ... this.first.compareHelper(String comparer) ... -- boolean
 * ... this.first.wordInfo(String character) ... -- IWord ...
 * this.first.blankString() ... -- boolean ... this.first.nullChar(String
 * character) ... -- boolean ... this.first.createActive() ... -- ActiveWord ...
 * this.first.moveWord(int level) ... -- IWord ... this.first.belowScreen() ...
 * -- boolean
 * 
 * 
 * 
 */

// represents a word with x and y coordinates
interface IWord {

  // helper method for draw function
  WorldScene drawHelp(WorldScene world);

  // checks if word is active
  boolean isActive();

  // compares a word to a word to see if theyre the same
  boolean compare(IWord comparer);

  // helper for compare method, takes in a string
  boolean compareHelper(String comparer);

  // takes character from a string
  IWord wordInfo(String character);

  // checks if string is blank
  boolean blankString();

  // checks if string is a null character
  boolean nullChar(String character);

  // creates an active word
  ActiveWord createActive();

  // moves a word, changing its x and y coordinates
  IWord moveWord(int level);

  // checks if a word has a y coordinate that is below the screen
  boolean belowScreen();

}

// represents an active IWord
class ActiveWord implements IWord {
  String word;
  int x;
  int y;

  ActiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }

  // checks if string is a null character
  public boolean nullChar(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return false;
  }

  // checks if word is active
  public boolean isActive() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return true;
  }

  // takes character from string
  public IWord wordInfo(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    if (word.length() > 0 && word.substring(0, 1).equals(character)) {
      return new ActiveWord(word.substring(1), x, y);
    }
    return this;
  }

  // compares a word to a word to see if theyre the same
  public boolean compare(IWord comparer) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     * Methods of Parameters:
     * 
     * 
     * 
     */

    return comparer.compareHelper(this.word);

  }

  // helper for compare method, takes in a string
  public boolean compareHelper(String comparer) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return (word.toLowerCase().compareTo(comparer.toLowerCase()) <= 0);

  }

  // checks if string is blank
  public boolean blankString() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return word.equals("");
  }

  // method for draw function
  public WorldScene draw(WorldScene world) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return world.placeImageXY(new TextImage(word, 24, Color.MAGENTA), x, y);
  }

  // creates an active word
  public ActiveWord createActive() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this;
  }

  // moves a word
  public IWord moveWord(int level) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return new ActiveWord(this.word, this.x, this.y + (level * 2));
  }

  // checks if a word is below the screen
  public boolean belowScreen() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return y >= ILoWord.PLAYER_Y;
  }

  // draws a word on a worldscene at this.x and this.y coordinates as green
  public WorldScene drawHelp(WorldScene world) {

    /*
     * ... Everything from class wide template...
     */

    return world.placeImageXY(new TextImage(this.word, 25, Color.GREEN), this.x, this.y);
  }

  /*
   * Classwide Template:
   * 
   * Fields:
   * 
   * ... this.word ... -- String ... this.x ... -- int ... this.y ... -- int
   * 
   * Methods:
   * 
   * ... this.drawHelp(WorldScene World) ... -- WorldScene ... this.isActive() ...
   * -- boolean ... this.compare(IWord comparer) ... -- boolean ...
   * this.compareHelper(String comparer) ... -- boolean ... this.wordInfo(String
   * character) ... -- IWord ... this.blankString() ... -- boolean ...
   * this.nullChar(String character) ... -- boolean ... this.createActive() ... --
   * ActiveWord ... this.moveWord(int level) ... -- IWord ... this.boolean
   * belowScreen() ... -- boolean
   * 
   * 
   * 
   */

}

// represents an inactive IWord
class InactiveWord implements IWord {
  String word;
  int x;
  int y;

  InactiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }

  // checks if string is a null character
  public boolean nullChar(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return word.length() > 0 && word.substring(0, 1).equals(character);
  }

  // moves a word
  public IWord moveWord(int level) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return new InactiveWord(this.word, this.x, this.y + (level * 2));
  }

  // checks if a word is active
  public boolean isActive() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return false;
  }

  // creates an active word
  public ActiveWord createActive() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return new ActiveWord(this.word.substring(1, word.length()), this.x, this.y);
  }

  // gets a character from a string
  public IWord wordInfo(String character) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return this;
  }

  // compares a word to a word to see if theyre the same
  public boolean compare(IWord comparer) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     * Methods of Parameters:
     * 
     */

    return comparer.compareHelper(this.word);

  }

  // helper for compare method, takes in a string
  public boolean compareHelper(String comparer) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return (word.toLowerCase().compareTo(comparer.toLowerCase()) <= 0);

  }

  // checks if string is blank
  public boolean blankString() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return word.equals("");
  }

  // draws the world
  public WorldScene draw(WorldScene world) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return world.placeImageXY(new TextImage(word, 25, Color.RED), this.x, this.y);
  }

  // checks if word is below the screen
  public boolean belowScreen() {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return y >= ILoWord.PLAYER_Y;
  }

  // draws a word on a WorldScene at this.x and this.y coordinates as red
  public WorldScene drawHelp(WorldScene world) {

    /*
     * Template:
     * 
     * ... Same as classwide template ...
     * 
     */

    return world.placeImageXY(new TextImage(this.word, 25, Color.RED), this.x, this.y);

  }

  /*
   * Classwide Template:
   * 
   * Fields:
   * 
   * ... this.word ... -- String ... this.x ... -- int ... this.y ... -- int
   * 
   * Methods:
   * 
   * ... this.drawHelp(WorldScene World) ... -- WorldScene ... this.isActive() ...
   * -- boolean ... this.compare(IWord comparer) ... -- boolean ...
   * this.compareHelper(String comparer) ... -- boolean ... this.wordInfo(String
   * character) ... -- IWord ... this.blankString() ... -- boolean ...
   * this.nullChar(String character) ... -- boolean ... this.createActive() ... --
   * ActiveWord ... this.moveWord(int level) ... -- IWord ... this.boolean
   * belowScreen() ... -- boolean
   * 
   * 
   * 
   */

}

class ExamplesZWorld {
  ExamplesZWorld() {
  }

  // ActiveWord lowercase
  IWord apple = new ActiveWord("apple", 300, 300);
  IWord pear = new ActiveWord("pear", 100, 200);
  IWord banana = new ActiveWord("banana", 400, 100);
  IWord orange = new ActiveWord("orange", 200, 400);
  IWord babaco = new ActiveWord("babaco", 500, 500);
  IWord fig = new ActiveWord("fig", 200, 200);

  // InactiveWord lowercase
  IWord pepper = new InactiveWord("pepper", 300, 100);
  IWord tomato = new InactiveWord("tomato", 200, 300);
  IWord carrot = new InactiveWord("carrot", 100, 400);
  IWord pea = new InactiveWord("pea", 100, 100);
  IWord broccoli = new InactiveWord("broccoli", 300, 600);
  IWord beet = new InactiveWord("beet", 600, 200);

  // ActiveWord uppercase
  IWord STEAK = new ActiveWord("STEAK", 100, 400);
  IWord CHICKEN = new ActiveWord("CHICKEN", 200, 600);
  IWord HAM = new ActiveWord("HAM", 300, 400);
  IWord APPLE = new ActiveWord("APPLE", 350, 550);
  IWord BABACO = new ActiveWord("BABACO", 750, 800);

  // InactiveWord uppercase
  IWord RICE = new InactiveWord("RICE", 500, 100);
  IWord CORN = new InactiveWord("CORN", 100, 300);
  IWord NUTS = new InactiveWord("NUTS", 300, 200);

  // empty strings
  IWord naa = new ActiveWord("", 0, 0);
  IWord nai = new InactiveWord("", 100, 100);

  ILoWord mt = new MtLoWord();

  // world scene background
  WorldScene bg = new WorldScene(800, 600);

  // non-sorted list of ActiveWord all lowercase
  ILoWord la1 = new ConsLoWord(this.banana, this.mt);
  ILoWord la2 = new ConsLoWord(this.pear, this.la1);
  ILoWord la3 = new ConsLoWord(this.apple, this.la2);
  ILoWord la4 = new ConsLoWord(this.orange, this.la3);

  // non-sorted list of InactiveWord all lowercase
  ILoWord li1 = new ConsLoWord(this.pepper, this.mt);
  ILoWord li2 = new ConsLoWord(this.tomato, this.li1);
  ILoWord li3 = new ConsLoWord(this.pea, this.li2);
  ILoWord li4 = new ConsLoWord(this.carrot, this.li3);

  // non-sorted list of mixed words all lowercase
  ILoWord lai1 = new ConsLoWord(this.carrot, new ConsLoWord(this.apple,
      new ConsLoWord(this.tomato, new ConsLoWord(this.orange, this.mt))));

  // non-sorted list of mixed words and mixed cases
  ILoWord lmm = new ConsLoWord(this.carrot,
      new ConsLoWord(this.BABACO, new ConsLoWord(this.apple, new ConsLoWord(this.RICE, this.mt))));

  // non-sorted list with a ""
  ILoWord lea = new ConsLoWord(this.fig,
      new ConsLoWord(this.pepper, new ConsLoWord(this.naa, this.mt)));

  ILoWord sameLetter = new ConsLoWord(this.pepper,
      new ConsLoWord(this.pear, new ConsLoWord(this.orange, this.mt)));

  // list that is long and sorted
  ILoWord sortedLongList = new ConsLoWord(this.banana,
      new ConsLoWord(this.carrot,
          new ConsLoWord(this.CHICKEN, new ConsLoWord(this.HAM, new ConsLoWord(this.orange,
              new ConsLoWord(this.pepper, new ConsLoWord(this.RICE, this.mt)))))));

  // list of mixed words with multiple words beginning with the same letter
  ILoWord mixedWords = new ConsLoWord(this.pear, new ConsLoWord(this.pepper,
      new ConsLoWord(this.pea, new ConsLoWord(this.HAM, new ConsLoWord(this.RICE, this.mt)))));

  // list with 2 ActiveWord ""
  ILoWord aEmpty2 = new ConsLoWord(this.naa,
      new ConsLoWord(this.babaco, new ConsLoWord(this.pear, new ConsLoWord(this.naa, this.mt))));

  // list with 2 InactiveWord ""
  ILoWord iEmpty2 = new ConsLoWord(this.nai,
      new ConsLoWord(this.beet, new ConsLoWord(this.broccoli, new ConsLoWord(this.nai, this.mt))));
  // list of mixed cases same letters activeword
  ILoWord mcsa = new ConsLoWord(this.apple, new ConsLoWord(this.BABACO,
      new ConsLoWord(this.APPLE, new ConsLoWord(this.babaco, this.mt))));

  // list of all empty ""
  ILoWord allEmpty = new ConsLoWord(this.naa,
      new ConsLoWord(this.nai, new ConsLoWord(this.naa, new ConsLoWord(this.nai, this.mt))));

  // non-sorted list of ActiveWord all uppercase
  ILoWord lau1 = new ConsLoWord(this.STEAK,
      new ConsLoWord(this.HAM, new ConsLoWord(this.CHICKEN, this.mt)));

  // sorted list with a ""
  ILoWord slei = new ConsLoWord(this.nai,
      new ConsLoWord(this.banana, new ConsLoWord(this.RICE, this.mt)));

  IWord iaEmpty = new InactiveWord("", 300, 200);

  IWord aEmpty = new ActiveWord("", 300, 200);

  World initial = new ZTypeWorld(new Random(5));

  World w1 = new ZTypeWorld(this.la4, new Random(5), 300, 6, 0);

  World w2 = new ZTypeWorld(this.li4, new Random(), 550, 8, 5);

  World w3 = new ZTypeWorld(this.sameLetter, new Random(10), 300, 6, 0);

  World w4 = new ZTypeWorld(this.lmm, new Random(), 550, 8, 5);

  ZTypeWorld z1 = new ZTypeWorld(this.lmm, new Random(), 550, 8, 5);

  ZTypeWorld z2 = new ZTypeWorld(this.la4, new Random(), 300, 6, 0);

  World w5 = new ZTypeWorld(this.lea, new Random(), 400, 4, 0);

  // tests draw
  boolean testDraw(Tester t) {
    return
    // checking draw() for a list of ActiveWord
    t.checkExpect(this.la4.draw(this.bg),
        new WorldScene(800, 600).placeImageXY(new TextImage("orange", 25, Color.GREEN), 200, 400)
            .placeImageXY(new TextImage("apple", 25, Color.GREEN), 300, 300)
            .placeImageXY(new TextImage("pear", 25, Color.GREEN), 100, 200)
            .placeImageXY(new TextImage("banana", 25, Color.GREEN), 400, 100))
        // checking draw() for a list of InactiveWord
        && t.checkExpect(this.li4.draw(this.bg),
            new WorldScene(800, 600).placeImageXY(new TextImage("carrot", 25, Color.RED), 100, 400)
                .placeImageXY(new TextImage("pea", 25, Color.RED), 100, 100)
                .placeImageXY(new TextImage("tomato", 25, Color.RED), 200, 300)
                .placeImageXY(new TextImage("pepper", 25, Color.RED), 300, 100))
        // checking draw() for a list of mixed words
        && t.checkExpect(this.lai1.draw(this.bg),
            new WorldScene(800, 600).placeImageXY(new TextImage("carrot", 25, Color.RED), 100, 400)
                .placeImageXY(new TextImage("apple", 25, Color.GREEN), 300, 300)
                .placeImageXY(new TextImage("tomato", 25, Color.RED), 200, 300)
                .placeImageXY(new TextImage("orange", 25, Color.GREEN), 200, 400))
        // checking draw() for an empty list
        && t.checkExpect(this.mt.draw(this.bg), new WorldScene(800, 600))
        // checking draw() for a list of mixed words with mixed cases
        && t.checkExpect(this.lmm.draw(this.bg),
            new WorldScene(800, 600).placeImageXY(new TextImage("carrot", 25, Color.RED), 100, 400)
                .placeImageXY(new TextImage("BABACO", 25, Color.GREEN), 750, 800)
                .placeImageXY(new TextImage("apple", 25, Color.GREEN), 300, 300)
                .placeImageXY(new TextImage("RICE", 25, Color.RED), 500, 100))
        // checking draw() with a list that includes ""
        && t.checkExpect(this.lea.draw(this.bg),
            new WorldScene(800, 600).placeImageXY(new TextImage("", 25, Color.GREEN), 0, 0)
                .placeImageXY(new TextImage("pepper", 25, Color.RED), 300, 100)
                .placeImageXY(new TextImage("fig", 25, Color.GREEN), 200, 200));

  }

  // tests drawHelp
  boolean testDrawHelp(Tester t) {
    return
    // takes an active word
    t.checkExpect(this.orange.drawHelp(bg),
        bg.placeImageXY(new TextImage("orange", 25, Color.GREEN), 200, 400))
        // takes an inactive word
        && t.checkExpect(this.pepper.drawHelp(bg),
            bg.placeImageXY(new TextImage("pepper", 25, Color.RED), 300, 100))
        // takes a ""
        && t.checkExpect(this.naa.drawHelp(bg),
            bg.placeImageXY(new TextImage("", 25, Color.GREEN), 0, 0))
        // takes a word with uppercase
        && t.checkExpect(this.NUTS.drawHelp(bg),
            bg.placeImageXY(new TextImage("NUTS", 25, Color.RED), 300, 200));
  }

  // tests makeScene
  boolean testMakeScene(Tester t) {
    return t
        .checkExpect(this.initial.makeScene(), new WorldScene(600, 800)
            .placeImageXY(new CircleImage(20, OutlineMode.SOLID, Color.MAGENTA), ILoWord.PLAYER_X,
                ILoWord.PLAYER_Y)
            .placeImageXY(new TextImage("Score: 0", 15, Color.BLACK), 50, 785)
            .placeImageXY(new TextImage("Level: 1", 15, Color.BLACK), 550, 785))
        && t.checkExpect(this.w1.makeScene(),
            new WorldScene(600, 800)
                .placeImageXY(new TextImage("orange", 25, Color.GREEN), 200, 400)
                .placeImageXY(new TextImage("apple", 25, Color.GREEN), 300, 300)
                .placeImageXY(new TextImage("pear", 25, Color.GREEN), 100, 200)
                .placeImageXY(new TextImage("banana", 25, Color.GREEN), 400, 100)
                .placeImageXY(new CircleImage(20, OutlineMode.SOLID, Color.MAGENTA),
                    ILoWord.PLAYER_X, ILoWord.PLAYER_Y)
                .placeImageXY(new TextImage("Score: 300", 15, Color.BLACK), 50, 785)
                .placeImageXY(new TextImage("Level: 6", 15, Color.BLACK), 550, 785))
        && t.checkExpect(this.w2.makeScene(),
            new WorldScene(600, 800).placeImageXY(new TextImage("carrot", 25, Color.RED), 100, 400)
                .placeImageXY(new TextImage("pea", 25, Color.RED), 100, 100)
                .placeImageXY(new TextImage("tomato", 25, Color.RED), 200, 300)
                .placeImageXY(new TextImage("pepper", 25, Color.RED), 300, 100)
                .placeImageXY(new CircleImage(20, OutlineMode.SOLID, Color.MAGENTA),
                    ILoWord.PLAYER_X, ILoWord.PLAYER_Y)
                .placeImageXY(new TextImage("Score: 550", 15, Color.BLACK), 50, 785)
                .placeImageXY(new TextImage("Level: 8", 15, Color.BLACK), 550, 785));

  }

  // tests utils
  boolean testUtils(Tester t) {
    return t.checkExpect(new Utils().generator(new Random(239874)), "tdwjb")
        && t.checkExpect(new Utils().letterGen(5), "f")
        && t.checkExpect(new Utils().generatorHelp(5, new Random(239874), ""), "vtdwj")
        && t.checkExpect(new Utils().generator(new Random(23)), "xiws")
        && t.checkExpect(new Utils().letterGen(3), "d")
        && t.checkExpect(new Utils().generatorHelp(4, new Random(239874), ""), "vtdw");

  }

  // testing onKeyEvent
  boolean testOnKey(Tester t) {
    return
    // checking when one word starts with the given string
    t.checkExpect(this.w1.onKeyEvent("o"),
        new ZTypeWorld(
            new ConsLoWord(new ActiveWord("range", 200, 400),
                new ConsLoWord(this.apple,
                    new ConsLoWord(this.pear, new ConsLoWord(this.banana, this.mt)))),
            new Random(), 300L, 6, 0))
        // testing when no words start with a given string
        && t.checkExpect(this.w1.onKeyEvent("z"), this.w1)
        // testing when multiple start with the given string
        && t.checkExpect(this.w3.onKeyEvent("p"),
            new ZTypeWorld(
                new ConsLoWord(new ActiveWord("epper", 300, 100),
                    new ConsLoWord(this.pear, new ConsLoWord(this.orange, this.mt))),
                new Random(), 300L, 6, 0))
        && t.checkExpect(this.w5.onKeyEvent("f"), new ZTypeWorld(
            new ConsLoWord(new ActiveWord("ig", 200, 200), new ConsLoWord(this.pepper, this.mt)),
            new Random(), 400L, 4, 0));

  }

  // tests world end
  boolean testWorldEnd(Tester t) {
    return t.checkExpect(this.w1.worldEnds(), new WorldEnd(false, this.w1.makeScene()))
        && t.checkExpect(this.w4.worldEnds(),
            (new WorldEnd(true,
                (new WorldScene(ILoWord.WORLD_WIDTH, ILoWord.WORLD_HEIGHT))
                    .placeImageXY(new TextImage("Game over", 24, Color.RED),
                        ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 - 20)
                    .placeImageXY(new TextImage("Final Score: " + 550, 15, Color.BLACK),
                        ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 + 5)
                    .placeImageXY(new TextImage("Level: " + 8, 15, Color.BLACK),
                        ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 + 25))));
  }

  // tests final scene
  boolean testFinalScene(Tester t) {
    return t.checkExpect(this.z1.makeAFinalScene(),
        new WorldScene(ILoWord.WORLD_WIDTH, ILoWord.WORLD_HEIGHT)
            .placeImageXY(new TextImage("Game over", 24, Color.RED), ILoWord.WORLD_WIDTH / 2,
                ILoWord.WORLD_HEIGHT / 2 - 20)
            .placeImageXY(new TextImage("Final Score: " + 550, 15, Color.BLACK),
                ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 + 5)
            .placeImageXY(new TextImage("Level: " + 8, 15, Color.BLACK), ILoWord.WORLD_WIDTH / 2,
                ILoWord.WORLD_HEIGHT / 2 + 25))
        && t.checkExpect(this.z2.makeAFinalScene(),
            (new WorldScene(ILoWord.WORLD_WIDTH, ILoWord.WORLD_HEIGHT))
                .placeImageXY(new TextImage("Game over", 24, Color.RED), ILoWord.WORLD_WIDTH / 2,
                    ILoWord.WORLD_HEIGHT / 2 - 20)
                .placeImageXY(new TextImage("Final Score: " + 300, 15, Color.BLACK),
                    ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 + 5)
                .placeImageXY(new TextImage("Level: " + 6, 15, Color.BLACK),
                    ILoWord.WORLD_WIDTH / 2, ILoWord.WORLD_HEIGHT / 2 + 25));
  }

  // tests filterOutEmpties
  boolean testFilterOutEmpties(Tester t) {
    return
    // checking filterOutEmpties() with a list with 1 ActiveWord empty ""
    t.checkExpect(this.lea.filterOutEmpties(),
        new ConsLoWord(this.fig, new ConsLoWord(this.pepper, this.mt)))
        // checking filterOutEmpties() with a list with 1 InactiveWord empty ""
        && t.checkExpect(this.slei.filterOutEmpties(),
            new ConsLoWord(this.banana, new ConsLoWord(this.RICE, this.mt)))
        // checking filterOutEmpties() with a list with no empty ""
        && t.checkExpect(this.lmm.filterOutEmpties(), this.lmm)
        // checking filterOutEmpties() with a list of all empty "" that has both active
        // and inactive words
        && t.checkExpect(this.allEmpty.filterOutEmpties(), this.mt)
        // checking filterOutEmpties() with an empty list
        && t.checkExpect(this.mt.filterOutEmpties(), this.mt)
        // checking filterOutEmpties() with a list with 2 ActiveWord empty ""
        && t.checkExpect(this.aEmpty2.filterOutEmpties(),
            new ConsLoWord(this.babaco, new ConsLoWord(this.pear, this.mt)))
        // checking filterOutEmpties() with a list with 2 InactiveWord empty ""
        && t.checkExpect(this.iEmpty2.filterOutEmpties(),
            new ConsLoWord(this.beet, new ConsLoWord(this.broccoli, this.mt)));

  }

  // tests addToEnd
  boolean testAddToEnd(Tester t) {
    return
    // checking addToEnd(IWord w) with a list of ActiveWord where w is an ActiveWord
    t.checkExpect(this.la4.addToEnd(this.babaco),
        new ConsLoWord(this.orange,
            new ConsLoWord(this.apple,
                new ConsLoWord(this.pear,
                    new ConsLoWord(this.banana, new ConsLoWord(this.babaco, this.mt))))))
        // checking addToEnd(IWord w) with a list of ActiveWord where w is an
        // InactiveWord
        && t.checkExpect(this.la4.addToEnd(this.broccoli),
            new ConsLoWord(this.orange, new ConsLoWord(this.apple, new ConsLoWord(this.pear,
                new ConsLoWord(this.banana, new ConsLoWord(this.broccoli, this.mt))))))
        // checking addToEnd(IWord w) with a list of InactiveWord where w is an
        // ActiveWord
        && t.checkExpect(this.li4.addToEnd(this.orange),
            new ConsLoWord(this.carrot, new ConsLoWord(this.pea, new ConsLoWord(this.tomato,
                new ConsLoWord(this.pepper, new ConsLoWord(this.orange, this.mt))))))
        // checking addToEnd(IWord w) with a list of InactiveWord where w is an
        // InactiveWord
        && t.checkExpect(this.li4.addToEnd(this.beet),
            new ConsLoWord(this.carrot, new ConsLoWord(this.pea, new ConsLoWord(this.tomato,
                new ConsLoWord(this.pepper, new ConsLoWord(this.beet, this.mt))))))
        // checking addToEnd(IWord w) with a list of mixed words where w is an
        // ActiveWord
        && t.checkExpect(this.lau1.addToEnd(this.apple),
            new ConsLoWord(this.STEAK, new ConsLoWord(this.HAM,
                new ConsLoWord(this.CHICKEN, new ConsLoWord(this.apple, this.mt)))))
        // checking addToEnd(IWord w) with a list of mixed words where w is an
        // InactiveWord
        && t.checkExpect(this.lau1.addToEnd(this.carrot),
            new ConsLoWord(this.STEAK, new ConsLoWord(this.HAM,
                new ConsLoWord(this.CHICKEN, new ConsLoWord(this.carrot, this.mt)))))
        // checking addToEnd(IWord w) with an empty list where w is an ActiveWord
        && t.checkExpect(this.mt.addToEnd(this.CHICKEN), new ConsLoWord(this.CHICKEN, this.mt))
        // checking addToEnd(IWord w) with an empty list where w is an InactiveWord
        && t.checkExpect(this.mt.addToEnd(this.CORN), new ConsLoWord(this.CORN, this.mt))
        // checking addToEnd(IWord w) with a list where w is ""
        && t.checkExpect(this.mixedWords.addToEnd(this.naa),
            new ConsLoWord(this.pear,
                new ConsLoWord(this.pepper, new ConsLoWord(this.pea, new ConsLoWord(this.HAM,
                    new ConsLoWord(this.RICE, new ConsLoWord(this.naa, this.mt)))))));
  }

  // tests checkAndReduce
  boolean testCheckAndReduce(Tester t) {
    return t.checkExpect(this.sameLetter.checkAndReduce("p"),
        new ConsLoWord(new ActiveWord("epper", 300, 100),
            new ConsLoWord(new InactiveWord("", 0, 0),
                new ConsLoWord(this.pear, new ConsLoWord(this.orange, this.mt)))))
        && t.checkExpect(this.lea.checkAndReduce("a"),
            new ConsLoWord(new ActiveWord("fig", 200, 200),
                new ConsLoWord(new InactiveWord("pepper", 300, 100),
                    new ConsLoWord(new ActiveWord("", 0, 0), this.mt))))
        && t.checkExpect(this.lmm.checkAndReduce("a"),
            new ConsLoWord(new InactiveWord("carrot", 100, 400),
                new ConsLoWord(new ActiveWord("BABACO", 750, 800),
                    new ConsLoWord(new ActiveWord("apple", 300, 300),
                        new ConsLoWord(new InactiveWord("RICE", 500, 100), this.mt)))));
  }

  // tests isActive
  boolean testIsActive(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.apple.isActive(), true)
        // checks active and inactive
        && t.checkExpect(this.fig.isActive(), true)
        // checks two inactive
        && t.checkExpect(this.tomato.isActive(), false)
        // checks a ""
        && t.checkExpect(this.naa.isActive(), true)
        // checks words with mixed cases
        && t.checkExpect(this.HAM.isActive(), true);
  }

  // tests compare
  boolean testCompare(Tester t) {
    return
    // compares
    t.checkExpect(this.apple.compare(this.banana), false)
        // checks active and inactive
        && t.checkExpect(this.fig.compare(this.carrot), true)
        // checks two inactive
        && t.checkExpect(this.tomato.compare(this.pea), true)
        // checks a ""
        && t.checkExpect(this.naa.compare(this.RICE), false)
        // checks words with mixed cases
        && t.checkExpect(this.HAM.compare(this.pepper), false);
  }

  // tests compareHelper
  boolean testCompareHelper(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.apple.compareHelper("a"), false)
        // checks active and inactive
        && t.checkExpect(this.fig.compareHelper("i"), true)
        // checks two inactive
        && t.checkExpect(this.tomato.compareHelper("x"), true)
        // checks a ""
        && t.checkExpect(this.naa.compareHelper("a"), true)
        // checks words with mixed cases
        && t.checkExpect(this.HAM.compareHelper("m"), true);
  }

  // tests wordEnd
  boolean testWordInfo(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.apple.compareHelper("w"), true)
        // checks active and inactive
        && t.checkExpect(this.fig.compareHelper("a"), false)
        // checks two inactive
        && t.checkExpect(this.tomato.compareHelper("o"), false)
        // checks a ""
        && t.checkExpect(this.naa.compareHelper("a"), true)
        // checks words with mixed cases
        && t.checkExpect(this.HAM.compareHelper("h"), false);
  }

  // tests blankString
  boolean testBlankString(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.aEmpty.blankString(), true)
        // checks active and inactive
        && t.checkExpect(this.iaEmpty.blankString(), true)
        // checks two inactive
        && t.checkExpect(this.tomato.blankString(), false)
        // checks a ""
        && t.checkExpect(this.naa.blankString(), true)
        // checks words with mixed cases
        && t.checkExpect(this.HAM.blankString(), false);
  }

  // tests nullChar
  boolean testNullChar(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.aEmpty.nullChar("a"), false)
        // checks active and inactive
        && t.checkExpect(this.iaEmpty.nullChar(""), false)
        // checks two inactive
        && t.checkExpect(this.tomato.nullChar("o"), false)
        // checks a ""
        && t.checkExpect(this.naa.nullChar(""), false)
        // checks words with mixed cases
        && t.checkExpect(this.HAM.nullChar(""), false);
  }

  // tests createActove
  boolean testCreateActive(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.aEmpty.createActive(), new ActiveWord("", 300, 200))
        // checks active and inactive
        && t.checkExpect(this.apple.createActive(), new ActiveWord("apple", 300, 300))
        // checks two inactive
        && t.checkExpect(this.tomato.createActive(), new ActiveWord("omato", 200, 300))
        // checks a ""
        && t.checkExpect(this.naa.createActive(), new ActiveWord("", 0, 0))
        // checks words with mixed cases
        && t.checkExpect(this.HAM.createActive(), new ActiveWord("HAM", 300, 400));
  }

  // tests moveWord
  boolean testMoveWord(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.aEmpty.moveWord(8), new ActiveWord("", 300, 216))
        // checks active and inactive
        && t.checkExpect(this.apple.moveWord(2), new ActiveWord("apple", 300, 304))
        // checks two inactive
        && t.checkExpect(this.tomato.moveWord(1), new InactiveWord("tomato", 200, 302))
        // checks a ""
        && t.checkExpect(this.naa.moveWord(3), new ActiveWord("", 0, 6))
        // checks words with mixed cases
        && t.checkExpect(this.HAM.moveWord(3), new ActiveWord("HAM", 300, 406));
  }

  // tests belowScreen
  boolean testBelowScreen(Tester t) {
    return
    // checks two active words
    t.checkExpect(this.aEmpty.belowScreen(), false)
        // checks active and inactive
        && t.checkExpect(this.apple.belowScreen(), false)
        // checks two inactive
        && t.checkExpect(this.tomato.belowScreen(), false)
        // checks a ""
        && t.checkExpect(this.babaco.belowScreen(), false)
        // checks words with mixed cases
        && t.checkExpect(this.HAM.belowScreen(), false);
  }

  // testing onTick
  boolean testOnTick(Tester t) {
    return
    // checking when one word starts with the given string

    t.checkExpect(this.w1.onTick(),
        new ZTypeWorld(
            new ConsLoWord(new ActiveWord("orange", 200, 412),
                new ConsLoWord(new ActiveWord("apple", 300, 312),
                    new ConsLoWord(new ActiveWord("pear", 100, 212),
                        new ConsLoWord(new ActiveWord("banana", 400, 112),
                            new ConsLoWord(new InactiveWord("ryygfe", 481, 62), this.mt))))),
            new Random(), 300L, 6, 1))
        &&

        // checking when one word starts with the given string
        t.checkExpect(this.w2.onTick(),
            new ZTypeWorld(
                new ConsLoWord(new InactiveWord("carrot", 100, 416),
                    new ConsLoWord(new InactiveWord("pea", 100, 116),
                        new ConsLoWord(new InactiveWord("tomato", 200, 316),
                            new ConsLoWord(new InactiveWord("pepper", 300, 116), this.mt)))),
                new Random(), 550L, 8, 6))
        &&
        // checking when one word starts with the given string
        t.checkExpect(this.w3.onTick(),
            new ZTypeWorld(
                new ConsLoWord(new InactiveWord("pepper", 300, 112),
                    new ConsLoWord(new ActiveWord("pear", 100, 212),
                        new ConsLoWord(new ActiveWord("orange", 200, 412),
                            new ConsLoWord(new InactiveWord("fspvgw", 138, 62), this.mt)))),
                new Random(), 300L, 6, 1));
  }

  // tests big bangs
  boolean testBigBang(Tester t) {
    ZTypeWorld world = new ZTypeWorld(new Random(10));
    int worldWidth = ILoWord.WORLD_WIDTH;
    int worldHeight = ILoWord.WORLD_HEIGHT;
    double tickRate = .1;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }

}
