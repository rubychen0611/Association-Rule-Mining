package Mining.FP_Growth;

import Data.DataBase;
import Data.Item;
import Data.Transaction;
import Mining.ItemSet;
import Mining.Miner;

import java.util.*;

class FPTreeNode
{
    private Item item;
    private int support_count;
    private FPTreeNode parent;
    private ArrayList<FPTreeNode> children;
    public FPTreeNode(Item item, FPTreeNode parent)
    {
        this.item = item;
        this.parent = parent;
        this.support_count = 1;
        this.children = new ArrayList<>();
    }
    public void addSupportCount()
    {
        this.support_count ++;
    }
    public void addChild(FPTreeNode child)
    {
        this.children.add(child);
    }
    public ArrayList<FPTreeNode> getChildren()
    {
        return children;
    }

    public int getSupport_count() { return support_count; }

    public Item getItem()
    {
        return item;
    }

    public FPTreeNode getParent() { return parent; }

    public FPTreeNode hasChild(Item item)
    {
        for(FPTreeNode child: children)
        {
            if(child.getItem().equals(item))
                return child;
        }
        return null;
    }
}
class FPTree
{
    private FPTreeNode root;
    private LinkedHashMap<Item, LinkedList<FPTreeNode>> headTable;
    HashMap<Item, Integer> countmap;                    //TODO: change to hash
    public FPTree()
    {
        root = new FPTreeNode(null, null);
        headTable = new LinkedHashMap<>();
    }

    public FPTree(ArrayList <CPB> cpbs, HashMap<Item, Integer> countmap)         //由条件模式基建树
    {
        this.countmap = countmap;
        root = new FPTreeNode(null, null);
        headTable = new LinkedHashMap<>();
        for(CPB cpb: cpbs)
        {
            Item item = cpb.getList().getFirst();
            LinkedList<Item> last = new LinkedList<>();
            last.addAll(cpb.getList());
            last.removeFirst();
            insertTree(item, last, root);
        }
    }

    public void setCountmap(HashMap<Item, Integer> countmap)
    {
        this.countmap = countmap;
    }

    public FPTreeNode getRoot() { return root; }

    public HashMap<Item, Integer> getCountmap() { return countmap; }

    public LinkedHashMap<Item, LinkedList<FPTreeNode>> getHeadTable() { return headTable; }

    public boolean isEmpty()
    {
        if(root.getChildren().size() == 0)
            return true;
        return false;
    }
    public boolean ifSinglePath()
    {
        FPTreeNode p = root;
        if(p.getChildren().size() == 0)
            return true;
        while(p.getChildren().size() != 0)
        {
            if(p.getChildren().size() != 1)
                return false;
            p = p.getChildren().get(0);
        }
        return true;
    }
    public int getSinglePathCount()
    {
        return root.getChildren().get(0).getSupport_count();
    }
    public ArrayList<LinkedList<Item>> getSinglePathSubsets()
    {
        LinkedList<Item> singleList = new LinkedList<>();
        FPTreeNode p = root.getChildren().get(0);
        while(p.getChildren().size() != 0)
        {
            singleList.addLast(p.getItem());
            p = p.getChildren().get(0);
        }
        singleList.addLast(p.getItem());

        ArrayList<LinkedList<Item>> subsets = new ArrayList<>();
        int N = (int)Math.pow(2, singleList.size());
        for(int i = 0; i < N; i++)
        {
            int k = 0, m = i;
            LinkedList<Item> subset = new LinkedList<>();
            while(m > 0)
            {
                if((m & 1) == 1)
                {
                    subset.addLast(singleList.get(k));
                }
                m >>= 1;
                k++;
            }
            if(!subset.isEmpty())
                subsets.add(subset);
        }
        return subsets;
    }
    private void insertTree(Item item, LinkedList<Item> last, FPTreeNode parent)
    {
        FPTreeNode curNode = parent.hasChild(item);
        if(curNode != null)
            curNode.addSupportCount();
        else
        {
            curNode = new FPTreeNode(item, parent);
            if(headTable.containsKey(item))
            {
                headTable.get(item).add(curNode);
            }
            else
            {
                LinkedList<FPTreeNode> headList = new LinkedList<>();
                headList.add(curNode);
                headTable.put(item, headList);          //尾插, 按插入顺序排列
            }
            parent.addChild(curNode);
        }
        if(!last.isEmpty())
        {
            Item next = last.getFirst();
            last.removeFirst();
            insertTree(next, last, curNode);
        }
    }
    public int getSupportCount(Item item)
    {
        return countmap.get(item);
    }
    public ArrayList<CPB> genCPBs(Item item)                                           //自底向上遍历树生成条件模式基
    {
        ArrayList<CPB> cpbs = new ArrayList<>();
        LinkedList<FPTreeNode> list = headTable.get(item);
        //生成cpbs ：未剪枝
        for(FPTreeNode leaf: list)
        {
            CPB cpb = new CPB();
            FPTreeNode p = leaf;
            int support_count = leaf.getSupport_count();
            p = p.getParent();
            while(p != root)
            {
                cpb.addItem(p.getItem());
                p = p.getParent();
            }
            if(cpb.getList().isEmpty()) continue;
            for(int i = 0; i < support_count; i++)              //多次插入
                 cpbs.add(cpb);
        }
        return cpbs;
    }
    public void outputTree(FPTreeNode p)  //for debug
    {
        if(p.getItem() == null) System.out.println("root");
        else System.out.println(p.getItem() + " " + p.getSupport_count());
        if(p.getChildren().size() != 0)
        {
            for(FPTreeNode child: p.getChildren())
            {
                outputTree(child);
            }
        }
    }
}
public class FP_Growth implements Miner
{
    private static ArrayList<Transaction> DB;
    private static double min_sup;
    private static double min_conf;
    HashMap<Item, Integer> frequent_1_itemSets;
    HashMap<ItemSet, Integer> frequentSets;
    public FP_Growth(DataBase DataBase, double min_sup, double min_conf)
    {
        this.DB = DataBase.getDB();
        this.min_sup = min_sup;
        this.min_conf = min_conf;
        frequentSets = new HashMap<>();
    }

    private void find_frequent_1_itemSets()
    {
        frequent_1_itemSets = new HashMap<>();
        for(Transaction t : DB)
        {
            for(Item item: t.getTransaction())
            {
                if(frequent_1_itemSets.containsKey(item))
                {
                    int value = frequent_1_itemSets.get(item);
                    frequent_1_itemSets.put(item, value + 1);
                }
                else
                    frequent_1_itemSets.put(item, 1);
            }
        }
        //删去支持度小于阈值的
       deleteInfrequentItems(frequent_1_itemSets);
    }
    public static void deleteInfrequentItems(HashMap<Item, Integer> map)
    {
        Iterator<Map.Entry<Item, Integer>> it = map.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<Item, Integer> entry=it.next();
            if(entry.getValue() <  (int)DB.size() * min_sup)
                it.remove();
        }
    }
    public ArrayList<CPB> firstCPBs()         //扫描数据库，获得一开始的条件模式基
    {
        ArrayList<CPB> cpbs = new ArrayList<>();
        for(Transaction t: DB)
        {
            CPB cpb = new CPB();
            for(Item item: t.getTransaction())
            {
                if(frequent_1_itemSets.containsKey(item))
                {
                    cpb.addItem(item);
                }
            }
            cpb.sortBySupportCount(frequent_1_itemSets);        //排序
            if(!cpb.getList().isEmpty())
                cpbs.add(cpb);
        }
        return cpbs;
    }
    private  HashMap<Item, Integer> prune(ArrayList<CPB> cpbs)
    {
        HashMap<Item, Integer> countmap = new HashMap<>();
        for(CPB cpb: cpbs)
        {
            for(Item item: cpb.getList())
            {
                if(countmap.containsKey(item))
                {
                    int value = countmap.get(item);
                    countmap.put(item, value + 1 );
                }
                else countmap.put(item, 1 );
            }
        }
        deleteInfrequentItems(countmap);
        Iterator<CPB> it1 = cpbs.iterator();
        while(it1.hasNext())
        {
            CPB cpb = it1.next();
            Iterator<Item> it2 = cpb.getList().iterator();
            while (it2.hasNext())
            {
                Item cur = it2.next();
                if (!countmap.containsKey(cur))
                    it2.remove();
            }
            if(cpb.getList().isEmpty())
                it1.remove();
        }
        return countmap;
    }
    private void FP_growth(FPTree tree, LinkedList<Item> alpha)
    {
        if(tree.ifSinglePath())          //单个路径
        {
            ArrayList<LinkedList<Item>> subsets = tree.getSinglePathSubsets();
            for(LinkedList<Item> subset: subsets)
            {
                int support_count = tree.getCountmap().get(subset.getLast());
                subset.addAll(alpha);
                outputFrequentItemSets(subset,support_count);
                if(subset.size() > 1)
                    frequentSets.put(new ItemSet(new TreeSet<>(subset)), support_count);
            }

            return;
        }
        LinkedHashMap<Item, LinkedList<FPTreeNode>> map = tree.getHeadTable();
        ListIterator<Map.Entry<Item,LinkedList<FPTreeNode>>> it=new ArrayList<>(map.entrySet()).listIterator(map.size());
        while(it.hasPrevious())         //逆序遍历项头表
        {

            Map.Entry<Item,LinkedList<FPTreeNode>> entry=it.previous();
            //System.out.println(entry.getKey() + " " + tree.getSupportCount(entry.getKey()));
            LinkedList<Item> beta  = new LinkedList<>(alpha);
            beta.addFirst(entry.getKey());
            int support_count = tree.getSupportCount(entry.getKey());
            outputFrequentItemSets(beta, support_count);
            if(beta.size() > 1)
                frequentSets.put(new ItemSet(new TreeSet<>(beta)), support_count);
            ArrayList<CPB> cpbs = tree.genCPBs(entry.getKey());
            HashMap<Item, Integer> countmap = prune(cpbs);
            FPTree betaFPTree = new FPTree(cpbs, countmap);
            if(!betaFPTree.isEmpty())
                FP_growth(betaFPTree, beta);
        }
    }
    @Override
    public void mine()
    {
       find_frequent_1_itemSets();
        ArrayList<CPB> cpbs = firstCPBs();            //扫描数据库，获得初始模式基集合
        //for(CPB cpb: cpbs)
       //     System.out.println(cpb);
        FPTree fpTree =new FPTree(cpbs, frequent_1_itemSets);                                //由初始模式基建FP树
       // fpTree.outputTree(fpTree.getRoot());                            //debug
        FP_growth(fpTree, new LinkedList<Item>());
        outputStrongAssociationRules();
    }

    public void outputFrequentItemSets(LinkedList<Item> list, int support_count)
    {
        System.out.print("{");
        for(Item item: list)
        {
            System.out.print(item);
            if(item == list.getLast())
                System.out.println("} " + support_count);
            else System.out.print(",");
        }
    }

    public void outputStrongAssociationRules()             //输出强关联规则
    {
        System.out.println("频繁项集产生的强关联规则：");
        Iterator<Map.Entry<ItemSet,Integer>> it = frequentSets.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<ItemSet,Integer> entry=it.next();
            ItemSet l = entry.getKey();
            TreeSet<ItemSet> subsets = l.getSubSets();
            for(ItemSet s: subsets)
            {
                if(support_count(l) >= support_count(s) * min_conf)
                {
                    TreeSet<Item> remainSet = new TreeSet<>();
                    remainSet.addAll(l.getItemSet());
                    remainSet.removeAll(s.getItemSet());
                    ItemSet remain = new ItemSet(remainSet);

                    System.out.println(s +" => " + remain);
                }
            }
        }
    }
    private int support_count(ItemSet set)
    {
        int count = 0;
        for(Transaction t: DB)
        {
            if(t.getTransaction().containsAll(set.getItemSet()))
                count ++;
        }
        return count;
    }
}
