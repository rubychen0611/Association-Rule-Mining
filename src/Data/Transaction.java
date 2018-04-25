package Data;

import java.util.ArrayList;

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
}
