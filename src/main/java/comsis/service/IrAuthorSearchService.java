package comsis.service;

import comsis.core.model.Author;
import comsis.core.model.AuthorIndexModel;
import comsis.core.model.index.AuthorIndexProperty;
import comsis.core.serviceInterface.AuthorSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IrAuthorSearchService implements AuthorSearchService {

    @Autowired
    private IrAuthorIndexService authorIndexService;


    @Override
    public Author getAuthorById(String query, int numberOfHits) {
        List<Author> authors = authorIndexService.searchIndex(AuthorIndexProperty.ID, query, 1)
                .stream().map(AuthorParser::parseAuthor).collect(Collectors.toList());

        if(authors.size() == 0) {
            return null;
        }

        return authors.get(0);
    }

    @Override
    public List<Author> getAuthorsByEmail(String query, int numberOfHits) {
        return authorIndexService.searchIndex(AuthorIndexProperty.EMAIL_ADDRESS, query, 1)
                .stream().map(AuthorParser::parseAuthor).collect(Collectors.toList());
    }

    @Override
    public List<Author> getAuthorsByName(String query, int numberOfHits) {
        return authorIndexService.searchIndex(AuthorIndexProperty.FULL_NAME, query, 1)
                .stream().map(AuthorParser::parseAuthor).collect(Collectors.toList());
    }

    @Override
    public List<Author> getAuthorsByInstitution(String query, int numberOfHits) {
        return authorIndexService.searchIndex(AuthorIndexProperty.INSTITUTION, query, 1)
                .stream().map(AuthorParser::parseAuthor).collect(Collectors.toList());
    }


}
