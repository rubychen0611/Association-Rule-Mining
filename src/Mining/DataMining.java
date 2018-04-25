package Mining;
import Data.DataBase;
import Data.DataSet;

enum MiningMethod{APRIORI, FPGROWTH, BASELINE};
public class DataMining
{
    private static double min_sup = 0.02;     //最小支持度阈值
    private static double min_conf = 0.7;    //最小置信度阈值
    public static DataSet dataSet = DataSet.GROCERYSTORE;     //挖掘内容：GroceryStore还是UNIX_usage
    public static MiningMethod method = MiningMethod.APRIORI;   //挖掘方法：Apriori，fp-growth，还是baseline
    private static DataBase dataBase;
    public static void main(String []args)
    {
        dataBase = new DataBase(DataSet.GROCERYSTORE);      //数据库初始化
        //dataBase.outputDataBase();                          //for debug
        Miner miner;
       // switch (method)
       // {
            //case APRIORI: miner = new Apriori(dataBase); break;
           // case FPGROWTH: break;
           // case BASELINE: break;
      //  }
        miner = new Apriori(dataBase);
        miner.mine();
        miner.outputFrequentItemsets();
    }
}
