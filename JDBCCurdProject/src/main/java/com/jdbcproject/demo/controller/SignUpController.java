package com.jdbcproject.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jdbcproject.demo.dao.SignUpDao;
import com.jdbcproject.demo.model.Response;
import com.jdbcproject.demo.model.SignUpModel;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class SignUpController {

	@Autowired
	private SignUpDao dao;

	@PostMapping("/create")
	public ResponseEntity<Response> createUser(@RequestBody SignUpModel datas) {
		return ResponseEntity.ok(dao.createUser(datas));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteUser(@RequestParam String sNo) {
		return ResponseEntity.ok(dao.deleteUser(sNo));
	}

	@PutMapping("/updateall")
	public ResponseEntity<Response> updateAllUser(@RequestBody SignUpModel datas) {
		return ResponseEntity.ok(dao.updateAllUser(datas));
	}

	@GetMapping("/getall")
	public ResponseEntity<Response> getAllUser() {
		return ResponseEntity.ok(dao.getAllUser());
	}

	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestParam String email, @RequestParam String password) {
		return ResponseEntity.ok(dao.loginUser(email, password));
	}
	
	@PostMapping("/softdelete")
	public ResponseEntity<Response> softdeleteuser(@RequestParam String sNo){
		return ResponseEntity.ok(dao.softdeleteUser(sNo));
		
	}

}