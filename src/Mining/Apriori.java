package Mining;

import Data.DataBase;

public class Apriori implements Miner
{
    private static DataBase DB;
    public  Apriori(DataBase DB)
    {
        this.DB = DB;
    }

    @Override
    public void mine()
    {

    }

    @Override
    public void outputFrequentItemsets()
    {

    }
}
