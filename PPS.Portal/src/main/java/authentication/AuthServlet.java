package authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.FactoryDao;
import common.LoggerManager;
import common.Util;
import dao.CookieDao;
import dao.UserDao;
import model.User;

public class AuthServlet {
	private static AuthServlet instance = null;

	private static AuthServlet get() {
		if (instance == null) {
			instance = new AuthServlet();
		}
		return instance;
	}

	private AuthServlet() {

	}

	private boolean getAuthorization(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute(UserService.SESSION_ID) != null) {
			UserService service = (UserService) session.getAttribute(UserService.SESSION_ID);
			LoggerManager.getLogger(AuthServlet.class).debug("Session - " + service.getUser().getId());
			return true;
		}
		Cookie cookie = Util.searchArray(request.getCookies(), (node) -> {
			return Certification.COOKIE_KEY.equals(node.getName());
		});
		if (cookie == null) {
			return false;
		}
		cookie.setMaxAge(Util.getCookieExpire());
		cookie.setPath(Util.getCookiePath());
		CookieDao cookie_dao = FactoryDao.getDao(CookieDao.class);
		model.Cookie entity = cookie_dao.getEntityByCookiekey(cookie.getValue());
		if (entity == null) {
			return false;
		}
		String id = entity.getId().getId();
		LoggerManager.getLogger(AuthServlet.class).debug("Authorization - " + id);
		UserDao user_dao = FactoryDao.getDao(UserDao.class);
		User user = user_dao.getUser(id);
		UserService info = new UserService();
		info.setUser(user);
		session.setAttribute(UserService.SESSION_ID, info);
		return true;
	}

	public static void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (AuthServlet.get().getAuthorization(request, response)) {
				response.sendRedirect("./");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static void auth(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!AuthServlet.get().getAuthorization(request, response)) {
				response.sendRedirect("./login.jsp");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
