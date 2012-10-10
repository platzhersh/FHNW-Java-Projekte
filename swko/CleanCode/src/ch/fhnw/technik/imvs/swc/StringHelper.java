package ch.fhnw.technik.imvs.swc;

public class StringHelper {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    /**
     * Returns a string made by connecting all the strings of the given Array
     * separated by the separator
     * @param input Array of Strings
     * @param separator
     * @return
     */
    static String concatenate(String[] input, char separator) {
        try {
            for (String part : input) {
                if (part.isEmpty() || part.equals("") || part.equals(null)) {
                    throw new Exception("Die Teilstrings dürfen nicht leer sein.");
                }
                
            }
        }
        catch (Exception e){
            
        }
        finally {
            return("");
        }
    }

}
