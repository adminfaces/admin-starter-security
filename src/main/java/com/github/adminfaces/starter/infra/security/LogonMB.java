package com.github.adminfaces.starter.infra.security;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

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
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

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
	
	public void autoLogin() throws IOException {
    	String emailCookie = Faces.getRequestCookie("admin-email");
		String passCookie = Faces.getRequestCookie("admin-pass");
		if(has(emailCookie) && has(passCookie)) {
			this.email = emailCookie;
			this.password = passCookie;
			login();
		}
	}

	public void login() throws IOException {
		switch (continueAuthentication()) {
		case SEND_CONTINUE:
			facesContext.responseComplete();
			break;
		case SEND_FAILURE:
			Messages.addError(null, "Login failed");
			externalContext.getFlash().setKeepMessages(true);
			break;
		case SUCCESS:
			externalContext.getFlash().setKeepMessages(true);
			addDetailMessage("Logged in successfully as <b>" + email + "</b>");
			if(remember) {
				storeCookieCredentials(email, password);
			}
			Faces.redirect(adminConfig.getIndexPage());
			break;
		case NOT_DONE:
			Messages.addError(null, "Login failed");
		}
	}
	
	private void storeCookieCredentials(final String email, final String password) {
		 Faces.addResponseCookie("admin-email", email, 1800);//store for 30min
		 Faces.addResponseCookie("admin-pass", password, 1800);//store for 30min
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