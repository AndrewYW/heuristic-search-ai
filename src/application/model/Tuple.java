package application.model;

/**
 * Tuple class to represent coordinates.
 * @author Andrew Wang
 * @author Mauricio Alvarez
 */
public class Tuple{
    /**
     * Row/Y coordinate of tuple.
     */
    private int row;

    /**
     * Column/X coordinate of tuple.
     */
    private int col;

    /**
     * Border start position of a tuple.
     */
    private int border;

    /**
     * Default constructor for Tuples.
     * @param row The row position of the coordinate
     * @param col The col position of the coordinate
     */
    public Tuple(int row, int col){
        this.row = row;
        this.col = col;
        this.border = 0;
    }

    /**
     * Constructor overload for the specific highway case. Only used for the start cell of a highway path.
     * In highways, the direction that a cell can traverse is important, so it is stored in this tuple.
     * Border values are used to represent 1: top border, 2: bottom border, 3: left border, 4: right border
     * @param row The row position of the coordinate
     * @param col The column position of the coordinate
     * @param border The border that this tuple begins on.
     */
    public Tuple(int row, int col, int border){
        this(row, col);
        this.border = border;
    }

    /**
     * Method to get the row coordinate of a tuple.
     * @return The row coordinate as an integer.
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Method to get the column coordinate of a tuple.
     * @return The column coordinate as an integer.
     */
    public int getCol(){
        return this.col;
    }

    /**
     * Method to get the border of a start cell.
     * @return 1 if top, 2 if bottom, 3 if left, 4 if right.
     */
    public int getBorder() { return this.border; }

    /**
     * Method override for use in making highways. Will compare coordinates of this and other tuple for equality.
     * @param t The tuple to be compared to
     * @return  True if Tuples are on the same coordinates.
     */
    @Override
    public boolean equals(Object t){
        if(t == this)
            return true;
        if(!(t instanceof Tuple))
            return false;

        Tuple tuple = (Tuple) t;
        if((this.getRow() == tuple.getRow()) && (this.getCol() == tuple.getCol()))
            return true;
        return false;
    }
}
