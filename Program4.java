import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Program4 {
	private Scanner scanner = new Scanner(System.in);
	private HashSet<String> dictionary = new HashSet<String>();
    private ArrayList<String> misspelled = new ArrayList<String>();
	
	public void run() {
		// Allow for repeated inquiries
		loadSet("words.txt");
		while(true) {
			System.out.println("Enter a sentence: (type 'exit' to end program)");
			String response = this.scanner.nextLine().trim();
			
			// End loop
			if(response.equals("exit")) {
				System.out.println("Exiting program.");
				scanner.close();
				break;
			}
			
			// Spellcheck sentence and suggest spelling any misspelled words
			spellCheck(response);
			suggestWords();

		    // Clear the list so next input works cleanly
		    misspelled.clear();
        	
		}
	}
	
	// Takes file input and adds each word into a hashset dictionary
	public void loadSet(String name) {
		// read in words from the file and add words to hashset
		try {
			// Open the file
			File file = new File(name);
            Scanner scan = new Scanner(file);
            
            // iterate through the file
            while(scan.hasNextLine()) {
            	// read line and separate into two strings
            	String line = scan.nextLine().trim();
            	String[] parts = line.split("[ ,!?]+");
            	
            	for(int i=0; i<parts.length; i++) {
            		dictionary.add(parts[i]);
            	}
            }
			scan.close();
		}catch (FileNotFoundException e){
			System.out.println("File not found.");
		}
	}
	
	// Takes input sentence and checks it with dictionary and flags and saves any misspelled words
	public void spellCheck(String text) {
		String updatedText = "";
		boolean hasMisspelled = false;
		
		// Separate text so words, spaces, and puctuation is by themselves
		String[] tokens = text.split("(?<=\\b)(?=\\W)|(?<=\\W)(?=\\b)");
		
		// Iterates through individual words, spaces, and punctuation
		for(String word : tokens) {
			
			// Finds words in dictionary and re-adds spaces and punctuation
			String cleanedWord = word.toLowerCase().replaceAll("[^a-zA-Z]", "");
			if(this.dictionary.contains(cleanedWord) || cleanedWord.equals("")){
				updatedText += word;
			}else {
				// If word isn't in dictionary, it gets highlighted
				hasMisspelled = true;
				updatedText += "<" + word + ">";
				misspelled.add(word);
			}
		}
		
		if (!hasMisspelled) {
		    System.out.println("Good job! No misspelled words.");
		} else {
			System.out.println(updatedText + "\n");
		}
		return;
	}

	// Builds a priority queue of words based on distance and adds top shortest distance words
	public void suggestWords() {
	    for (String w : misspelled) {
	        PriorityQueue<Word> pq = new PriorityQueue<>();

	        for (String dictWord : dictionary) {
	            int[][] dp = new int[w.length() + 1][dictWord.length() + 1];
	            for (int[] row : dp) Arrays.fill(row, -1);
	            int dist = editDistance(w, dictWord, w.length(), dictWord.length(), dp);
	            pq.offer(new Word(dictWord, dist));
	        }

	        System.out.println("Suggestions for <" + w + ">:");

	        // Print top 5 suggestions
	        for (int i = 0; i < 5 && !pq.isEmpty(); i++) {
	            Word suggestion = pq.poll();
	            System.out.println("  - Did you mean " + suggestion.word + "? (" + suggestion.distance + ")");
	        }
	        
	        System.out.print("\n");
	    }
	}
	
	// Uses dynamic programming to determine distance between two words
	private static int editDistance(String str1, String str2, int i, int j, int[][] dp) {
		
		// Base cases
		if(i == 0) {
			return j;
		}
		else if(j == 0) {
			return i;
		}
		// Check memoization table to see if we already have answer
		if(dp[i][j] != -1) {
			return dp[i][j];
		}
		
		if(str1.charAt(i-1) == str2.charAt(j-1)) {
			int x = editDistance(str1, str2, i-1, j-1, dp);
			dp[i][j] = x;
			return dp[i][j];
		}
		
		// Try space in x
		int x_space = 1 + editDistance(str1, str2, i, j-1, dp);
		// Try space in y
		int y_space = 1 + editDistance(str1, str2, i-1, j, dp);
		// No spaces, align characters normally
		int misalign = 1 + editDistance(str1, str2, i-1, j-1, dp);
		
		int solution = Math.min( Math.min(x_space,  y_space), misalign );
		dp[i][j] = solution;
		return dp[i][j];
		
	}
}
