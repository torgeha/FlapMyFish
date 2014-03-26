package no.ntnu.flapmyfish.util;

import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

public class GraphicsUtils {
	
	public static Paint createPaint(float textSize, int color, boolean alignCenter){
		Paint p = new Paint();
		p.setTypeface(Typeface.SANS_SERIF);
		p.setAntiAlias(true);
		p.setTextSize(textSize);
		p.setColor(color);
		if (alignCenter) p.setTextAlign(Align.CENTER);
		return p;
	}

}