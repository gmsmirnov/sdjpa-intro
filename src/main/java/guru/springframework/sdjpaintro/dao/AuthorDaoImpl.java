package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", getRowMapper(), id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE first_name = ? AND last_name = ?",
                getRowMapper(),
                firstName, lastName);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO author (first_name, last_name) VALUES (?, ?)",
                author.getFirstName(), author.getLastName());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class); // MySQL specific function
        author.setId(id);
        return author;
    }

    @Override
    public void updateAuthor(Author author) {
    }

    @Override
    public void deleteAuthor(Author author) {
    }

    private Author getAuthorFromResultSet(ResultSet rs) throws SQLException {
        return Author.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();
    }

    private RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
