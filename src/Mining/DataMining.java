package Mining;
import Data.DataBase;
import Data.DataSet;

enum MiningMethod{APRIORI, FPGROWTH, BASELINE};
public class DataMining
{
    private static double min_sup = 0.02;     //最小支持度阈值
    private static double min_conf = 0.5;    //最小置信度阈值
    public static DataSet dataSet = DataSet.GROCERYSTORE;     //挖掘内容：GroceryStore还是UNIX_usage
    public static MiningMethod method = MiningMethod.BASELINE;   //挖掘方法：Apriori，fp-growth，还是baseline
    private static DataBase dataBase;
    public static void main(String []args)
    {
        try{
            char read = (char) System.in.read();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        dataBase = new DataBase(DataSet.GROCERYSTORE);      //数据库初始化
        //dataBase.outputDataBase();                          //for debug
        Miner miner;
        switch (method)
        {
            case APRIORI: miner = new Apriori(dataBase, min_sup, min_conf); break;
            case FPGROWTH:  miner = new FP_Growth(dataBase, min_sup, min_conf);break;
            case BASELINE:  miner = new BaseLine(dataBase, min_sup, min_conf);break;
            default: miner = new Apriori(dataBase, min_sup, min_conf); break;
        }
        //miner = new Apriori(dataBase, min_sup, min_conf);
        miner.mine();
      //  miner.outputFrequentItemsets();
    }
}
