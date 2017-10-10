package tonnetzgenerator;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class TonnetzGenerator implements ListSelectionListener {
	
	JFrame frame;
	
	JList<String> list;
	JScrollPane scrollPane;
	
	JLabel prompt;
	JLabel top;
	JLabel middle;
	JLabel bottom;

	String[] notesSharp = { "A", "A#", "B", "C", "C#", "D", 
			"D#", "E", "F", "F#", "G", "G#"};
	
	String[] notesFlat = { "A", "Bb", "B", "C", "Db", "D", 
			"Eb", "E", "F", "Gb", "G", "Ab"};
	
	StringBuilder sb;
	
	public TonnetzGenerator() {
		frame = new JFrame("TonnetzGenerator");
		frame.setLayout(new GridLayout(5, 1));
		frame.setSize(300, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		list = new JList<>(notesSharp);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(120, 90));
		
		list.addListSelectionListener(this);
		
		prompt = new JLabel("Select a note.");
		top = new JLabel("----");
		middle = new JLabel("----");
		bottom = new JLabel("----");
		
		prompt.setHorizontalAlignment(SwingConstants.CENTER);
		top.setHorizontalAlignment(SwingConstants.CENTER);
		middle.setHorizontalAlignment(SwingConstants.CENTER);
		bottom.setHorizontalAlignment(SwingConstants.CENTER);
		
		frame.add(prompt);
		frame.add(scrollPane);
		frame.add(top);
		frame.add(middle);
		frame.add(bottom);
		
		frame.setVisible(true);
	}
	
	private void getLine(int index) {
		sb = new StringBuilder();
		
		for(int i=0; i < 5; i++) {
			if(index >= notesSharp.length) {
				index -= notesSharp.length;
			} else if(index < 0) {
				index += notesSharp.length;
			}
			sb.append(notesSharp[index]).append("    ");
			index += 7;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		int index = list.getSelectedIndex();
		
		if(index == -1) {
			return;
		}
		
		getLine(index);
		middle.setText(sb.toString());
		
		getLine(index - 3);
		top.setText(sb.toString());
		
		getLine(index - 4);
		bottom.setText(sb.toString());
		return;
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TonnetzGenerator();
			}
		});
	}


}
