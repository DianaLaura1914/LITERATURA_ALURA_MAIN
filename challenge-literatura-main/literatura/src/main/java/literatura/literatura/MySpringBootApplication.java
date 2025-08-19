package literatura.literatura;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import literatura.literatura.models.Book;
import literatura.literatura.service.BookService;

@SpringBootApplication
public class MySpringBootApplication {
	 private static final String API_BASE_URL = "http://localhost:8080/api/books";
	 static Scanner scanner = new Scanner(System.in);
     BookService bookService = new BookService();
     private static final ObjectMapper objectMapper = new ObjectMapper();
     private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
        showMainMenu();
       

    }
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== CATÁLOGO DE LIBROS ===");
            System.out.println("1. Mostrar todos los libros");
            System.out.println("2. Buscar libro por título");
            System.out.println("3. Agregar nuevo libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int option = 0;
            try {
                try {
					option = Integer.parseInt(scanner.nextLine());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número.");
                continue;
            }

            switch (option) {
                case 1:
                    showAllBooks();
                    break;
                case 2:
                    searchBookByTitle();
                    break;
                case 3:
                    addNewBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    System.out.println("¡Gracias por usar el catálogo de libros!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    private static void showAllBooks() {
        try {
        	String response = restTemplate.getForObject(API_BASE_URL, String.class);
            List<Book> books = objectMapper.readValue(response, new TypeReference<List<Book>>() {});
            
            System.out.println("\n=== LISTA DE LIBROS ===");
            if (books.isEmpty()) {
                System.out.println("No hay libros registrados.");
            } else {
                System.out.printf("%-5s %-30s %-25s\n", "ID", "TÍTULO", "AUTOR");
                books.forEach(book -> 
                    System.out.printf("%-5d %-30s %-25s\n", 
                        book.getId(), 
                        book.getTitle(), 
                        book.getAuthor()));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los libros: " + e.getMessage());
        }
        waitForEnter();
    }

    private static void searchBookByTitle() {
        System.out.print("\nIngrese el título o parte del título a buscar: ");
        String searchTerm = scanner.nextLine();
        
        try {
            String response = restTemplate.getForObject(API_BASE_URL + "/search?title=" + searchTerm, String.class);
            List<Book> books = objectMapper.readValue(response, new TypeReference<List<Book>>() {});
            
            System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
            if (books.isEmpty()) {
                System.out.println("No se encontraron libros con ese criterio.");
            } else {
                System.out.printf("%-5s %-30s %-25s\n", "ID", "TÍTULO", "AUTOR");
                books.forEach(book -> 
                    System.out.printf("%-5d %-30s %-25s\n", 
                        book.getId(), 
                        book.getTitle(), 
                        book.getAuthor()));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar libros: " + e.getMessage());
        }
        waitForEnter();
    }

    private static void addNewBook() {
        System.out.println("\n=== AGREGAR NUEVO LIBRO ===");
        
        try {
            System.out.print("Título: ");
            String title = scanner.nextLine();
            
            System.out.print("Autor: ");
            String author = scanner.nextLine();
            
            Book newBook = new Book();
            newBook.setTitle(title);
            newBook.setAuthor(author);
            
            restTemplate.postForObject(API_BASE_URL, newBook, Book.class);
            System.out.println("\nLibro agregado exitosamente!");
        } catch (Exception e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
        }
        waitForEnter();
    }

    private static void deleteBook() {
        System.out.println("\n=== ELIMINAR LIBRO ===");
        showAllBooks();
        
        try {
            System.out.print("\nIngrese el ID del libro a eliminar: ");
            long id = Long.parseLong(scanner.nextLine());
            
            restTemplate.delete(API_BASE_URL + "/" + id);
            System.out.println("Libro eliminado exitosamente!");
        } catch (NumberFormatException e) {
            System.out.println("ID no válido. Debe ser un número.");
        } catch (Exception e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
        }
        waitForEnter();
    }

    private static void waitForEnter() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

}
