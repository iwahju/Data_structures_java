
package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        List<String> result = new ArrayList<>();
        TrieNode<List<String>> root = find(prefix);
        if (root == null) {
            put(prefix, List.of(prefix));
            return List.of(prefix);
        }
        traverseNode(root, prefix, result);
        result.sort((x, y) -> {
            if (x.length() == y.length()) {
                return x.compareTo(y);
            }
            return Integer.compare(x.length(), y.length());
        });
        result.remove(prefix);
        List<String> candidates = new ArrayList<>();
        if (root.hasValue()) {
            candidates.addAll(root.getValue());
            Collections.reverse(candidates);
        }
        for (String s : result) {
            if (candidates.size() == getMax()) break;
            if (candidates.contains(s)) continue;
            candidates.add(s);
        }
        if (candidates.size() == 0) return List.of(prefix);
        return candidates;
    }

    private void traverseNode(TrieNode<List<String>> node, String prefix, List<String> result) {
        if (node.isEndState()) {
            result.add(prefix);
        }
        for (Character c : node.getChildrenMap().keySet()) {
            traverseNode(node.getChildrenMap().get(c), (prefix + c), result);
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        String trimmed = candidate.trim();
        TrieNode<List<String>> node = find(prefix);
        if (node.hasValue()) {
            if (!node.getValue().contains(trimmed))
                node.getValue().add(trimmed);
        } else {
            put(prefix, new ArrayList<>(List.of(trimmed)));
            put(trimmed, null);
        }
    }
}