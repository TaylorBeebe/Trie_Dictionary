/*Taylor Beebe
 * 11/5/2017
 */ 

import structure5.*;
import java.util.Iterator;

class LexiconNode implements Comparable<LexiconNode> {

    //contains the letter of this node
    private char letter;
    //says if this node is the end of a wordpath
    private boolean isWord;
    //data structure for children of this node
    private OrderedVector<LexiconNode> v = new OrderedVector<LexiconNode>();
    
    
    // Constructor
    LexiconNode(char letter, boolean isWord) {
	this.letter = letter;
	this.isWord = isWord;
    }
    
    //Compares two nodes
    public int compareTo(LexiconNode o) {
	if(o.equals(null)) return 0;
	return this.letter - o.getLetter();
    }
    
    //Return letter stored by this LexiconNode
    public char getLetter() {
	return letter;
    }
    
    //Add LexiconNode child to correct position in child data structure
    public void addChild(LexiconNode ln) {
	if(!ln.equals(null)) v.add(ln);
    }
    
    //Get LexiconNode child for 'ch' out of child data structure
    public LexiconNode getChild(char ch) {
	for(LexiconNode n : v){
	    if (n.getLetter() == ch) return n;
	}
	return null;
    }
    
    //Returns true if child is contained in children
    public boolean hasChild(char ch){
	for(LexiconNode n : v){
	    if (n.getLetter() == ch) return true;
	}
	return false;
    }
    
    //Remove LexiconNode child for 'ch' from child data structure
    public void removeChild(char ch) {
	for(LexiconNode n : v){
	    if (n.getLetter() == ch) v.remove(n);
	}
    }
    
    //returns an iterator for the children of this node
    public Iterator<LexiconNode> iterator() {
	return v.iterator();
    }
    //returns true if the children vector has children
    public boolean hasChildren(){return v.size() > 0;}
    
    //changes the isWord Flag
    protected boolean setIsWord(boolean b){
	this.isWord = b;
	return true;
    }
    
    //returns the value of isWord
    protected boolean isWord() {return isWord;}
}
