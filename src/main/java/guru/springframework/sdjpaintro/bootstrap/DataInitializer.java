package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;

    @Autowired
    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
//        bookRepository.deleteAll();
//
//        Book bookDDD = Book.builder()
//                .title("Domain Driven Design")
//                .isbn("123")
//                .publisher("RandomHouse")
//                .build();
//        System.out.println("Id: " + bookDDD.getId());
//        Book savedDDD = bookRepository.save(bookDDD);
//        System.out.println("Id: " + savedDDD.getId());
//
//        Book bookSIA = Book.builder()
//                .title("Spring in Action")
//                .isbn("234234")
//                .publisher("Mining")
//                .build();
//        System.out.println("Id: " + bookSIA.getId());
//        Book savedSIA = bookRepository.save(bookSIA);
//        System.out.println("Id: " + savedSIA.getId());
//
//        bookRepository.findAll().forEach(book -> {
//            System.out.println("Book id: " + book.getId());
//            System.out.println("Book title: " + book.getTitle());
//        });
    }
}
