package sheep.gui;

import java.util.LinkedList;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * A Container is a Widget which contains other Widgets. It passes 
 * keyboard and touch events down to its children, until it finds a child
 * Widget which can handle the message. When this is found, the rest of the
 * children will not receive the same message.
 */
public class Container extends Widget {

	LinkedList<Widget> widgets;
	
	/**
	 * Constructor.
	 */
	public Container() {
		widgets = new LinkedList<Widget>();
	}
	
	@Override
	public void draw(Canvas canvas) {
		for(Widget w : widgets) {
			w.draw(canvas);
		}
	}
	
	/**
	 * Adds a child Widget to this Container.
	 * @param widget The Widget to add to this Container.
	 */
	public void addWidget(Widget widget) {
		widgets.add(widget);
	}
	
	/**
	 * Remove a Widget from this Container.
	 * @param widget The Widget to remove from this Container.
	 */
	public void removeWidget(Widget widget) {
		widgets.remove(widget);
	}
	
	@Override
	public boolean onKeyDown(KeyEvent event) {
		
		for(Widget w : widgets) {
			if(w.onKeyDown(event)) {
				return true;
			}
		}		
		
		return false;
	}

	@Override
	public boolean onKeyUp(KeyEvent event) {

		for(Widget w : widgets) {
			if(w.onKeyUp(event)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean onTouchDown(MotionEvent event) {

		for(Widget w : widgets) {
			if(w.onTouchDown(event)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		// TODO Auto-generated method stub
		
		for(Widget w : widgets) {
			if(w.onTouchMove(event)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		// TODO Auto-generated method stub
		
		for(Widget w : widgets) {
			if(w.onTouchUp(event)) {
				return true;
			}
		}
		
		return false;
	}
}
