/*package com.github.adminfaces.starter.infra.security;

import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import com.github.adminfaces.template.config.AdminConfig;
import javax.inject.Inject;

*//**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login
 * page or not. By default AdminSession isLoggedIn always resolves to true so it
 * will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user
 * must be redirect to initial page or logon you can skip this class.
 *//*
@Named
@SessionScoped
@Specializes
public class LogonMB_SEC extends AdminSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Size(min = 8, message = "Password must have at least 8 characters")
	private String password;

	@NotEmpty
	@Email(message = "Please provide a valid e-mail")
	private String email;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private ExternalContext externalContext;

	@Inject
	private FacesContext facesContext;

	private boolean remember;

	@Inject
	private AdminConfig adminConfig;

	public void login() throws IOException {
		switch (continueAuthentication()) {
		case SEND_CONTINUE:
			facesContext.responseComplete();
			break;
		case SEND_FAILURE:
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
			break;
		case SUCCESS:
			Faces.getExternalContext().getFlash().setKeepMessages(true);
			addDetailMessage("Logged in successfully as <b>" + email + "</b>");
			Faces.redirect(adminConfig.getIndexPage());
			break;
		case NOT_DONE:
		}
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

	private AuthenticationStatus continueAuthentication() {
		return securityContext.authenticate((HttpServletRequest) externalContext.getRequest(),
				(HttpServletResponse) externalContext.getResponse(),
				AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(email, password)));
	}

}
*/