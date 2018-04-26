package Mining;

import java.util.TreeMap;

public interface Miner
{
    void mine();                    //挖掘
    void outputFrequentItemSets(TreeMap<ItemSet, Integer> map, int k);  //输出k频繁项集
}
