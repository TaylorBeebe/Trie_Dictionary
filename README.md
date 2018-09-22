# Trie_Dictionary
Load any dictionary or individual words into the data structure. Use regular expressions to find words matching a pattern and use the built in spell-checker if you forget how to spell!

Requires structure5 to compile located here: http://www.cs.williams.edu/~bailey/JavaStructures/Software.html

Main.java: Used for testing.
LexiconTrie: Has functions like add, remove, search with regular expressions, and spelling corrections
LexiconNode: The trie is made up of LexiconNode objects to store letters
ospdt.txt a text file containing words, can be loaded into the LexiconTrie class with addWordsFromFile(string filename)
NodeVector, MyVector: Custom vector classes to suit the project
