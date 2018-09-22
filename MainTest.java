import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.FileWriter;
class MainTest{
  
  public static void main(String[] args){
    LexiconTrie l = new LexiconTrie();
    l.addWordsFromFile("ospd2.txt");
    Iterator<String> i = l.iterator();
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("OUTPUT.txt"))){
      while (i.hasNext()){
        bw.write(i.next());
        bw.newLine();
      }
      } catch (Exception e){}
  }
  
}