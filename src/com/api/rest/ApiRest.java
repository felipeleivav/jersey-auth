package com.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.api.bean.UserBean;
import com.api.utils.Tokenizer;

@Path("/api")
public class ApiRest {

	// credenciales en duro
	private static String user = "felipe";
	private static String pass = "passwd";

	@POST
	@Path("/login")
	public String login(UserBean userLogin) throws Exception {
		if (user.equals(userLogin.getUsername()) && pass.equals(userLogin.getPassword())) {
			return Tokenizer.generateToken(userLogin.getUsername(), userLogin.getPassword());
		} else {
			return "WRONG_USER";
		}
	}

	@GET
	@Path("/dashboard")
	public String dashboard() {
		return "Esto es área restringida";
	}

	@GET
	@Path("/settings")
	public String settings() {
		return "Esto es otra área restringida";
	}

}
