package exec07;

import java.util.LinkedHashMap;
import java.util.Map;

public class Index {
    private final Map<Integer, Long> indice = new LinkedHashMap<>();

    public void addIndex(int id, long offset) {
        indice.put(id, offset);
    }

    public Long getOffset(int id) {
        return indice.get(id);
    }

    public void indexRemover(int id) {
        indice.remove(id);
    }

    public Map<Integer, Long> getTodos() {
        return indice;
    }

    public void indexClear() {
        indice.clear();
    }
}
