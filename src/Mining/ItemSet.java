package Mining;

import Data.Item;
import com.sun.org.apache.xpath.internal.operations.Equals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class ItemSet implements Comparable<ItemSet>
{
    private TreeSet<Item> itemSet;

    public ItemSet()
    {
        itemSet = new TreeSet<Item>();
    }
    public ItemSet(TreeSet<Item> itemSet){this.itemSet = itemSet;}
    public void addItem(Item item)
    {
        itemSet.add(item);
    }

    @Override
    public int compareTo(ItemSet o)
    {
        Iterator<Item> it1 = itemSet.iterator(), it2 = o.itemSet.iterator();
         while(it1.hasNext() && it2.hasNext())
         {
             Item item1 = it1.next(), item2 = it2.next();
             if(!(item1.equals(item2)))
             {
                 return item1.compareTo(item2);
             }
         }
         if(it1.hasNext())
             return 1;
         if(it2.hasNext())
             return -1;
         return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        Iterator<Item> it1 = itemSet.iterator(), it2 = ((ItemSet)obj).itemSet.iterator();
        while(it1.hasNext() && it2.hasNext())
        {
            Item item1 = it1.next(), item2 = it2.next();
            if(!(item1.equals(item2)))
            {
                return false;
            }
        }
        if(it1.hasNext() || it2.hasNext())
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        String str = "{";
        for(Item item : itemSet)
        {
            str += item.toString();
            if(item == itemSet.last())
                str += "}";
            else str += ",";
        }
        return str;
    }

   public boolean canJoin(ItemSet o)
   {
       Iterator<Item> it1 = itemSet.iterator(), it2 = o.itemSet.iterator();
       while(it1.hasNext() && it2.hasNext())
       {
           Item item1 = it1.next(), item2 = it2.next();
           if(item1 == itemSet.last())
           {
               if(item1.compareTo(item2) >= 0)
                   return false;
               else continue;
           }
           if(!item1.equals(item2))
               return false;
       }
       return true;
   }
   public ItemSet join(ItemSet o)
   {
        ItemSet newSet = new ItemSet();
        newSet.itemSet.addAll(this.itemSet);
        newSet.addItem(o.itemSet.last());
        return newSet;
   }
   public boolean has_infrequent_subset(TreeSet<ItemSet> L)
   {
        for(Item t: itemSet)
        {
            ItemSet subSet = new ItemSet();
            subSet.itemSet.addAll(this.itemSet);
            subSet.itemSet.remove(t);
            if(!L.contains(subSet))
                return true;
        }
        return false;
   }
    public boolean isEmpty()
    {
        return itemSet.isEmpty();
    }

    public TreeSet<Item> getItemSet()
    {
        return itemSet;
    }
    public TreeSet<ItemSet> getSubSets()
    {
        int N = (int)Math.pow(2, this.itemSet.size());
        ArrayList<Item> copyset = new ArrayList<>();
        copyset.addAll(this.itemSet);
        TreeSet<ItemSet> subsets = new TreeSet<>();
        for(int i = 0; i < N-1; i++)
        {
            int k = 0, m = i;
            ItemSet subset = new ItemSet();
            while(m > 0)
            {
                if((m & 1) == 1)
                {
                    subset.addItem(copyset.get(k));
                }
                m >>= 1;
                k++;
            }
            if(!subset.isEmpty())
                subsets.add(subset);
        }
        return subsets;
    }
    public int getSize()
    {
        return this.itemSet.size();
    }
}
