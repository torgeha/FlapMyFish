package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import sheep.collision.Polygon;
import sheep.math.BoundingBox;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class HorizontalBorder extends Token {
	
	Paint p;
	Rect r;
	
	public HorizontalBorder(int y, int height) {
		super();
		p = new Paint();
		p.setColor(Color.WHITE);
		r = new Rect(0, y, Constants.WINDOW_WIDTH, y+height);
		setShape(new Polygon(new BoundingBox(r).getPoints()));
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(r, p);
		super.draw(canvas);
	}

}
