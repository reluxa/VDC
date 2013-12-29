package org.reluxa.vaadin.auth;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.LoginService;
import org.reluxa.model.Player;

import com.vaadin.cdi.access.AccessControl;
import com.vaadin.sass.internal.util.StringUtil;
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
		if (getPrincipalName() != null) {
			Player player = loginService.getUser(getPrincipalName());
			if (Player.ROLE_USER.equals(role) && player != null) {
				return true;
			} else if (Player.ROLE_ADMIN.equals(role) && player != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getPrincipalName() {
		return (String) UI.getCurrent().getSession().getAttribute(USER_EMAIL);
	}

	
	public boolean login(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("Username and password must not be empty!");
		}
		Player player = new Player();
		player.setEmail(username);
		player.setPassword(password);
		if (loginService.isUserExists(player)) {
			UI.getCurrent().getSession().setAttribute(USER_EMAIL, player.getEmail());
			return true;
		}
		return false;
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
