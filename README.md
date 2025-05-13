# Spelling Corrector using Dynamic Programming

## Project Overview
This program is a simple **spelling corrector** that identifies misspelled words in a sentence and suggests a list of the top five most similar words from the dictionary using **Edit Distance**, implemented with **Dynamic Programming**.

Given a user-input sentence, the program highlights misspelled words, calculates their edit distance from dictionary entries (`words.txt`), and provides correction suggestions. The core of the algorithm is based on lecture material from April 17.

---

## Features

### ✅ Misspelled Word Detection
- Words not found in the dictionary are surrounded by `<` and `>` (e.g., `I am <speking> to you`)
- Words are normalized (converted to lowercase, stripped of punctuation)

### 📏 Edit Distance Calculation
- Uses **Dynamic Programming** to compute edit distance between each misspelled word and dictionary entries
- Efficient and scalable for checking against large word lists

### 🔍 Suggest Top 5 Corrections
- For each misspelled word, suggests **top 5 closest words** from the dictionary
- Uses a `PriorityQueue<Word>` to sort candidates by ascending edit distance

### 🔁 Continuous Looping
- The program runs in a loop, allowing users to enter multiple sentences until they manually exit


---

## Acknowledgments
- Inspiration: This work was based on course materials provided by Reese Pearsall, CSCI 232: Data Structures and Algorithms & Data Structures and Algorithms, Montana State Univeristy - Bozeman.
- Libraries/Tools: All code is written in Java using standard libraries.
