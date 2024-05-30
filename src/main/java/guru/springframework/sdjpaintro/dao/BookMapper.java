package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .id(rs.getLong("id"))
                .isbn(rs.getString("isbn"))
                .publisher(rs.getString("publisher"))
                .title(rs.getString("title"))
                .authorId(rs.getLong("author_id"))
                .build();
    }
}
