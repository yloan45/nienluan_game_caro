package nienluan.caroAI;

public class Cell {
	private int x;
	private int y;
	private int selected;
	
	public Cell() {
		this.x = -1;
		this.y = -1;
		this.selected = 0;
	}
	

	public Cell(int x, int y, int selected) {
		this.x = x;
		this.y = y;
		this.selected =  selected;
	}
	
	
	/* GETTER X, Y, SELECTED*/
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
	}


	/* ô có thể chọn đc */
	public boolean isClickable() {
		if(this.selected != 0) return true;
		return false;
	}
	

	

	
}
