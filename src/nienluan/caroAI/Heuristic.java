package nienluan.caroAI;

import java.util.ArrayList;

public class Heuristic {

    private int[][] evalState; // ma trận lượng giá
    
    public Heuristic() {
        this.evalState = new int[Value.SIZE][Value.SIZE];
    }
    
    
   /* Khởi tạo hai mảng điểm Tấn công (AScore) và Phòng thủ (Dscore)*/
    
   int[] DScore = { 0, 1, 9, 81, 729 };										// mảng điểm phòng thủ
   int[] AScore = {0, 2, 18, 162, 1458};									// mảng điểm tấn công

   
    
   // reset giá trị về 0
    void reset() {
        for (int i = 0; i < Value.SIZE; i++) {
            for (int j = 0; j < Value.SIZE; j++) {
                evalState[i][j] = 0;
            }
        }
    }
    

    
    // x: cột 
    // y: hàng
    // tiến hành quét 1 block 5 ô theo các hướng: ngang + dọc + chéo(chéo trên + chéo dưới)
    
    public void evaluateEachCell(State state, int player) {
        reset();
        int x, y, i, countAI=0, countUser=0;
        int[][] cell = state.getState();


        // tiến hành kiểm tra theo chiều ngang
      
        for (x = 0; x < Value.SIZE; x++) {
            for (y = 0; y < Value.SIZE - 4; y++) {
            	countAI = countUser = 0;
            	
               // dem so quan co cua nguoi choi va ai 
              for (i = 0; i < 5; i++) { // duyệt đoạn
                    if (cell[x][y+i] == Value.AI_VALUE) countAI++;
                    else if (cell[x][y+i] == Value.USER_VALUE) countUser++;
                }
              
              // tien hanh cong diem cho cac o trong 
              for(i = 0; i < 5; i++) {
            	  if(cell[x][y+i] == 0) {									// ô này chưa ai đánh
            		  if(countAI == 0) {									// Nếu AI chưa đánh ô nào
            			  if(player == Value.AI_VALUE)						// nếu lượt chơi hiện tại của AI
                			  evalState[x][y+i] += DScore[countUser];		// nếu có n quân địch -> các ô trống được cộng DScore[n]_phòng thủ điểm
                		  else evalState[x][y+i] += AScore[countUser];		// ngược lại, nếu có n quân ta thì các ô trống được cộng điểm tấn công
            		  }
            		  
            		  else if(countUser == 0) {								// tương tự như trên, tính điểm cho AI
            			  if(player == Value.USER_VALUE)
	            			  evalState[x][y+i] += DScore[countAI];			//  có n quân địch -> các ô trống được cộng điểm phòng thủ DS điểm
	            		  else evalState[x][y+i] += AScore[countAI];
            		  } 
            	  }
              }
           }
        }
        
        
        // tiến hành kiểm tra theo chiều dọc //
        for (x = 0; x < Value.SIZE - 4; x++) {
            for (y = 0; y < Value.SIZE; y++) {
            	countAI = countUser = 0;
            	
               // đếm số ô của người chơi(user+máy)
            	
              for (i = 0; i < 5; i++) { // duyệt đoạn
                    if (cell[x+i][y] == Value.AI_VALUE) countAI++;
                    else if (cell[x+i][y] == Value.USER_VALUE) countUser++;
                }

              
              // tiến hành cộng điểm cho các ô trống
              
              for(i = 0; i < 5; i++) {
            	  if(cell[x+i][y] == 0) {
            		  if(countAI == 0) {
            			  if(player == Value.AI_VALUE)
                			  evalState[x+i][y] += DScore[countUser];
                		  else evalState[x+i][y] += AScore[countUser];
            		  }
            		  
            		  else if(countUser == 0) {
            			  if(player == Value.USER_VALUE)
	            			  evalState[x+i][y] += DScore[countAI];
	            		  else evalState[x+i][y] += AScore[countAI];
            		  }
            		  
            	  }
        
              }
           
           }
       }
        
        // kiem tra cheo tren
        for (x = 0; x < Value.SIZE - 4; x++) {
            for (y = 0; y < Value.SIZE -4 ; y++) {
            	countAI = countUser = 0;
               // dem so o cua nguoi choi va ai 
              for (i = 0; i < 5; i++) { // duyệt đoạn
                    if (cell[x+i][y+i] == Value.AI_VALUE) countAI++;
                    else if (cell[x+i][y+i] == Value.USER_VALUE) countUser++;
                }

              // tien hanh cong diem cho cac o trong 
              for(i = 0; i < 5; i++) {
            	  if(cell[x+i][y+i] == 0) {
            		  if(countAI == 0) {
            			  if(player == Value.AI_VALUE)
                			  evalState[x+i][y+i] += DScore[countUser];
                		  else evalState[x+i][y+i] += AScore[countUser];
            		  }
            		  
            		  else if(countUser == 0) {
            			  if(player == Value.USER_VALUE)
	            			  evalState[x+i][y+i] += DScore[countAI];
	            		  else evalState[x+i][y+i] += AScore[countAI];
            		  }
            		  
            	  }
        
              }
           
           }
       }
        
        
        // kiem tra cheo duoi
        for (x = 4; x < Value.SIZE; x++) {
            for (y = 0; y < Value.SIZE-4; y++) {
            	countAI = countUser = 0;
               // dem so o cua nguoi choi va ai 
              for (i = 0; i < 5; i++) { // duyệt đoạn
                    if (cell[x-i][y+i] == Value.AI_VALUE) countAI++;
                    else if (cell[x-i][y+i] == Value.USER_VALUE) countUser++;
                }

              // tien hanh cong diem cho cac o trong 
              for(i = 0; i < 5; i++) {
            	  if(cell[x-i][y+i] == 0) {
            		  if(countAI == 0) {
            			  if(player == Value.AI_VALUE)
                			  evalState[x-i][y+i] += DScore[countUser];
                		  else evalState[x-i][y+i] += AScore[countUser];
            		  }
            		  
            		  else if(countUser == 0) {
            			  if(player == Value.USER_VALUE)
	            			  evalState[x-i][y+i] += DScore[countAI];
	            		  else evalState[x-i][y+i] += AScore[countAI];
            		  }
            		  
            	  }
             } 
          }
       }
        
    }
       
    
    
    // Lấy danh sách những ô tối ưu nhất có điểm lượng giá cao để duyệt
     
    public ArrayList<EvalCell> evalMaxList() {
        int size = Value.MAX_NUM_OF_HIGHEST_CELL_LIST; 												// số phần tử tối đa được phép lấy = 5
        
        int[] maxValueList = new int[size];
        Cell[] maxCellList = new Cell[size];
        
        // khởi tạo giá trị
        for(int i = 0; i< size; i++) {
        	maxValueList[i] = Integer.MIN_VALUE;
        	maxCellList[i] = new Cell();
        }
        
        for (int x = 0; x < Value.SIZE; x++) {
            for (int y = 0; y < Value.SIZE; y++) {
            	int value = getEvalCellValue(x, y);
            	
           	/*Tìm list những ô tối ưu để đánh*/
           	for(int i = size-1; i >= 0; i--) {
            		if(maxValueList[i] <= value) { 
            			maxCellList[i].setLocation(maxCellList[i].getX(), maxCellList[i].getY()); // cập nhật vị trí
            			maxValueList[i] = value;
            			maxCellList[i].setLocation(x, y);												
            			//break;
            		}
            	}
            }
        }
   
        ArrayList<EvalCell> list = new ArrayList<EvalCell>();
        list.add(new EvalCell(maxCellList[size-1], maxValueList[size-1])); 						// add vào phần tử có điểm lớn nhất vào cell
        return list;
    }
    
	
	public int getEvalCellValue(int x, int y) {
		return this.evalState[x][y];
	}
	
	public void setEvalCell(int x, int y, int value) {
		this.evalState[x][y] = value;
	}


	
	
	
	/* *
	 * 
	 * ĐIỂM LƯỢNG GIÁ BÀN CỜ 
	 * 
	 * Cài đặt tại đây
	 * 
	 * hàm tính điểm
	 * 
	 * hàm in điểm
	 * 
	 * 
	 * 
	 * */
	
	
}
