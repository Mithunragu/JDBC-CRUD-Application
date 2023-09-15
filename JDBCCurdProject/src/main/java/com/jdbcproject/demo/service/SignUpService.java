package com.jdbcproject.demo.service;

import org.springframework.stereotype.Service;
import com.jdbcproject.demo.model.Response;
import com.jdbcproject.demo.model.SignUpModel;

@Service
public interface SignUpService {

	public Response createUser(SignUpModel datas);

	public Response deleteUser(String sNo);

	public Response updateAllUser(SignUpModel datas);

	public Response getAllUser();

	public Response loginUser(String email,String password);

	public Response softdeleteUser(String sNo);

}
