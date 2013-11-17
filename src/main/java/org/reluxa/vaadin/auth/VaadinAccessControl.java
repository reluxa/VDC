package org.reluxa.vaadin.auth;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.reluxa.LoginService;
import org.reluxa.model.Player;

import com.vaadin.cdi.access.AccessControl;
import com.vaadin.ui.UI;

@Alternative
public class VaadinAccessControl extends AccessControl {

	private static final String USER_EMAIL = "user";

	@Inject
	LoginService loginService;

	@Override
	public boolean isUserSignedIn() {
		return UI.getCurrent().getSession().getAttribute(USER_EMAIL) != null;
	}

	@Override
	public boolean isUserInRole(String role) {
		Player player = loginService.getUser(getPrincipalName());
		return player.getRoles() != null && player.getRoles().contains(role);
	}

	@Override
	public String getPrincipalName() {
		return (String) UI.getCurrent().getSession().getAttribute(USER_EMAIL);
	}

	public boolean login(Player player) {
		if (loginService.isUserExists(player)) {
			UI.getCurrent().getSession().setAttribute(USER_EMAIL, player.getEmail());
			return true;
		}
		return false;
	}

	public void logout() {
		UI.getCurrent().getSession().setAttribute(USER_EMAIL, null);
	}

}
