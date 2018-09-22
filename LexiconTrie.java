/*Taylor Beebe
 * 11/5/2017
 */

import structure5.*;
import java.util.Iterator;
import java.util.Scanner;

public class LexiconTrie implements Lexicon {
  //Seed node
  private LexiconNode start = new LexiconNode('#', false);
  //Keeps track of word count
  private int counter = 0;
  
  /*Adds words to lexicon
   * @Pre: String is not null
   * @Post returns true if word was successfully added
   */ 
  public boolean addWord(String word) {
      if(word.equals(null)) return false;
      word = word.toLowerCase();
      LexiconNode c = start;
      char ch;
      for (int x = 0; x < word.length(); x++){
        ch = word.charAt(x);
        if (c.hasChild(ch) == false) c.addChild(new LexiconNode(ch, false));
        c = c.getChild(ch);
      }
      if (c.isWord() == true) return false;
      c.setIsWord(true);
      counter++;
      return true; }
    
    /*Adds words from an input file if words are seperated by newlines
     * @Pre: valid filename
     * @Post: Returns number of words added
     */
    public int addWordsFromFile(String filename) {
      int count = 0;
      try{
        Scanner in = new Scanner(new FileStream(filename));
        String s;
        while(in.hasNextLine()){
          s = in.nextLine();
          if (addWord(s)) count++;
        } in.close();
      } catch (Exception e){
          System.out.println("Invalid Filename. Couldn't add words to lexicon");
        } return count; }
    
    /*Removes word from lexicon
     * @Pre word is not null
     * @Returns true if word was removed from lexicion
     */ 
    public boolean removeWord(String word) {
      if(word.equals(null)) return false;
      word = word.toLowerCase();
      if(!containsWord(word)) return false;
      StackList<LexiconNode> s = stackify(word);
      s.get().setIsWord(false);
      counter--;
      int size = s.size();
      for(int x = 1; x < size; x++){
        if(!s.get().isWord() && !s.get().hasChildren()){
          char l = s.pop().getLetter();
          s.get().removeChild(l);
        } else{break;} }
      return true; }
    
    //Creates a stack containing nodes leading up to a word in lexicon
    private StackList<LexiconNode> stackify(String word){
      StackList<LexiconNode> s = new StackList<LexiconNode>();
      LexiconNode c = start;
      s.push(c);
      for(int x = 0; x < word.length(); x++){
        c = c.getChild(word.charAt(x));
        s.push(c);
      } return s; }
    
    //Returns the number of words in the lexicon
    public int numWords() {return counter;}
    
    /*Checks if word is in the lexicon
     * @Pre: word is not null
     * @Post: returns true if word is in the lexicon
     */ 
    public boolean containsWord(String word){
      if(word.equals(null)) return false;
      word = word.toLowerCase();
      LexiconNode c = start;
      char letter;
      for (int x = 0; x < word.length(); x++){
        letter = word.charAt(x);
        if (c.hasChild(letter) == false) return false;
        c = c.getChild(letter);
      }
      if (c.isWord()) return true;
      return false; }

    /*Checks if prefix is in the lexicon
     * @Pre: word is not null
     * @Post: returns true if prefix is in the lexicon, even if the prefix is a word or if it has no children
     */ 
    public boolean containsPrefix(String prefix){
      prefix = prefix.toLowerCase();
      LexiconNode c = start;
      for (int x = 0; x < prefix.length(); x++){
        if (c.hasChild(prefix.charAt(x)) == false) return false;
        c = c.getChild(prefix.charAt(x));
      } return true; }
    
    //Returns an iterator that iterates through a vector containing all the words within the lexicon
    public Iterator<String> iterator() {
      Vector<String> words = new Vector<String>(counter);
      iteratorHelper(new String(""), start, words);
      return words.iterator(); }
    
    //Helper function for iterator
    private void iteratorHelper(String s, LexiconNode n, Vector<String> words){
      if(!n.equals(start)) s += n.getLetter();
      if (n.isWord()) words.add(s);
      if(n.hasChildren()){
        Iterator<LexiconNode> i = n.iterator();
        while(i.hasNext()){
          iteratorHelper(s, i.next(), words); } } }
    
    /*Creates a set containing possible corrections with the given parameters
     * @Pre: target is not null, maxDistance is not zero
     * @Post: returns a set containing possible corrections
     */
    public Set<String> suggestCorrections(String target, int maxDistance) {
      Set<String> corrections = new SetList<String>();
      if (target.equals(null) || maxDistance == 0) return corrections;
      target = target.toLowerCase();
      LexiconNode n;
      Iterator<LexiconNode> i = start.iterator();
      while(i.hasNext()){
        n = i.next();
        suggestCorrectionsHelper(target, new String("" + n.getLetter()), maxDistance, n, corrections);
      } return corrections; }
    
    //Helper for corrections suggestion
    private void suggestCorrectionsHelper(String target, String running, int maxDistance, LexiconNode n, Set<String> corrections) {
      if(n.isWord() && running.length() == target.length() && maxDistance > 0) corrections.add(running);
      if(running.length() < target.length() && maxDistance >= 0){
         Iterator<LexiconNode> it = n.iterator();
         while(it.hasNext()){
           n = it.next();
           if(n.getLetter() == target.charAt(running.length())) suggestCorrectionsHelper(target, running + n.getLetter(), maxDistance, n, corrections);
           else suggestCorrectionsHelper(target, running + n.getLetter(), maxDistance - 1, n, corrections);
         } } }
    
    /*Creates a set containing strings that match a given regular expression
     * @Pre:Pattern is not null
     * @Post: Returns a set containing strings that match a regular expression
     */ 
    public Set<String> matchRegex(String pattern){
      Set<String> regex = new SetList<String>();
      if(pattern.equals(null)) return regex;
      pattern = pattern.toLowerCase();
      matchRegexHelper(pattern, new String(""), start, regex);
      return regex;
    }
   
    //Helper function for regular expression interpreter
    private void matchRegexHelper(String expression, String running, LexiconNode n, Set<String> regex){
      //Base case: if the expression is an empty string and the running string is a word
      if ((expression.length() == 0 && n.isWord())) regex.add(running);
      //Make sure the expression isn't empty and the node has children
      if(expression.length() > 0 && n.hasChildren()){
        //Get the character at index zero (it makes the code easier to read)
        char l = expression.charAt(0);
        //See if  the first character is a ? or *
        if (l == '?' || l == '*'){
          //create an iterator for the children of this node
          Iterator<LexiconNode> i = n.iterator();
          //for both ? and *, remove the first letter of the expression and don't advance the node
          matchRegexHelper(expression.substring(1), running, n, regex);
          while (i.hasNext()){
            n = i.next();
            //if it is a *, dont change the expression, add this node's letter to the running string, and call itself with new node
            if (l == '*') matchRegexHelper(expression, running + n.getLetter(), n, regex);
            //if it is a ?, remove the first letter of the expression, add this node's letter to the running string, and call itself with new node
            else matchRegexHelper(expression.substring(1), running + n.getLetter(), n, regex);
            //otherwise, it must be a letter/other character so see if the child is in this node's children. If not, it is a dead end
          } } else if (n.hasChild(l)) matchRegexHelper(expression.substring(1), running + n.getChild(l).getLetter(), n.getChild(l), regex);
        } }
}
