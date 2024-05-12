package com.hamza.librarymanagementsystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(BookDao bookDao){
//		return args -> {
//			if(bookDao.selectAllBooks() == null){
//				Book book = new Book(
//						"java",
//						"hamza",
//						2024,
//						21345122
//				);
//				bookDao.addBook(book);
//			}
//		};
//	}

}
