package search;

import java.util.Collection;
import java.util.Vector;

public interface StreamingServiceAdapter {
    void configurar(Vector<String> configParams);
    Collection<SearchResult> buscar(String query, Vector<String> searchParams);
    Collection<SearchResult> consultar(String query, Vector<String> consultParams);
}