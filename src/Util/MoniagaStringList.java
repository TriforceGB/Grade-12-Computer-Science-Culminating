package Util;

// this class was created to allow a list functionality without having to do all the work in claases needed for it
// lists that are default in java work, but no lists allowed so I made my own
// no help from Ai was used for this and was coded by Michael
public class MoniagaStringList {
    // store the items in the array
    private String[] items;
    // store a count to the number of items for quicker access
    private int count;

    // overloaded constructors for creation on empty
    public MoniagaStringList() {
        items = new String[0];
        count = 0;
    }

    // or creation with data
    public MoniagaStringList(String[] startVals) {
        for (int i = 0; i < startVals.length; i++) {
            add(startVals[i]);
        }
    }

    // creates the .count() method seen on lists
    public int count() {
        return count;
    }

    // creates the add function
    // when adding a string it ensures that by forcing a new string to be created, it isn't passed by reference and simply value
    public void add(String toAdd) {
        int newCount = count + 1;
        String[] newItems = new String[newCount];
        for (int i = 0; i < count; i++) {
            // this allows that to happen (pass by val)
            newItems[i] = new String(items[i]);
        }
        newItems[count] = toAdd;
        items = newItems;
        count = newCount;
    }

    // resize the array to fit evertything properly
    public void removeWhen(String match) {
        int numOfNull = 0;
        for (int i = 0; i < count; i++) {
            if (items[i].equals(match)) {
                numOfNull++;
                items[i] = null;
            }
        }
        int newCount = count - numOfNull;
        String[] newItems = new String[newCount];
        int addIndex = 0;
        for (int i = 0; i < count; i++) {
            if (items[i] != null) {
                newItems[addIndex] = items[i];
                addIndex++;
            }
        }
        items = newItems;
        count = newCount;
    }

    // get the posistion andd return null if exceeds limits
    public String getAt(int index) {
        if (index < 0 || index >= count)
            return null;
        else
            return items[index];
    }

    // check if a certain string exists within the array
    public boolean exists(String check) {
        boolean result = false;
        for (int i = 0; i < count; i++) {
            if (items[i].equals(check)) {
                result = true;
                break;
            }
        }
        return result;
    }
}