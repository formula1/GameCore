package ui;

import java.util.ArrayList;

import assets.Vec2u;


public class GuiVerticalList extends GuiContain {

	public byte horizontal_float;//1: things go to furthest left, 0: centered, -1:things furthest right
	//this will also set the initial scroll
public byte vertical_float;//1: things go top to bottom, 0: centered, -1:things go bottom to top
public float vertical_wrap;//true: if things are too big for the Pane, it will go to next line, else it becomes scrollable
		
ArrayList<GuiItem> items;

public Vec2u getPopulatePad(){
	Vec2u poppad = new Vec2u();
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

	float currentlinewidth =0;
	float totalwidth;
	Vec2u pp = getPopulatePad();
	Vec2u currentPoint = pp;
	
	ArrayList<GuiItem> items_cur_line = new ArrayList<GuiItem>();
	
	for(GuiItem g : items){
		if(		vertical_wrap > 0
		&&		currentPoint.y + g.width_height().y + g.margins.top + g.margins.bottom > this.vertical_wrap
		&& items_cur_line.size() > 0
		){
			for(GuiItem l : items_cur_line)
				l.drawing_offset.y += (vertical_wrap - currentPoint.y)*((float)(1f+vertical_float)/2f);
			items_cur_line = new ArrayList<GuiItem>();
			currentPoint.x = currentlinewidth+pp.x;
			total_wh.y +=currentlinewidth;
			currentlinewidth = 0;
			currentPoint.x = pp.x;
		}
		
		currentPoint.x += g.margins.left;
		g.drawing_offset.x = currentPoint.x;
		Vec2u gwh = g.width_height();
		totalwidth = gwh.x + g.margins.left + g.margins.right;
		if(totalwidth > currentlinewidth){
			for(GuiItem l : items_cur_line) 
				l.drawing_offset.y += (totalwidth -currentlinewidth)*((float)(1+horizontal_float)/2f);
			currentlinewidth = totalwidth;
		}else g.drawing_offset.x = (currentlinewidth-totalwidth)*((float)(1+horizontal_float)/2f);
		currentPoint.y += gwh.y + g.margins.bottom;
		items_cur_line.add(g);
	}
	total_wh.y = (vertical_wrap > 0)?vertical_wrap:currentPoint.x;
	total_wh.x += padding.right;
}

@Override
public Vec2u width_height() {
	//
	if(total_wh.y < 0){
		populate();
	}
	return width_height;
}

}
