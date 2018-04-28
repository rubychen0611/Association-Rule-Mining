package Data;

public class Item implements Comparable<Item>
{
    private String name;
    public Item(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    @Override
    public int compareTo(Item o)
    {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj)
    {
        return name.equals(((Item)obj).name);
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
