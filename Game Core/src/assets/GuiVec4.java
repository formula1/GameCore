package assets;

public class GuiVec4 {

	public float top;
	public float bottom;
	public float left;
	public float right;

	public GuiVec4(){
		bottom = left= right = top = 0;
	}

	public GuiVec4(float all){
		bottom = left= right = top = all;
	}
	public GuiVec4(float horizontals, float verticals){
		left = right = horizontals;
		top = bottom = verticals;
	}
	public GuiVec4(float t, float b, float l, float r){
		top = t;
		bottom = b;
		left = l;
		right = r;
	}
	
}
