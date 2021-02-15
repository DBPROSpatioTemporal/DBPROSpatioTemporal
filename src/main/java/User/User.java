package User;

/**
 * This class is made for the user that uses the Application.
 * It stores all the necessary information from the user for the program
 * to be able to make a good Weather Evaluation at a specific station
 * and also take into consideration if the user uses a wheelchair
 */
public class User {
    private int age;
    private boolean hasLuggage;
    private boolean hasWheelchair;
    private boolean hasStroller;
    private boolean hasChildren;


    public User(int age, boolean hasLuggage, boolean hasWheelchair,
                boolean hasStroller, boolean hasChildren) {
        this.age = age;
        this.hasLuggage = hasLuggage;
        this.hasWheelchair = hasWheelchair;
        this.hasStroller = hasStroller;
        this.hasChildren = hasChildren;
    }

    /**
     * Default Constructor
     */
    public User() { }


    /**
     * SETTERS AND GETTERS
     */
    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public boolean hasLuggage() {
        return hasLuggage;
    }

    public void setHasLuggage(boolean hasLuggage) {
        this.hasLuggage = hasLuggage;
    }

    public boolean hasWheelchair() {
        return hasWheelchair;
    }

    public void setHasWheelchair(boolean hasWheelchair) {
        this.hasWheelchair = hasWheelchair;
    }

    public boolean hasStroller() {
        return hasStroller;
    }

    public void setHasStroller(boolean hasStroller) {
        this.hasStroller = hasStroller;
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
    /**
     * for debug purposes
     * @return 
     */
    public String toString() {
    	String s = "User age: " + this.age +"\n"+
    				"has Luggage: " + this.hasLuggage+"\n"+
    				"Has Stroller: " + this.hasStroller +"\n"+
    				"Has Children: " + this.hasChildren +"\n"+
    				"Has Wheelchair:" + this.hasWheelchair;
		return s;
    	
    }
}
