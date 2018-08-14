package com.github.adminfaces.starter.infra.security;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;

@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

	@Inject
	private AdminConfig adminConfig;

	@Inject
	private SecurityContext securityContext;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private ExternalContext externalContext;

	private String password;

	private String email;

	private boolean remember;

	public void login() throws IOException {
		switch (continueAuthentication()) {
		case SEND_CONTINUE:
			externalContext.getFlash().setKeepMessages(true);
			break;
		case SEND_FAILURE:
			Messages.addError(null, "Login failed");
			break;
		case SUCCESS:
			facesContext.responseComplete();
			addDetailMessage("Logged in successfully as <b>" + email + "</b>");
			Faces.redirect(adminConfig.getIndexPage());
			break;
		case NOT_DONE:
			Messages.addError(null, "Login failed");
		}
	}
	
	private AuthenticationStatus continueAuthentication() {
		return securityContext.authenticate((HttpServletRequest) externalContext.getRequest(),
				(HttpServletResponse) externalContext.getResponse(),
				AuthenticationParameters.withParams().rememberMe(remember)
				.credential(new UsernamePasswordCredential(email, password)));
	}

	@Override
	public boolean isLoggedIn() {
		return securityContext.getCallerPrincipal() != null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public String getCurrentUser() {
		return securityContext.getCallerPrincipal() != null ? securityContext.getCallerPrincipal().getName() : "";
	}

}