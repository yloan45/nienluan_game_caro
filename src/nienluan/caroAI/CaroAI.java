package nienluan.caroAI;

import java.util.ArrayList;
import java.util.Random;

public class CaroAI {
    Random rand;
    private int nextX;
    private int nextY;
    private int mode;
    private State root; 													// Trạng thái hiện tại của trò chơi
    private Heuristic heuristic;		

    
    public CaroAI(int mode) {
        rand = new Random();
        this.mode = mode;	
		if(this.mode == 1) { 												// Nếu Mode = 1 => AI đánh trước
			root = new State(); 
			
			// đánh ở giữa bàn cờ
			nextX = Value.SIZE/2;
			nextY = Value.SIZE/2;
			
		}
		else root = new State(); 
		heuristic = new Heuristic();
    }

    
    public int getNextX() {
    	return nextX;
    }
    
    public int getNextY() {
    	return nextY;
    }
    
	public void update(int x, int y, int value) {
		root.update(x, y, value);
	}

	public boolean isClickable(int x, int y) {
		return root.isClickable(x, y);
	}

	public boolean isOver() {
		return root.isOver();
	}
    
	
	
	// CHƯA ĐƯỢC CÀI ĐẶT
	
    private int eval(State state) {
    	// cài đặt lượng giá của bàn cờ tại đây
    	
    	return 10000;					// đặt giá trị tạm thời
    }
    
    
    
    
    public boolean checkWinner(int player) {
    	return root.checkWinner(player);
    }
    
  
    public void nextStep() {
    	
    	Cell choice = bestMove(root); 										// tìm kiếm bước đi tối ưu nhất
    	this.nextX = choice.getX(); 
        this.nextY = choice.getY();
        if(!isClickable(this.nextX, this.nextY));
        else update(nextX, nextY, Value.AI_VALUE); 							// cập nhật root
        
    }
    
    
    /**
     * 
     * @param state
     * @return
     */
    
    // TÌM KIẾM NƯỚC ĐI TỐI 
    
    public Cell bestMove(State state) {
    	
        State remState = new State(state.getState());
        heuristic.evaluateEachCell(remState, Value.AI_VALUE);
        
        ArrayList<EvalCell> list = heuristic.evalMaxList();
        ArrayList<EvalCell> ListChoose = new ArrayList<EvalCell>();			// tạo arraylist
        
        
        int n = list.size();
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
        	int value = minValue(remState, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        	
         if (maxValue < value) {
                //maxValue = value;
                //ListChoose.clear();
                ListChoose.add(list.get(i));
            } else if (maxValue == value) ListChoose.add(list.get(i));
        }
        
        
        n = ListChoose.size(); 												// lấy số phần tử có điểm cao nhất
        int x = rand.nextInt(n); 											// chọn ra một
        return ListChoose.get(x).getCell();
    }
    
    
    /**
     * 
     * @param state
     * @param alpha
     * @param beta
     * @param depth
     * @return
     */
    
    private int maxValue(State state, int alpha, int beta, int depth) {
        if (depth >= Value.MAX_DEPTH || state.checkWinner(Value.AI_VALUE)) 
        	 return eval(state);
        int v = -9999;
        ArrayList<EvalCell> list = heuristic.evalMaxList();
        for (int i = 0; i < list.size(); i++) {
            v = Math.max(alpha, minValue(state, alpha, beta, depth + 1));
          }
            if(v > beta)
        		return v;
        	alpha = Math.max(alpha,v);
        	return v;
        
    }

    /**
     * 
     * @param state
     * @param alpha
     * @param beta
     * @param depth
     * @return
     */
    
    private int minValue(State state, int alpha, int beta, int depth) {
        if (depth >= Value.MAX_DEPTH || state.checkWinner(Value.AI_VALUE)) 
        	 return eval(state);				//  
        int v = 9999;
        ArrayList<EvalCell> list = heuristic.evalMaxList();
        for (int i = 0; i < list.size(); i++) {
            v = Math.min(alpha, maxValue(state, alpha, beta, depth + 1));
          }
            if(v < alpha)
        		return v;
        	beta = Math.min(beta,v);
        	return v;
        
    }
}
