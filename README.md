# CS440: Project 3: Heuristic Search

- Andrew Wang
- Mauricio Alvarez

### Heuristic Functions

- Euclidean distance
- Manhattan distance
- Chebyshev distance
- Octile distance

##### Euclidean

```java
public class Node{
    
    ...
    
    private void euclidean(Node node){
        int row1 = this.row;
        int col1 = this.col;
        int row2 = node.getRow();
        int col2 = node.getCol();

        double aSquared = Math.pow((row1-row2), 2);
        double bSquared = Math.pow((col1-col2), 2);

        this.hVal =  (float)Math.sqrt(aSquared+bSquared);

    }
}
```

##### Manhattan
```java
public class Node{
    
    ...
    
    private void manhattan(Node node){
         int row1 = this.row;
         int col1 = this.col;
         int row2 = node.getRow();
         int col2 = node.getCol();
    
         int x = Math.abs(row1-row2);
         int y = Math.abs(col1-col2);
    
         this.hVal = x+y;
     }
}
```

##### Chebyshev/Octile
```java
public class Node{
    
    ...
    
    private void diagonal(Node node, int d){
        double cost;
        if(d == 2) {          //Chebyshev distance
            cost = 1;
        }
        else {                //Octile distance
            cost = Math.sqrt(2);
        }
        int row1 = this.row;
        int col1 = this.col;
        int row2 = node.getRow();
        int col2 = node.getCol();

        int x = Math.abs(row1-row2);
        int y = Math.abs(col1-col2);

        this.hVal = (float)((x+y) + ((cost - 2) * (Math.min(x,y))));
    }
}

```

