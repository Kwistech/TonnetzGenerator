package tonnetzgenerator;

/*
 * TonnetzGenerator is a desktop application that displays a
 * tone network (Tonnetz) of a given starting note.
 * 
 * This is useful for composers who want to have a birds-eye view 
 * of compositions.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class TonnetzGenerator implements ListSelectionListener {
	
	// Set the initial Swing variables.
	JFrame frame;
	
	JList<String> list;
	JScrollPane scrollPane;
	
	JLabel prompt;
	JLabel tonnetz;
	JLabel top;
	JLabel middle;
	JLabel bottom;
	
	Font monospacedTitle = new Font("Monospaced", Font.BOLD, 24);
	Font monospacedText = new Font("Monospaced", Font.BOLD, 14);
	
	// Set additional variables and data.
	String[] notesSharp = { "A", "A#", "B", "C", "C#", "D", 
			"D#", "E", "F", "F#", "G", "G#"};
	
	String[] notesFlat = { "A", "Bb", "B", "C", "Db", "D", 
			"Eb", "E", "F", "Gb", "G", "Ab"};
	
	StringBuilder sb, space;

	// Construct the GUI for the application.
	public TonnetzGenerator() {
		// Set all Swing components.
		frame = new JFrame("TonnetzGenerator");
		frame.setLayout(new GridLayout(6, 1));
		frame.setSize(450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		list = new JList<>(notesSharp);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(120, 90));
		
		list.addListSelectionListener(this);
		
		prompt = new JLabel("Select a note");
		tonnetz = new JLabel("Tonnetz: ");
		top = new JLabel("----");
		middle = new JLabel("----");
		bottom = new JLabel("----");
		
		prompt.setHorizontalAlignment(SwingConstants.CENTER);
		tonnetz.setHorizontalAlignment(SwingConstants.CENTER);
		top.setHorizontalAlignment(SwingConstants.CENTER);
		middle.setHorizontalAlignment(SwingConstants.CENTER);
		bottom.setHorizontalAlignment(SwingConstants.CENTER);

		prompt.setFont(monospacedTitle);
		tonnetz.setFont(monospacedTitle);
		top.setFont(monospacedText);
		middle.setFont(monospacedText);
		bottom.setFont(monospacedText);
		
		frame.add(prompt);
		frame.add(scrollPane);
		frame.add(tonnetz);
		frame.add(top);
		frame.add(middle);
		frame.add(bottom);
		
		frame.setVisible(true);
		
		// Set additional program variables.
		space = new StringBuilder();
		space.append(String.format("%6s", " "));

	}

	private void getLine(int index, int limit) {
		sb = new StringBuilder();
		String note;
		
		for(int i=0; i < limit; i++) {
			// For handling ArrayIndexOutOfBounds possibility.
			while(index >= notesSharp.length) {
				index -= notesSharp.length;
			} 
			while(index < 0) {
				index += notesSharp.length;
			}
			
			// Highlights selected note in tonnetz output.
			if(notesSharp[index] == list.getSelectedValue()) {
				note = "[" + notesSharp[index] + "]";
			} else {
				note = notesSharp[index];
			}
			
			// Appends correct spacing to JLabel output.
			if(notesSharp[index].length() == 1) {
				sb.append(note)
				.append(String.format("%6s", " "));
			} else {
				sb.append(note)
				.append(String.format("%5s", " "));
			}

			// Increment by 7 semi-tones.
			index += 7;
			
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		// Sets the JLabels to correct tonnetz output.
		int index = list.getSelectedIndex();
		
		// No selection.
		if(index == -1) {
			return;
		}
		
		getLine(index - 14, 5);
		middle.setText(space.toString() + sb.toString() + space.toString());
		
		getLine(index - 3 - 14, 6);
		top.setText(sb.toString());
		
		getLine(index - 4 - 14, 6);
		bottom.setText(sb.toString());
		
		return;
	}
	
	// Create new instance of TonnetzGenerator on event-dispatch thread.
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TonnetzGenerator();
			}
		});
	}


}
