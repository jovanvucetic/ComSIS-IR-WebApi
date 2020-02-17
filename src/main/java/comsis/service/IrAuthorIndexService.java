package comsis.service;

import comsis.core.model.Author;
import comsis.core.model.AuthorIndexModel;
import comsis.core.model.index.AuthorIndexProperty;
import comsis.core.serviceInterface.AuthorIndexService;
import comsis.core.utils.Constants;
import comsis.service.lucene.IrAuthorIndexer;
import comsis.service.lucene.IrAuthorSarcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrAuthorIndexService implements AuthorIndexService {

    private String authorIndexFolderPath;

    public IrAuthorIndexService(@Value("${authors.index.folder.path}")String authorIndexFolderPath) {
        this.authorIndexFolderPath = authorIndexFolderPath;
    }

    @Override
    public void indexAuthors(Collection<Author> authors) {
        List<AuthorIndexModel>  indexModels = authors.stream()
                .filter(a -> a.validateAuthor())
                .map(a -> new AuthorIndexModel(a))
                .collect(Collectors.toList());

        IrAuthorIndexer indexer = new IrAuthorIndexer(true, authorIndexFolderPath);
        indexer.indexAuthos(indexModels);
    }

    @Override
    public List<AuthorIndexModel> searchIndex(AuthorIndexProperty property, String query, int numberOfHits) {
        switch (property) {
            case ID:
                return searchForAuthor(Constants.Index.Author.ID_SEARCH_KEY, query, numberOfHits);
            case FULL_NAME:
                return searchForAuthor(Constants.Index.Author.NAME_SEARCH_KEY, query, numberOfHits);
            case EMAIL_ADDRESS:
                return searchForAuthor(Constants.Index.Author.EMAIL_SEARCH_KEY, query, numberOfHits);
            case INSTITUTION:
                return searchForAuthor(Constants.Index.Author.INSTITUTION_SEARCH_KEY, query, numberOfHits);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public List<AuthorIndexModel> getAllAuthors() {
        IrAuthorSarcher authorSearcher = new IrAuthorSarcher(authorIndexFolderPath);
        return authorSearcher.findAll();
    }

    private List<AuthorIndexModel> searchForAuthor(String field, String query, int numberOfHits) {
        IrAuthorSarcher authorSearcher = new IrAuthorSarcher(authorIndexFolderPath);
        return authorSearcher.search(field, query, numberOfHits);
    }
}
