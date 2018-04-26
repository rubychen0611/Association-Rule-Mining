package Data;

import Mining.ItemSet;

import java.util.ArrayList;
import java.util.TreeSet;

public class Transaction
{
    private ArrayList<Item> transaction;
    public Transaction()
    {
        transaction = new ArrayList<>();
    }
    public void addItem(Item item)
    {
        transaction.add(item);
    }
    public void output()
    {
        for(Item i: transaction)
            System.out.print(i.getName() + "|");
        System.out.println();
    }

    public ArrayList<Item> getTransaction()
    {
        return transaction;
    }
    public ItemSet toItemSet()
    {
        TreeSet<Item> treeSet = new TreeSet<>();
        treeSet.addAll(transaction);
        ItemSet itemSet = new ItemSet(treeSet);
        return itemSet;
    }
}
