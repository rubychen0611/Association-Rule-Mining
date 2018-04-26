package Mining;

import Data.DataBase;
import Data.Item;
import Data.Transaction;

import java.util.*;

public class BaseLine implements Miner
{
    private static ArrayList<Transaction> DB;
    private static double min_sup;
    private static double min_conf;
    private TreeMap<ItemSet, Integer> freqSets;
    public BaseLine(DataBase DataBase, double min_sup, double min_conf)
    {
        this.DB = DataBase.getDB();
        this.min_sup = min_sup;
        this.min_conf = min_conf;
        freqSets = new TreeMap<>();
    }
    private int support_count(ItemSet set)
    {
        int count = 0;
        for(Transaction t: DB)
        {
            if(t.getTransaction().containsAll(set.getItemSet()))
                count ++;
        }
        return count;
    }
    @Override
    public void mine()
    {
        for(Transaction t: DB)
        {
            ItemSet tSet = t.toItemSet();
            TreeSet<ItemSet> subsets = tSet.getSubSets();
            for(ItemSet subset: subsets)
            {
               /* if(subset.getSize() == 1) continue;
                if(freqSets.containsKey(subset)) continue;
                //int count = support_count(subset);
                //if(count >= (int)DB.size() * min_sup)
                //{
                    freqSets.put(subset, count);
                    System.out.println(subset + " " + count);
                }*/
                if(!freqSets.containsKey(subset))
                {
                    freqSets.put(subset, 1);
                }
                else
                {
                    int value = freqSets.get(subset);
                    freqSets.put(subset, value + 1);
                }
            }
        }
        Iterator<Map.Entry<ItemSet,Integer>> it = freqSets.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<ItemSet,Integer> entry=it.next();
            if(entry.getValue() < (int)DB.size() * min_sup)
                it.remove();
        }
        outputFrequentItemSets(freqSets, 0);        //k在此处没用上
    }

    @Override
    public void outputFrequentItemSets(TreeMap<ItemSet, Integer> map, int k)
    {
        Iterator<Map.Entry<ItemSet,Integer>> it = freqSets.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<ItemSet,Integer> entry=it.next();
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    @Override
    public void outputStrongAssociationRules(TreeMap<ItemSet, Integer> map, int k)
    {

    }
}
