package nienluan.caroAI;

// hàm lượng giá của một ô 

public class EvalCell {
	private int value;
	private Cell cell;
	
	public EvalCell(Cell cell, int value) {
		super();
		this.value = value;
		this.cell = cell;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}


}
