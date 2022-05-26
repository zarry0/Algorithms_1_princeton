public class p
{
    public static void valueOrReference(Counter c) {
        c.increment();
    }
    public static void main(String[] args) {
        Counter c1 = new Counter("ones");
        c1.increment();
        Counter c2 = c1;
        c2.increment();
        System.out.printf("c1: %s, c2: %s\n", c1, c2);

        p.valueOrReference(c1);
        System.out.printf("c1: %s, c2: %s\n", c1, c2);

        Counter c3 = new Counter("tails");
        valueOrReference(c3);
        System.out.printf("c1: %s, c2: %s, c3: %s\n", c1, c2, c3);
    }
}

class Counter 
{
    int num = 0;
    String id;

    Counter(String id) {
        this.id = id;
    }

    public void increment() {
        this.num++;
    }

    public int tally() {
        return this.num;
    }

    public String toString() {
        return this.num + " " + this.id;
    }
}