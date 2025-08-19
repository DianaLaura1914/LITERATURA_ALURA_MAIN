package literatura.literatura.service;

import org.springframework.web.client.RestTemplate;

public class BookService {
	
	 private final String API_URL = "http://localhost:8080/api/books";
	    public String getBooks() {
	        RestTemplate restTemplate = new RestTemplate();
	        return restTemplate.getForObject(API_URL, String.class);
	    }
}
