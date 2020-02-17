package comsis.service;

import comsis.core.mapperinterface.AuthorMapper;
import comsis.core.model.Author;
import comsis.core.serviceInterface.AuthorIndexService;
import comsis.core.serviceInterface.AuthorService;
import comsis.core.serviceInterface.PublicationIndexService;
import comsis.data.entity.AuthorDto;
import comsis.data.repositoryInterface.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class IrAuthorService implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorIndexService authorIndexService;

    @Override
    public Author getOrCreate(Author author) {
        AuthorDto existingAuthor = authorRepository.findByEmail(author.getEmail());

        if(existingAuthor != null) {
            return authorMapper.toServiceModel(existingAuthor);
        }

        AuthorDto newAuthor = authorRepository.save(authorMapper.toDto(author));

        return authorMapper.toServiceModel(newAuthor);
    }

    @Override
    public void indexAuthors() {
        Iterator<AuthorDto> authorDtos = authorRepository.findAll().iterator();
        List<Author> authors = new ArrayList<>();
        while(authorDtos.hasNext()){
           authors.add(authorMapper.toServiceModel(authorDtos.next()));
        }

        authorIndexService.indexAuthors(authors);
    }
}
