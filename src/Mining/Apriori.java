package Mining;

import Data.DataBase;
import Data.Item;
import Data.Transaction;
import sun.reflect.generics.tree.Tree;

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
   TreeSet<ItemSet> find_frequent_1_itemSets()
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
        deleteInfrequentItemSets(map);
        outputFrequentItemSets(map, 1);
        TreeSet<ItemSet> newset = new TreeSet<>();
        newset.addAll(map.keySet());
        return newset;
    }

    /*input:Lk-1: k-1频繁项集*/
    /*output:Ck : k候选集合*/
   private TreeSet<ItemSet> aproiri_gen(TreeSet<ItemSet> L)
    {
        TreeSet<ItemSet> Ck = new TreeSet<>();
        for(ItemSet l1 : L)
        {
            for(ItemSet l2: L)
            {
                if(l1 == l2) continue;
                if(l1.canJoin(l2))
                {
                    ItemSet c = l1.join(l2);                    //连接
                    if(!c.has_infrequent_subset(L))             //剪枝
                        Ck.add(c);
                }
            }
        }
        return Ck;
    }
    private void deleteInfrequentItemSets(TreeMap<ItemSet, Integer> mapK)
    {
        Iterator<Map.Entry<ItemSet,Integer>> it = mapK.entrySet().iterator(); it.hasNext();
        while(it.hasNext())
        {
            Map.Entry<ItemSet,Integer> entry=it.next();
            if(entry.getValue() < (int)DB.size() * min_sup)
                it.remove();
        //    else
             //   System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    @Override
    public void mine()
    {
        TreeSet<ItemSet> L = find_frequent_1_itemSets();
        for(int k = 2; !L.isEmpty(); k++)
        {
            TreeSet<ItemSet> Ck = aproiri_gen(L);
            TreeMap<ItemSet, Integer> mapK = new TreeMap<>();
            for(ItemSet set: Ck)
            {
                mapK.put(set, 0);
            }
            for(Transaction t: DB)
            {
                for(ItemSet set: Ck)
                {
                    if(t.getTransaction().containsAll(set.getItemSet()))
                    {
                        int value = mapK.get(set);
                        mapK.put(set, value + 1);
                    }
                }
                /*ArrayList<ItemSet> Ct = t.getSubSets(Ck);
                for(ItemSet c: Ct)
                {
                    int value = mapK.get(c);
                    mapK.put(c, value + 1);
                }*/
            }
            deleteInfrequentItemSets(mapK);
            L = new TreeSet<>();
            L.addAll(mapK.keySet());
            outputFrequentItemSets(mapK, k);
        }
    }

    @Override
    public void outputFrequentItemSets(TreeMap<ItemSet, Integer> map, int k)
    {
        System.out.println(k + "频繁项集：");
        Iterator<Map.Entry<ItemSet,Integer>> it = map.entrySet().iterator(); it.hasNext();
        while(it.hasNext())
        {
            Map.Entry<ItemSet,Integer> entry=it.next();
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
