package Mining.FP_Growth;

import Data.Item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class CPB                    //条件模式基
{
    private LinkedList<Item> cpb;
    public CPB()
    {
        cpb = new LinkedList<>();
    }
    public CPB(ArrayList<Item> array)
    {
        cpb = new LinkedList<>(array);
    }
    public void addItem(Item item)
    {
        cpb.addFirst(item);
    }                   //头插

    public LinkedList<Item> getList()
    {
        return cpb;
    }
    public void sortBySupportCount(TreeMap<Item, Integer> freqSet)
    {
        cpb.sort((Item i1, Item i2) -> (freqSet.get(i2) - freqSet.get(i1)));      //逆序
    }

    @Override
    public String toString()
    {
        String str = "";
        for(Item item : cpb)
        {
            str += item.toString();
            if(item != cpb.getLast())
            str += ",";
        }
        return str;
    }
}
