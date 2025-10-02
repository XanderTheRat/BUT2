package kasiski;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repetition {
    public final String fragment;
    public final List<Integer> distances;

    public Repetition(String fragment, List<Integer> distances) {
        this.fragment = fragment;
        this.distances = distances;
    }

    public static List<Repetition> trouverRepetitions(String texte, int tailleMin) {
        Map<String, List<Integer>> fragments = new HashMap<>();
        for (int taille = texte.length(); taille >= tailleMin; taille--) {
            Map<String, List<Integer>> positions = new HashMap<>();
            for (int i = 0; i <= texte.length() - taille; i++) {
                String frag = texte.substring(i, i + taille);
                positions.computeIfAbsent(frag, k -> new ArrayList<>()).add(i);
            }
            for (Map.Entry<String, List<Integer>> entry : positions.entrySet()) {
                if (entry.getValue().size() > 1) {
                    List<Integer> dists = new ArrayList<>();
                    List<Integer> pos = entry.getValue();
                    for (int j = 1; j < pos.size(); j++) {
                        dists.add(pos.get(j) - pos.get(j - 1));
                    }
                    fragments.put(entry.getKey(), dists);
                }
            }
        }
        List<Repetition> repet = new ArrayList<>();
        fragments.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getKey().length(), a.getKey().length()))
                .forEach(e -> repet.add(new Repetition(e.getKey(), e.getValue())));
        return repet;
    }
}
