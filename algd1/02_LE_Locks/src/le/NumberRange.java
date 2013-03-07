package le;

public class NumberRange {
    // INVARIANT: lower <= upper
    private int lower, upper = 0;

    public void setLower(int i) {
        if (i > upper) throw new IllegalArgumentException();
        // unluckyScheduling();
        lower = i;
    }

    public void setUpper(int i) {
        if (i < lower) throw new IllegalArgumentException();
        upper = i;
    }

    public boolean contains(int i) {
        return i >= lower && i <= upper;
    }
    
    @Override
    public String toString() {
        return "lower: "+lower+", upper: "+upper;
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        final NumberRange r = new NumberRange();
        r.setUpper(10);
        r.setLower(5);
        System.out.println(r.toString());
        
        Thread a = new Thread() {
            public void run() { r.setLower(7); }
        };
        
        Thread b = new Thread() {
            public void run() { r.setUpper(6); }
        };
        a.start(); b.start();
        
        a.join(); b.join();
        
        System.out.println(r.toString());
    }
    
    /** Causes deadly scheduling. */
    @SuppressWarnings("unused")
	private static void unluckyScheduling() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {}
    }
}
