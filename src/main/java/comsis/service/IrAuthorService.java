package comsis.service;

import comsis.core.mapperinterface.AuthorMapper;
import comsis.core.model.Author;
import comsis.core.serviceInterface.AuthorService;
import comsis.data.entity.AuthorDto;
import comsis.data.repositoryInterface.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrAuthorService implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public Author getOrCreate(Author author) {
        AuthorDto existingAuthor = authorRepository.findByEmail(author.getEmail());

        if(existingAuthor != null) {
            return authorMapper.toServiceModel(existingAuthor);
        }

        AuthorDto newAuthor = authorRepository.save(authorMapper.toDto(author));

        return authorMapper.toServiceModel(newAuthor);
    }
}
