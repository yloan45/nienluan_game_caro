package nienluan.caroAI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Game extends JFrame implements MouseListener{

	
	private static final long serialVersionUID = 1L;
	private int mode;
	private JPanel contenPane;
	private JPanel TableCell;											// tạo  1 panel chứa các ô của bàn cờ
	private JLabel[][] cell;											// ma trận các ô
	private JLabel aiClickedCell; 										// cell được AI click chọn
	private Border cellBorder;											// tạo viền cho ô
	private CaroAI caro;
	private final ButtonGroup buttonGroup = new ButtonGroup();			// quản lý trạng thái được chọn hoặc không ( AI|USER Play fir

	/**
	 *  
	 * @param args
	 * @throws IOException 
	 */

	public Game() throws IOException {
		setResizable(false);																		// không được thay đổi kích thước
		setTitle("GAME CARO");																	// tittle
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Value.SIZE*Value.CELL_WIDTH+3*Value.MARGIN+285, Value.SIZE*Value.CELL_WIDTH+50);	// kích thước game
		
		setLocationRelativeTo(null);																// mặc định cho vị trí bàn cờ ngay giữa
		cellBorder = new LineBorder(Color.black, 1); 												// tạo border cho mỗi ô trong ma trận
		
		
		
		/* ========================== tạo các đối tượng trong game ===============================*/
		
		contenPane = new JPanel();
		contenPane.setBackground(Color.darkGray);
		setContentPane(contenPane);
		contenPane.setLayout(null);
		
		TableCell = new JPanel();
		TableCell.setLayout(new GridLayout(Value.SIZE, Value.SIZE, 0, 0));
		TableCell.setBounds(Value.MARGIN, Value.MARGIN, Value.SIZE*Value.CELL_WIDTH, Value.SIZE*Value.CELL_WIDTH);
		contenPane.add(TableCell);
		
		
		cell = new JLabel[Value.SIZE][Value.SIZE];
		
		// TẠO MA TRẬN CÁC Ô
	 
		for (int i = 0; i< Value.SIZE; i++) {
			for(int j =0 ; j< Value.SIZE; j++) {
				cell[i][j] = new JLabel();
				cell[i][j].setSize(Value.CELL_WIDTH, Value.CELL_WIDTH);	
				cell[i][j].setOpaque(true);														// cài đặt màu nền setBackground có hiệu lực
				cell[i][j].setBorder(cellBorder);
				cell[i][j].setBackground(Color.darkGray);
				cell[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				cell[i][j].setFont(new Font("Time New Roman", Font.BOLD, 20));
				cell[i][j].addMouseListener(this);												// bat su kien click chuot			
				TableCell.add(cell[i][j]);														// add cell vào tablecell
			}
		}
		
		
		
		// background setup game
		
		JPanel view = new JPanel();
		view.setBackground(new Color(250, 235, 215));
		view.setBounds(Value.SIZE*Value.CELL_WIDTH+2*Value.MARGIN, Value.MARGIN, 274, Value.SIZE*Value.CELL_WIDTH);
		contenPane.add(view);
		view.setLayout(null);
		
		JLabel lbltitle = new JLabel("GAME CARO");
		lbltitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitle.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		lbltitle.setForeground(new Color(0,128,0));// màu nền button
		lbltitle.setBounds(10, 11, 254, 50);
		view.add(lbltitle);
		
		
		//BUTTON NEW GAME
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "New Game") {
					int result = JOptionPane.showConfirmDialog(null, "New Game?", "Game Co Caro", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) newGame();		
				}
			}
		});
		
		btnNewGame.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnNewGame.setBounds(30, 315, 89, 37);
		btnNewGame.setBackground(new Color(255, 20, 147));						// màu nền button
		btnNewGame.setForeground(new Color(0,128,0));// màu nền button
		btnNewGame.setOpaque(false);
		btnNewGame.setBorder(new TitledBorder(cellBorder));
		view.add(btnNewGame);													// thêm button new game
		

		//BUTTON EXIT GAME
		
		JButton btnExitGame = new JButton("Exit Game");
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Exit Game?", "Game Caro", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) System.exit(0); 			// thoát game
			}
		});
		
		btnExitGame.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnExitGame.setOpaque(false);
		btnExitGame.setBorder(new TitledBorder(cellBorder));
		btnExitGame.setBackground(new Color(255, 20, 147));	
		btnExitGame.setForeground(new Color(0,128,0));							// màu chu
		btnExitGame.setBounds(156, 315, 89, 37);
		view.add(btnExitGame);
		
		
		
	
		//BUTTON INTRODUCE
		
		JButton btnIntroduce = new JButton("Introduce");
		btnIntroduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contenPane, "HƯỚNG DẪN TRÒ CHƠI"
						+ "\n+ Game cờ caro với chế độ người đánh với máy."
						+ "\n+ Bàn cờ có 19x19 ô vuông. Người chơi là X, AI là O."
						+ "\n+ Khi đến lượt mình, người chơi phải tích vào một ô trên bàn cờ."
						+ "\n+ Tích đủ 5 ô theo chiều dọc hoặc chiều ngang hoặc đường chéo thì sẽ thắng."); 			// hướng dẫn trò chơi
			}
		});
		
		
		btnIntroduce.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnIntroduce.setOpaque(false);
		btnIntroduce.setBorder(new TitledBorder(cellBorder));
		btnIntroduce.setBackground(new Color(255, 20, 147));	
		btnIntroduce.setForeground(new Color(0,128,0));							// màu chữ
		btnIntroduce.setBounds(30, 375, 89, 37);
		view.add(btnIntroduce);
		
		
		
		// GIỚI THIỆU
		
		JButton btnInfo = new JButton("Info");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contenPane, "THÔNG TIN"
						+ "\nHọ tên: Lê Thị Yến Loan - B1910094"
						+ "\nHọc phần: CT466 - 09"
						+ "\nĐề tài: Xây dựng trò chơi cờ caro"); 			// hướng dẫn trò chơi
			}
		});
		
		
		btnInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnInfo.setOpaque(false);
		btnInfo.setBorder(new TitledBorder(cellBorder));
		btnInfo.setBackground(new Color(255, 20, 147));	
		btnInfo.setForeground(new Color(0,128,0));							// màu chữ
		btnInfo.setBounds(156, 375, 89, 37);
		view.add(btnInfo);
		
		
		
		// ĐỔI CHẾ ĐỘ CHƠI: Ai đánh cờ trước ?
		
		//sử dụng JRadioButton
		
		JRadioButton UserPlayFirst =new JRadioButton("User play first");
		JRadioButton AIPlayFirst =new JRadioButton("AI play first");

		//button radio user
		UserPlayFirst.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		buttonGroup.add(UserPlayFirst);
		UserPlayFirst.setOpaque(false);										// làm mờ nền cho button
		UserPlayFirst.setBounds(26, 192, 232, 23);
		UserPlayFirst.setForeground(new Color(0,128,0));					// màu chữ button
		view.add(UserPlayFirst);

		
		//button radio AI
		AIPlayFirst.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		buttonGroup.add(AIPlayFirst);
		AIPlayFirst.setOpaque(false);
		AIPlayFirst.setBounds(26, 230, 232, 23);
		AIPlayFirst.setForeground(new Color(0,128,0));
		view.add(AIPlayFirst);
		
		
		
		// AI first or User first
		if (getMode() == 0) UserPlayFirst.setSelected(true);								// mặc định user đánh trước
		else AIPlayFirst.setSelected(true);
		
		AIPlayFirst.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(getMode() == 0) {					
					int kq = JOptionPane.showConfirmDialog(null, "Bạn muốn máy đánh cờ trước?","Xác nhận",JOptionPane.YES_NO_OPTION);
					if(kq== JOptionPane.YES_OPTION) {
						setMode(1);				
						newGame();
						
					}
				}
			}
		});
		
		UserPlayFirst.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(getMode() == 1) {														// may dang danh truoc
					int kq = JOptionPane.showConfirmDialog(null, "Bạn muốn đánh cờ trước?","Xác nhận",JOptionPane.YES_NO_OPTION);
					if(kq== JOptionPane.YES_OPTION) {
						setMode(0);															// user danh truoc
						newGame();
						
					}
				}
			}
		});
	}
	
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	
	public void newGame() {																	
		caro = new CaroAI(getMode());
		aiClickedCell = null;
		for (int i = 0; i < Value.SIZE; i++) {
			for (int j = 0; j < Value.SIZE; j++) {
				cell[i][j].setBackground(Color.darkGray);
				cell[i][j].setForeground(Color.red);
				cell[i][j].setText("");
			}
		}
		
		if(getMode() == 1) {
			// cập nhật nước đi của AI
			int x = caro.getNextX();
			int y = caro.getNextY();
			updateTableCells(x, y, Value.AI_VALUE);
		}
		
	}
	

	public void updateTableCells(int x, int y, int player) {
		if(player == Value.AI_VALUE) {
			if(aiClickedCell != null) {
				aiClickedCell.setBackground(Color.darkGray); 								// đặt lại màu clickedCell cũ
			}
			aiClickedCell = cell[x][y];
			aiClickedCell.setForeground(Color.green);
			aiClickedCell.setText("O");
			aiClickedCell.setBackground(Color.white); 										// làm nổi bật cell được AI chọn
		}
		else {
			cell[x][y].setBackground(Color.darkGray); 										// đặt lại màu clickedCell cũ
			cell[x][y].setText("X");
		}
	}
	
	private boolean checkResult(int player) {
		if(player == Value.USER_VALUE) {
			boolean result = caro.checkWinner(Value.USER_VALUE);
			if(result == true) {
				System.out.println("User thắng!");
				JOptionPane.showMessageDialog(null, "Bạn đã thắng!");
				newGame();
				return true; // kết thúc ván
			}
		}
		else {
			boolean result = caro.checkWinner(Value.AI_VALUE);
			if(result == true) {
				System.out.println("AI thắng!");
				JOptionPane.showMessageDialog(null, "AI đã thắng!");
				newGame();
				return true;
			}
		}
		if(caro.isOver()) {
			System.out.println("Hòa!");
			JOptionPane.showMessageDialog(null, "Hòa!");
			newGame();
			return true;
		}
		return false;
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		//
		int x =0, y=0;
		for (int i = 0; i< Value.SIZE; i++) {
			for (int j = 0 ; j < Value.SIZE; j++) {
				if(cell[i][j] == e.getSource()) {
					x= i;
					y = j;
					break;
				}
			}
		}
					
		if(caro.isClickable(x, y)) {											// nếu ô này chưa được đánh
			caro.update(x, y, Value.USER_VALUE); 								// update matrix
			updateTableCells(x, y, Value.USER_VALUE);							// cap nhat buoc di cua user
			
			// AI danh tiep
			if(checkResult(Value.USER_VALUE)) return;
			caro.nextStep();
			
			x = caro.getNextX();
			y = caro.getNextY();
			updateTableCells(x, y, Value.AI_VALUE);
			if(checkResult(Value.AI_VALUE)) return;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
