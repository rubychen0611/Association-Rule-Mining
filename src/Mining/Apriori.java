package Mining;

import Data.DataBase;
import Data.Item;
import Data.Transaction;

import java.util.*;

public class Apriori implements Miner
{
    private static ArrayList<Transaction> DB;
    private static double min_sup;
    public Apriori(DataBase DataBase, double min_sup)
    {
        this.DB = DataBase.getDB();
        this.min_sup = min_sup;
    }
    Set<ItemSet> find_frequent_1_itemSets()
    {
       TreeMap<ItemSet,Integer> map = new TreeMap<>();
        for(Transaction t : DB)
        {
            for(Item item: t.getTransaction())
            {
                ItemSet itemSet = new ItemSet();
                itemSet.addItem(item);
                if(map.containsKey(itemSet))
                {
                    int value = map.get(itemSet);
                    map.put(itemSet, value + 1);
                }
                else
                    map.put(itemSet, 1);
            }
        }
        Iterator<Map.Entry<ItemSet,Integer>> it = map.entrySet().iterator(); it.hasNext();
        while(it.hasNext())
        {
            Map.Entry<ItemSet,Integer> entry=it.next();
            if(entry.getValue() < (int)DB.size() * min_sup)
               it.remove();
            else
                System.out.println(entry.getKey() + " " + entry.getValue());
        }

        return map.keySet();
    }
    @Override
    public void mine()
    {
        Set<ItemSet> L1 = find_frequent_1_itemSets();
    }

    @Override
    public void outputFrequentItemsets()
    {

    }
}
