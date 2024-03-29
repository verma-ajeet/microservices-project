package com.epam.libraryservice.feign;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.libraryservice.entity.UserDTO;

@Service
public class UserFeignClientImpl implements UserFeignClient {

	@Override
	public ResponseEntity<UserDTO> addUser(UserDTO userDTO) {
		userDTO.setName("name");
		userDTO.setEmail("email");
		userDTO.setPort("form fallback");
		userDTO.setUserName("username");
		System.out.println(" \n\n fallback \n\n");
		return new ResponseEntity<>(userDTO, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<UserDTO>> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UserDTO> getUser(String username) {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("name");
		userDTO.setEmail("email");
		userDTO.setPort("form fallback");
		userDTO.setUserName("username");
		return new ResponseEntity<>(userDTO, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<String> deleteUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> updateUser(String username, UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
