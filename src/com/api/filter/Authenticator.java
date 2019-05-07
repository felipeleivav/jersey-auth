package com.api.filter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.api.utils.Tokenizer;

@Provider
@PreMatching
public class Authenticator implements ContainerRequestFilter {

	// credenciales en duro
	private static String user = "felipe";
	private static String pass = "passwd";
	
	public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
			String path = containerRequest.getUriInfo().getPath();
			String method = containerRequest.getMethod();

			// decimos que la api /login no requiere autenticación, logicamente :P
			if (method.equals("OPTIONS") || path.equalsIgnoreCase("api/login")) {
				return;
			}

			// extraemos el token de la cabecera HTTP
			String token = containerRequest.getHeaderString("Authorization");

			// si no viene el token, entonces arrojamos 401 no autorizado
			if (token == null) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

			// desencriptamos el token para obtener el usuario y contraseña
			String[] userData;
			try {
				userData = Tokenizer.readToken(token.substring(7));
			} catch (Exception e) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

			// si el usuario y contraseña no son validos, retornamos error 401 no autorizado
			if (!user.equals(userData[0]) || !pass.equals(userData[1])) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
	}
	
}
