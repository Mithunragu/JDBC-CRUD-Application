package com.jdbcproject.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import com.jdbcproject.demo.model.Response;
import com.jdbcproject.demo.model.SignUpModel;
import com.jdbcproject.demo.service.SignUpService;

@Component
public class SignUpDao implements SignUpService {

	Response res = new Response();

	String url = "jdbc:mysql://127.0.0.1:3306/kgm";
	String user = "root";
	String pass = "two5062001";

	@Override
	public Response createUser(SignUpModel datas) {

		String uuid = UUID.randomUUID().toString();
		datas.setsNo(uuid);
		datas.setCreatedBy(uuid);
		datas.setUpdatedBy(uuid);

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		datas.setCreatedDate(date);
		datas.setUpdatedDate(date);
		datas.setIsActive(1);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, user, pass);
					Statement st = conn.createStatement();) {
				String insertQuery = "INSERT INTO kgm.user_list(s_no,first_name,last_name,email,phone_number,password,age,gender,dob,location,created_by,updated_by,created_date,updated_date,is_active)"
						+ "VALUES('" + datas.getsNo() + "','" + datas.getFirstName() + "','" + datas.getLastName()
						+ "','" + datas.getEmail() + "','" + datas.getPassword() + "'," + datas.getPhoneNumber() + ""
						+ "," + datas.getAge() + ",'" + datas.getGender() + "','" + datas.getDob() + "','"
						+ datas.getLocation() + "','" + datas.getCreatedBy() + "','" + datas.getUpdatedBy() + "','"
						+ datas.getCreatedDate() + "','" + datas.getUpdatedDate() + "'," + datas.getIsActive() + ");";

				System.out.println(insertQuery);
				st.executeUpdate(insertQuery);

				res.setResponseCode(200);
				res.setResponseMsg("Success");
				res.setData("User created Successfully");

			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseCode(500);
				res.setResponseMsg("Errors");
				res.setData("Internal Server error Mithun Boy");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// =============================================
	@Override
	public Response deleteUser(String sNo) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, user, pass);
					Statement st = conn.createStatement();) {

				String deleteQuery = "DELETE FROM kgm.user_list WHERE s_no = '" + sNo + "';";
				System.out.println(deleteQuery);

				st.executeUpdate(deleteQuery);

				res.setResponseMsg("success");
				res.setResponseCode(200);
				res.setData("User Deleted Successfully!");

			} catch (Exception e) {
				e.printStackTrace();

				res.setResponseMsg("error");
				res.setResponseCode(500);
				res.setData("Internal Server Error!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

//======================================
	@Override
	public Response updateAllUser(SignUpModel datas) {

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		datas.setCreatedDate(date);
		datas.setUpdatedDate(date);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, pass);
					Statement st = conn.createStatement();) {

				String updateQuery = "UPDATE kgm.user_list SET first_name='" + datas.getFirstName() + "',last_name ='"
						+ datas.getLastName() + "',email ='" + datas.getEmail() + "',phone_number ="
						+ datas.getPhoneNumber() + ",password ='" + datas.getPassword() + "',age =" + datas.getAge()
						+ ",gender ='" + datas.getGender() + "',dob ='" + datas.getDob() + "',location ='"
						+ datas.getLocation() + "',created_date ='" + datas.getCreatedDate() + "',updated_date ='"
						+ datas.getUpdatedDate() + "'" + "WHERE s_no ='" + datas.getsNo() + "' ;";
				System.out.println(updateQuery);
				st.executeUpdate(updateQuery);

				res.setResponseMsg("success");
				res.setResponseCode(200);
				res.setData("User Updated Successfully!");

			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("error");
				res.setResponseCode(500);
				res.setData("Internal Server Error!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

//============================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public Response getAllUser() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String selectAllQuery = "SELECT * FROM kgm.user_list WHERE is_active=1;";
			try (Connection conn = DriverManager.getConnection(url, user, pass);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectAllQuery);) {

				JSONArray jsonArray = new JSONArray();
				while (rs.next()) {

					JSONObject jsonobject = new JSONObject();

					jsonobject.put("firstName", rs.getString("first_name"));
					jsonobject.put("lastName", rs.getString("last_name"));
					jsonobject.put("age", rs.getInt("age"));

					jsonArray.add(jsonobject);

					res.setResponseMsg("success");
					res.setResponseCode(200);
					res.setData("User Updated Successfully!");

					res.setjData(jsonArray);
				}
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("error");
				res.setResponseCode(500);
				res.setData("Internal Server Error!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

//======================================================================================================
	@Override
	public Response loginUser(String email, String password) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String SelectQuery = "SELECT * FROM kgm.user_list WHERE email='" + email + "' and password='" + password
					+ "';";

			try (Connection conn = DriverManager.getConnection(url, user, pass);
					PreparedStatement pst = conn.prepareStatement(SelectQuery);
					ResultSet rs = pst.executeQuery(SelectQuery);) {

				if (rs.next()) {
					res.setResponseCode(200);
					res.setResponseMsg("Success");
					res.setData("Login Successfull!");
				} else {

					res.setResponseCode(200);
					res.setResponseMsg("Success");
					res.setData("User Does Not Exit! Create An Account!");
				}

			} catch (Exception e) {
				e.printStackTrace();

				res.setResponseMsg("error");
				res.setResponseCode(500);
				res.setData("Internal Server Error!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
//===================================================================
	@Override
	public Response softdeleteUser(String sNo) {

		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn=DriverManager.getConnection(url, user, pass);
				Statement st=conn.createStatement();){
				String softQuery="UPDATE kgm.user_list SET is_active=0 WHERE s_no='"+sNo+"';";
				System.out.println(softQuery);
				
				st.executeUpdate(softQuery);
				
				res.setResponseCode(200);
				res.setResponseMsg("Success");
				res.setData("User Deleted Succesfully");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseMsg("error");
				res.setResponseCode(500);
				res.setData("Internal Server Error!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

}
