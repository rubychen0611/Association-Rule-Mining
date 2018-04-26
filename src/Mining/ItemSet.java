package Mining;

import Data.Item;
import com.sun.org.apache.xpath.internal.operations.Equals;

import java.util.Iterator;
import java.util.TreeSet;

public class ItemSet implements Comparable<ItemSet>
{
    private TreeSet<Item> itemSet;

    public ItemSet()
    {
        itemSet = new TreeSet<Item>();
    }
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
        String str = "";
        for(Item item : itemSet)
            str += (item.toString() + "|");
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
}
