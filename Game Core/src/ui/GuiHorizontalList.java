package ui;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import ui.GuiContain;
import ui.GuiItem;


public class GuiHorizontalList extends GuiContain{
	public byte horizontal_float;//1: things go left to right, 0: centered, -1:things go right to left
		//this will also set the initial scroll
	public byte vertical_float;//1: things go top to bottom, 0: centered, -1:things go bottom to top
	public float horizontal_wrap;//true: if things are too big for the Pane, it will go to next line, else it becomes scrollable
			
	ArrayList<GuiItem> items;

	public Vec2 getPopulatePad(){
		Vec2 poppad = new Vec2();
		if(horizontal_float > 0){
			poppad.x = padding.left;
		}else if(horizontal_float < 0){
			poppad.x = padding.right;
		}
		if(vertical_float > 0){
			poppad.y = padding.top;
		}else if(vertical_float < 0){
			poppad.y = padding.bottom;
		}
		
		return poppad;
	}
	
	public void populate(){

		float currentlineheight =0;
		float totalheight;
		Vec2 pp = getPopulatePad();
		Vec2 currentPoint = pp;
		
		ArrayList<GuiItem> items_cur_line = new ArrayList<GuiItem>();
		
		for(GuiItem g : items){
			if(		horizontal_wrap > 0
			&&		currentPoint.x + g.width_height().x + g.margins.left + g.margins.right > this.horizontal_wrap
			&& items_cur_line.size() > 0
			){
				for(GuiItem l : items_cur_line)
					l.drawing_offset.x += (horizontal_wrap - currentPoint.x)*((float)(1+horizontal_float)/2f);
				items_cur_line = new ArrayList<GuiItem>();
				currentPoint.y = currentlineheight+pp.y;
				total_wh.y +=currentlineheight;
				currentlineheight = 0;
				currentPoint.x = pp.x;
			}
			
			currentPoint.x += g.margins.left;
			g.drawing_offset.x = currentPoint.x;
			Vec2 gwh = g.width_height();
			totalheight = gwh.y + g.margins.top + g.margins.bottom;
			if(totalheight > currentlineheight){
				for(GuiItem l : items_cur_line) 
					l.drawing_offset.y += (totalheight -currentlineheight)*((float)(1+vertical_float)/2f);
				currentlineheight = totalheight;
			}else g.drawing_offset.y = (currentlineheight-totalheight)*((float)(1+vertical_float)/2f);
			currentPoint.x += gwh.x + g.margins.right;
			items_cur_line.add(g);
		}
		total_wh.x = (horizontal_wrap > 0)?horizontal_wrap:currentPoint.x;
		total_wh.y += padding.bottom;
	}

	@Override
	public Vec2 width_height() {
		//
		if(total_wh.y < 0){
			populate();
		}
		return width_height;
	}
	
}
