package nienluan.caroAI;

import java.util.ArrayList;

/**
 * trạng thái của bàn cờ
 * 
 */

public class State {
	private int[][] cell;					// ma trận trạng thái
	private ArrayList<Cell> moveSteps;		// danh sách các bước đã đi
	private int steps;
	
	public int[][] getCell() {
		return cell;
	}

	public void setCell(int[][] cell) {
		this.cell = cell;
	}

	public ArrayList<Cell> getMoveSteps() {
		return moveSteps;
	}

	

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public State() {
		this.moveSteps = new ArrayList<Cell>();
		this.cell = new int[Value.SIZE][Value.SIZE];
		for (int i=0;i<Value.SIZE; i++) {
			for(int j=0;j<Value.SIZE;j++)
				cell[i][j] = 0;
		}
	}
	
	public int [][] getState(){
		return this.cell;
	}
	
	public State(int[][] cell){
		this.steps = 0;
		this.moveSteps = new ArrayList<Cell>();
		this.cell = new int[Value.SIZE][Value.SIZE];
		for (int i=0; i<Value.SIZE; i++) {
			for(int j=0; j<Value.SIZE; j++) {
				this.cell[i][j] = cell[i][j];
				if(cell[i][j] != 0) {
					this.moveSteps.add(new Cell(i, j, cell[i][j]));
					this.steps++;
				}
			}
		}
		
	}
	
	// kiem tra o da duoc danh hay chua?
	
	public boolean isClickable(int x, int y) {
		if(x >= 0 && x < Value.SIZE && y >= 0 && y < Value.SIZE)
			if(cell[x][y] == 0) return true;
		return false;
	}
	
	public void update(int x, int y, int player) {
		this.cell[x][y] = player;
		this.moveSteps.add(new Cell(x, y, player));
	}

	
	// kiểm tra thắng
	
	public boolean checkWinner(int player) {
		
		int[] X = {1, 1, 0, 1};  							// các đường cần kiểm tra: ngang + dọc + 2chéo
		int[] Y = {0, 1, 1, -1}; 							// tìm người thắng
		
		for (int x = 0; x < Value.SIZE; x++) {
			for (int y = 0; y < Value.SIZE; y++) {
				if(cell[x][y] == player) { 					// Nếu ô này đã được player chọn => kiểm tra			
					for (int i = 0; i < 4; i++) { 			// kiểm tra 4 đường
						int count = 1; 						// count = 5 => player chiến thắng
						for(int j = 1; j <= 4; j++) { 		// kiểm tra 4 ô tiếp theo
							int vtx = x + X[i]*j; 			// vị trí x của ô tiếp theo cần check
							int vty = y + Y[i]*j; 			// vị trí y của ô tiếp theo cần check
							
							// vtx hoặc vty < 0 hoặc > Value.SIZE, hoặc ô này != ô đầu => khỏi ktra
							if(vtx < 0 || vty < 0 || vtx >= Value.SIZE || vty >= Value.SIZE) break;
							
							if(cell[vtx][vty] == player) count++;
							else break;
						}
						if(count == 5) return true; 		// Player thắng
					}
				}
			}
		}
		return false; 										// Không ai thắng cả
	}
	
	
	
	public boolean isOver() {
		if(this.steps == Value.SIZE*Value.SIZE) return true;
		else return false;
	}
}
