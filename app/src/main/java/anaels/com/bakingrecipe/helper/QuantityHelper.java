package anaels.com.bakingrecipe.helper;

/**
 * Created by Anael on 11/13/2017.
 */
public class QuantityHelper {
    public static String formatForDisplay(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
