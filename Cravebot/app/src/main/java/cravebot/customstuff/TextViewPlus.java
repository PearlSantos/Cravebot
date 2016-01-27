package cravebot.customstuff;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by elysi on 12/28/2015.
 *
 * This class is an extension of TextView. This is created so that the fonts of the TextView could easily be changed.
 */
public class TextViewPlus extends TextView {
    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

}
