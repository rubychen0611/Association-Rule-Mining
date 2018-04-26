package Mining;

import Data.DataBase;
import Data.Transaction;

import java.util.ArrayList;
import java.util.TreeMap;

public class FP_Growth implements Miner
{
    private static ArrayList<Transaction> DB;
    private static double min_sup;
    private static double min_conf;

    public FP_Growth(DataBase DataBase, double min_sup, double min_conf)
    {
        this.DB = DataBase.getDB();
        this.min_sup = min_sup;
        this.min_conf = min_conf;
    }

    @Override
    public void mine()
    {

    }

    @Override
    public void outputFrequentItemSets(TreeMap<ItemSet, Integer> map, int k)
    {

    }

    @Override
    public void outputStrongAssociationRules(TreeMap<ItemSet, Integer> map, int k)
    {

    }
}
