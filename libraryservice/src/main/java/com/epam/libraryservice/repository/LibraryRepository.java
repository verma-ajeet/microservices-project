package com.epam.libraryservice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.libraryservice.entity.LibraryEntity;

@Transactional
@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Integer> {

	List<LibraryEntity> findByUsername(String username);
	
	Optional<LibraryEntity> findByUsernameAndBookId(String username,int bookId);
    boolean removeByUsernameAndBookId(String username,int bookId);
}
