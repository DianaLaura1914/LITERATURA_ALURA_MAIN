package literatura.literatura.models;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Book {
		
	private long id;
	 private String title;
	    private String author;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
	    
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public List<Book> parseBooks(String jsonResponse) throws Exception {
		    ObjectMapper objectMapper = new ObjectMapper();
		    return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class));
		}

}
