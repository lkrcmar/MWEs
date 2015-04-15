package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.common.SemanticSpace;

import java.math.BigDecimal;
import java.util.*;

public class Common {
	
	public static double round(double d, int decimalPlace){
	    // see the Javadoc about why we use a String in the constructor
	    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}

    public static boolean ignoreTags(SemanticSpace space) {
        return space.getSpaceName().endsWith(Constants.SSNOTAGSFN_EXTENSION);
    }

    public static String mapToStringRep(String strStartingWithSpaceName) {
        char space1char = strStartingWithSpaceName.charAt(0);
        switch (space1char) {
            case 'L': return "LSA";
            case 'H': return "HAL";
            case 'V': return "VSM";
            case 'R': return "RI";
            case 'C': return "COALS";
            default: {
                throw new IllegalArgumentException("Unknown space in 3 chars!!");
            }
        }
    }

    public static String extractSpacePars(String semanticSpaceName) {
        String result = semanticSpaceName.toString().replace("_ALLF50OTXX", "_X_true");
        result = result.replace("_ALLF50OT", "_X_false");
        result = result.substring(result.indexOf("_") + 1);

        return result;
    }
}
