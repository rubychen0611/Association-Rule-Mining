package Data;

import java.io.*;
import java.util.ArrayList;

public class DataBase
{
    private ArrayList<Transaction> DB;
    public DataBase(DataSet dataSet)
    {
        DB = new ArrayList<>();
        if (dataSet == DataSet.GROCERYSTORE)
        {
            readGroceryData();
        }
    }
    private void addTransation(Transaction transaction)
    {
        DB.add(transaction);
    }
    private void readGroceryData()
    {
      File file = new File("dataset\\GroceryStore\\Groceries.txt");
        try {
                if (file.exists())
                {
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null)                  //读一行文件
                    {
                        int i1 = line.indexOf('{'), i2 = line.indexOf("}");
                        if (i1 == -1 || i2 == -1) continue;
                        String substr = line.substring(i1 + 1, i2 );
                        String[] itemstrs = substr.split(",");
                        Transaction transaction = new Transaction();
                        for (int i = 0; i < itemstrs.length; i++)
                        {
                            Item item = new Item(itemstrs[i]);
                            transaction.addItem(item);
                        }
                       this.addTransation(transaction);
                    }
                    read.close();
                    System.out.println("文件读取成功! 读入事务共" + DB.size() + "条。");
                }else System.out.println("文件不存在！");
             } catch (IOException ioe)
            {
                    System.out.println("文件读取失败！");
            }
    }
    public void outputDataBase()
    {
        System.out.println("size: " + DB.size());
        for(Transaction t: DB)
            t.output();
    }
}
