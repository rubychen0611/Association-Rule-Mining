package Test;
import Data.DataBase;
import Data.DataSet;
import Mining.Apriori;
import Mining.FP_Growth.FP_Growth;
import Mining.Miner;

import org.junit.Test;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
public class MiningTest
{
    public String getMemoryUseInfo(){
        MemoryUsage mu = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        long getCommitted = mu.getCommitted();
        long getInit = mu.getInit();
        long getUsed = mu.getUsed();
        long max = mu.getMax();
        return ">>getCommitted(MB)=>" + getCommitted / 1000 / 1000 + "\n"
                +">>getInit(MB)=" + getInit / 1000 / 1000 + "\n"
                +">>getUsed(MB)=" + getUsed / 1000 / 1000+ "\n"
                +">>max(MB)=" + max / 1000 / 1000 + "\n";
    }
    @Test
    public  void testApriori()
    {
        double min_sup = 0.2;     //最小支持度阈值
        double min_conf = 0.8;    //最小置信度阈值
       // System.out.println(getMemoryUseInfo());
        DataBase dataBase; dataBase = new DataBase(DataSet.UNIX_USAGE);      //数据库初始化
        Miner miner;
        miner = new FP_Growth(dataBase, min_sup, min_conf);
       //miner = new Apriori(dataBase, min_sup, min_conf);
        System.out.println(getMemoryUseInfo());
        miner.mine();
        System.out.println(getMemoryUseInfo());

    }
}
