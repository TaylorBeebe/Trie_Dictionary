import structure5.*;

public class NodeVector<LexiconNode> extends Vector<LexiconNode>{

    public NodeVector(int initialCapacity){
      super();
    }
    public void sort(){
      int n = this.size();
 
      for (int i = 0; i < n-1; i++)
        {
        // Find the minimum element in unsorted array
        int minIndex = i;
        for (int j = i+1; j < n; j++)
          if (this.get(minIndex).compareTo(this.get(j)) >= 1){
          minIndex = j;
        }
        // Swap the found minimum element with the first
        // element
        E temp = this.get(minIndex);
        this.setElementAt(this.get(i),minIndex);
        this.setElementAt(temp, i);
      }
      
    }

}
