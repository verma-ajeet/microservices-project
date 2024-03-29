package com.epam.libraryservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.libraryservice.entity.LibraryEntity;
import com.epam.libraryservice.exceptions.BookCantBeIssuedException;
import com.epam.libraryservice.exceptions.RecordAlreadyExistsException;
import com.epam.libraryservice.exceptions.RecordNotFoundException;
import com.epam.libraryservice.feign.BookFeignClient;
import com.epam.libraryservice.feign.UserFeignClient;
import com.epam.libraryservice.repository.LibraryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LibraryService {

	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	BookFeignClient bookFeignClient;
	@Autowired
	UserFeignClient userFeignClient;
	@Autowired
	FeignService feignService;

	public String issueBookToUser(String username, int bookId) {
		log.info("book issue service started");
		userFeignClient.getUser(username);
		bookFeignClient.getBook(bookId);
		checkLibraryRecord(username,bookId);
		int numberOfBooksPerUser = getAllBookByUserName(username).size();
		if (numberOfBooksPerUser >= 1 && numberOfBooksPerUser <=2)
			libraryRepository.save(new LibraryEntity(username, bookId));
		else
			throw new BookCantBeIssuedException("you cant issue more than two books");
		return "book issued successfully to: " + username;
	}

	
//	public List<Object> getLibraryDataByUsername(String username) {
//		List<LibraryEntity> book = libraryRepository.findByUsername(username);
//		if(book.isEmpty())
//			throw new RecordNotFoundException("Record not found with name: "+username );
//		UserDTO body = userFeignClient.getUser(username).getBody();
//		ObjectMapper object=new ObjectMapper();
//		object.convertValue(body,UserDTO.class);
//		List<UserDTO> collect = book.stream().map(bookId->feignService.getUserByUserName(username).getBody()).collect(Collectors.toList());
//        Map<List<UserDTO>,List<LibraryEntity>> map=new HashMap<>();
//        map.put(collect, book);
//        Map convertValue = object.convertValue(map,Map.class);
//        System.out.println("\n\n"+convertValue);
//		return null;
//	}
//	
	public List<LibraryEntity> getAllRecords()
	{
		return libraryRepository.findAll();
	}
	
	public List<LibraryEntity> getAllBookByUserName(String username) {
		List<LibraryEntity> book = libraryRepository.findByUsername(username);
		if(book.isEmpty())
			throw new RecordNotFoundException("Record not found with name: "+username );
//		return book.stream().map(bookId->feignService.getBookByBookId(bookId).getBody()).collect(Collectors.toList());
		return book;
	}
	
	public void checkLibraryRecord(String username, int bookId)
	{
		if(libraryRepository.findByUsernameAndBookId(username, bookId).isPresent())
			throw new RecordAlreadyExistsException("Record with name: "+username+" and book id: "+bookId+" already exists");
	}
 
	public boolean deleteIssuedBook(String username, int bookId) {
		log.info("release issued book service started");
		Optional<LibraryEntity> findByUsernameAndBookId = libraryRepository.findByUsernameAndBookId(username, bookId);
		if (!findByUsernameAndBookId.isPresent())
			throw new RecordNotFoundException("Record not found with the given data");
		log.info("release issued book service ended");
		return libraryRepository.removeByUsernameAndBookId(username, bookId);
	}

}
