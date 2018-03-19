package com.example.android.softkeyboard;

import java.util.*;

public class SuggestionTrie {
    public List<Word> wordList = new ArrayList<>();



    //    public static void main(String[] args){
//        SuggestionTrie suggestionTrie = new SuggestionTrie();
//        TrieNode trieNode = suggestionTrie.new TrieNode(null);
//        suggestionTrie.insert(trieNode, "hello",1);
//        suggestionTrie.insert(trieNode, "dog",2);
//        suggestionTrie.insert(trieNode, "hell",3);
//        suggestionTrie.insert(trieNode, "cat",4);
//        suggestionTrie.insert(trieNode, "a",5);
//        suggestionTrie.insert(trieNode, "hel",6);
//        suggestionTrie.insert(trieNode, "help",7);
//        suggestionTrie.insert(trieNode, "helps",8);
//        suggestionTrie.insert(trieNode, "helping",9);
//        int output = suggestionTrie.getAutoSuggestions(trieNode,"hel");
//
//        Collections.sort(suggestionTrie.wordList, new Word.WordFrequencyComparator());
//        List<Word> wordList1 = suggestionTrie.wordList;
//        for (int i = 0; i < 5; i++) {
//            Word word = wordList1.get(i);
//            System.out.println(word.str + " " + word.frequency);
//        }
//
//    }

    class TrieNode{
        Character c;
        Map<Character,TrieNode> node;
        boolean isCompleteWord;
        int frequency;

        public TrieNode(Character c) {
            this.c = c;
            node = new TreeMap<>();
            this.isCompleteWord = false;
        }
    }

    public void insert(TrieNode trieNode,String str,int freq){
        TrieNode currentNode;

        for (int i = 0; i < str.length(); i++)
        {
            if(!trieNode.node.containsKey(str.charAt(i))){
                currentNode = new TrieNode(str.charAt(i));
                trieNode.node.put(str.charAt(i),currentNode);

            }else{
                currentNode = trieNode.node.get(str.charAt(i));
            }
            trieNode = currentNode;

        }
        trieNode.isCompleteWord = true;
        trieNode.frequency = freq;
    }

    public void update(TrieNode trieNode,String str){
        for (int i = 0; i < str.length(); i++)
        {
            if(trieNode.node.containsKey(str.charAt(i))){
                trieNode = trieNode.node.get(str.charAt(i));
            }
        }
        trieNode.frequency = trieNode.frequency + 1;
    }



    public boolean search(TrieNode trieNode,String str){
        int length = str.length();
        for (int i = 0; i < length; i++)
        {
            if (!trieNode.node.containsKey(str.charAt(i))){
                return false;
            }
            trieNode = trieNode.node.get(str.charAt(i));
        }
        return (trieNode != null && trieNode.isCompleteWord);
    }

    public int getAutoSuggestions(TrieNode trieNode, String prefix){
        wordList.clear(); // to clear the already searched prfix and refreshing the list to give updated
        TrieNode currentNode = trieNode;
        // Check if prefix is present and find the
        // the node (of last level) with last character
        // of given string.
        int n = prefix.length();
        for (int i = 0; i < n; i++) {
            if (!currentNode.node.containsKey(prefix.charAt(i))){
                return 0;
            }
            currentNode = currentNode.node.get(prefix.charAt(i));
        }
        // If prefix is present as a word.
        boolean isWord = (currentNode.isCompleteWord == true);

        // If prefix is last node of tree (has no
        // children)
        boolean isLast = isLastNode(currentNode);

        // If prefix is present as a word, but
        // there is no subtree below the last
        // matching node.
        if (isWord && isLast)
        {
            return -1;
        }

        if (!isLast)
        {
            String str = prefix;
            suggestionsRec(currentNode, str);
            return 1;
        }


        return -1;
    }

    private void suggestionsRec(TrieNode currentNode, String currentPrefix) {
        // found a string in Trie with the given prefix
        if (currentNode.isCompleteWord)
        {
            wordList.add(new Word(currentPrefix,currentNode.frequency));
        }

        // All children node pointers are NULL
        if (isLastNode(currentNode))
            return;
        for (Map.Entry<Character,TrieNode> children :
                currentNode.node.entrySet()) {
            currentPrefix = currentPrefix.concat(String.valueOf(children.getKey()));
            suggestionsRec(currentNode.node.get(children.getKey()),currentPrefix);
            currentPrefix = currentPrefix.substring(0,currentPrefix.length()-1);
        }
    }


    public boolean isLastNode(TrieNode trieNode){
        if (!trieNode.node.isEmpty()){
            return false;
        }
        return true;
    }

    
}
