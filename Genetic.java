import java.util.Random;
import java.util.Arrays;

/**
 * A class which demonstrates a version of the text genetic algorithm demonstrated in class
 * @author Spencer Yoder
 */
public class Genetic {
  /** The string we're trying to evolve into */
  private static final String GOAL = "ILoveBeingTheTAForArtificialIntelligenceTwo";
  /** Print the best candidate from every xth generation */
  private static final int PRINT_FREQUENCY = 1;
  /** The number of children in each generation */
  private static final int GENERATION_SIZE = 1200;
  /** 1 every x letters will be a mutation */
  private static final int MUTUATION_RARITY = 100;
  
  /**
   * The main method, starts the program
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    Word[] candidates = new Word[GENERATION_SIZE];
    Random r = new Random();
    for(int i = 0; i < candidates.length; i++) {
      String s = "";
      for(int j = 0; j < GOAL.length(); j++) {
        if(r.nextInt(2) == 0) {
          s += (char) ('a' + r.nextInt(26));
        } else {
          s += (char) ('A' + r.nextInt(26));
        }
      }
      candidates[i] = new Word(s);
    }
    Arrays.sort(candidates);
    int idx = 0;
    while(!candidates[0].s.equals(GOAL)) {
      Word parent1 = candidates[0];
      Word parent2 = candidates[1];
      for(int i = 0; i < candidates.length; i++) {
        String s = "";
        for(int j = 0; j < GOAL.length(); j++) {
          if(r.nextInt(MUTUATION_RARITY) == 0) {
            if(r.nextInt(2) == 0) {
              s += (char) ('a' + r.nextInt(26));
            } else {
              s += (char) ('A' + r.nextInt(26));
            }
          } else
            s += candidates[r.nextInt(2)].s.charAt(j);
        }
        candidates[i] = new Word(s);
      }
      Arrays.sort(candidates);
      if(idx++ % PRINT_FREQUENCY == 0)
        System.out.println(candidates[0]);
    }
    System.out.println(candidates[0]);
  }
  
  /**
   * A word (solution to the algorithm)
   * @author Spencer Yoder
   */
  private static class Word implements Comparable<Word> {
    /** The String stored in this word */
    private String s;
    /** The evolutionary score for this word */
    private int score;
    
    /**
     * Creates a new word from the given string
     */
    private Word(String s) {
      this.s = s;
      score = 0;
      for(int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        char g = GOAL.charAt(i);
        if(sameCase(c, g))
          score++;
        if(Character.toLowerCase(c) == Character.toLowerCase(g))
          score++;
      }
    }
    
    /**
     * @return true if both characters have the same case
     */
    private boolean sameCase(char c, char d) {
      return Character.isUpperCase(c) && Character.isUpperCase(d) || Character.isLowerCase(c) && Character.isLowerCase(d);
    }
    
    /**
     * Helps to sort words by score
     */
    @Override
    public int compareTo(Word other) {
      return other.score - score;
    }
    
    /**
     * Helps with printing words
     */
    @Override
    public String toString() {
      return s;
    }
  }
}