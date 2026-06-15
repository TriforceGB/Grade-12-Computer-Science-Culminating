package Util;
public class MoniagaStringList {
    private String[] items;
    private int count;

    public MoniagaStringList() {
        items = new String[0];
        count = 0;
    }


    // TODO check if used, else remove
    public MoniagaStringList(String[] startVals) {
        for (int i = 0; i < startVals.length; i++) {
            add(startVals[i]);
        }
    }

    public int count() {
        return count;
    }

    public void add(String toAdd) {
        int newCount = count + 1;
        String[] newItems = new String[newCount];
        for (int i = 0; i < count; i++) {
            newItems[i] = new String(items[i]);
        }
        newItems[count] = toAdd;
        items = newItems;
        count = newCount;
    }

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

    public String getAt(int index) {
        if (index < 0 || index >= count)
            return null;
        else
            return items[index];
    }
}