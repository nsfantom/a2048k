package tm.nsfantom.a2048k.util;

import android.content.res.Resources;
import android.util.TypedValue;

public final class DisplayUtil {

    public static int dpToPx(Resources resources, int dp) {
        // Converts dip into its equivalent px
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    public static int getIntFromColor(int red, int green, int blue){
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

}
