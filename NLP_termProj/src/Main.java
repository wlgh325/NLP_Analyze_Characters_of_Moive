import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// NLP_Analyze_Characters_of_Moive

public class Main {
//	public static String fileName = "";
	public static String inputSource = "";
	public static String pythonFilePath = "";
	public static String outputSource = "./Script/output/";
	public static String mode1 [] = {"NODE_WEIGHT_1", "NODE_WEIGHT_COUNT"};
	public static String mode2 [] = {"EDGE_WEIGHT_1", "EDGE_WEIGHT_MULTIPLY", "EDGE_WEIGHT_MULTIPLY_AND_COUNT"};
	public static int scriptChoose_flag = 0;
	public static int pythonChoose_flag = 0;
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Script analyze project");
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		JComboBox<String> comboBox1 = new JComboBox<String>(mode1);
		
		JComboBox<String> comboBox2 = new JComboBox<String>(mode2);
		
		JButton button = new JButton("분석 시작");
		JButton filechoose = new JButton("스크립트 선택");
		JButton pythonChoose = new JButton("파이썬 파일 선택");
		
		JLabel label1 = new JLabel("Mode1");
		JLabel label2 = new JLabel("Mode2");
		
		JFileChooser script_chooser = new JFileChooser();	// Script file 선택
		JFileChooser python_chooser = new JFileChooser();	// python file 선택 

		// 분석 시작 버튼 리스너
		ActionListener button_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// threshold 입력했는지 검사 후 분석 시작
				if(comboBox1.getSelectedItem() != null && comboBox2.getSelectedItem() != null && scriptChoose_flag != 0 &&
						pythonChoose_flag != 0) {
					
					// 스크립트 분석을 위한 ScriptAnalzer 생성
					ScriptAnalzer scriptAnalzer = new ScriptAnalzer();
					File dirFile = new File(inputSource);
					File[] inputFiles = dirFile.listFiles();
					String outputFilePath = "";
					
					try {
						File file2 = new File(inputSource);
						scriptAnalzer.analyzeScriptFile(inputSource);
						// 2. Python 그림 그려주는 프로그램에 들어갈 input형식에 맞추어 data write
						outputFilePath = scriptAnalzer.generateActorMapData(file2.getPath().substring(0, file2.getPath().length()-4), comboBox1.getSelectedIndex() + 1, comboBox2.getSelectedIndex() + 1);
					} catch(Exception ee) {
						ee.printStackTrace();
					}
					List<String> cmd = new ArrayList<String>();
					cmd.add("python");
					System.out.println(pythonFilePath);
					
					cmd.add(pythonFilePath);
					cmd.add(outputFilePath);
					
 					ProcessBuilder pb = new ProcessBuilder(cmd);
					String str = "";
					try {
						Process p = pb.start();
						BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
						// 표준출력 상태를 출력
						while( (str = in.readLine()) != null ) {
						    System.out.println(str);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "파일을 선택해주세요!");
				}
			}
		};
		
		// 스크립트 파일 선택 버튼 리스너
		ActionListener scriptChooseButton_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scriptChoose_flag = -1;
				int ret = script_chooser.showOpenDialog(null);  //열기창 정의
				 
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.",
							"경고", JOptionPane.WARNING_MESSAGE);
					return;
				}
				String filePath = script_chooser.getSelectedFile().getPath();  //파일경로를 가져옴
				inputSource = filePath;
			}
		};
		
		// 파이썬 파일 선택 버튼 리스너
		ActionListener pythonChooseButton_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pythonChoose_flag = -1;
				int ret = python_chooser.showOpenDialog(null);  //열기창 정의
				 
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.",
							"경고", JOptionPane.WARNING_MESSAGE);
					return;
				}
				 
				 
				String filePath = python_chooser.getSelectedFile().getPath();  //파일경로를 가져옴
				pythonFilePath = filePath;
				
			}
		};
		
		// 라벨 가운데 정렬
		label1.setHorizontalAlignment(JLabel.CENTER);
		label2.setHorizontalAlignment(JLabel.CENTER);
		
		// 버튼 리스너 추가
		button.addActionListener(button_listener);
		filechoose.addActionListener(scriptChooseButton_listener);
		pythonChoose.addActionListener(pythonChooseButton_listener);
		
		// GUI
		label1.setBackground(new Color(0,255,0));
		label2.setBackground(new Color(0,255,0));
		panel1.add(label1);
		panel1.add(comboBox1);
		panel1.add(label2);
		panel1.add(comboBox2);

		panel1.setLayout(new GridLayout(4,1));
		panel1.setSize(500,50);
		
		panel2.add(filechoose);
		panel2.add(pythonChoose);
		panel2.add(button);
		panel2.setLayout(new GridLayout(3,1));
		
		f.add(panel1);
		f.add(panel2);
		
		f.setLayout(new GridLayout(2,1));
		f.setSize(500,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
}
