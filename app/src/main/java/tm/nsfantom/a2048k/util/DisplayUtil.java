package tm.nsfantom.a2048k.util;

import android.content.res.Resources;
import android.util.TypedValue;

public final class DisplayUtil {

    public static int dpToPx(Resources resources, int dp) {
        // Converts dip into its equivalent px
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

}
