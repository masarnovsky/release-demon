package by.masarnovsky.releasedemon.service;

import java.util.List;

public interface UserLibraryRetriever {
    List<String> retrieve(String username);
}
