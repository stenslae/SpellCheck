class Word implements Comparable<Word> {
    String word;
    int distance;

    public Word(String word, int distance) {
        this.word = word;
        this.distance = distance;
    }
    
    public String getWord() {
    	return word;
    }
    public int getDistance() {
    	return distance;
    }

    public int compareTo(Word other) {
        return Integer.compare(this.distance, other.distance);
    }
}