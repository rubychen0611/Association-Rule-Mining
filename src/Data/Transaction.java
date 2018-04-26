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
    public ArrayList<ItemSet> getSubSets( TreeSet<ItemSet> Ck)
    {
        int N = (int)Math.pow(2, transaction.size());
        ArrayList<ItemSet> Ct = new ArrayList<>();
        for(int i = 0; i < N; i++)
        {
            int k = 0, m = i;
            ItemSet subset = new ItemSet();
            while(m > 0)
            {
                if((m & 1) == 1)
                {
                    subset.addItem(this.transaction.get(k));
                }
                m /= 2;
                k++;
            }
            if(!subset.isEmpty() && Ck.contains(subset))
                Ct.add(subset);
        }
        return Ct;
    }
}
