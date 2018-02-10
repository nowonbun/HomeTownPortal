package HT.Portal.Servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HT.Portal.authentication.Certification;
import HT.Portal.authentication.UserServer;
import HT.Portal.common.Util;
import dao.CookieDao;
import dao.FactoryDao;
import dao.UserDao;
import model.User;

public class InstanceServlet {
	private static InstanceServlet instance = null;

	public static InstanceServlet get() {
		if (instance == null) {
			instance = new InstanceServlet();
		}
		return instance;
	}

	private InstanceServlet() {

	}

	private boolean getAuthorization(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute(UserServer.SESSION_ID) != null) {
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
		UserDao user_dao = FactoryDao.getDao(UserDao.class);
		User user = user_dao.getUser(id);
		UserServer info = new UserServer();
		info.setUser(user);
		session.setAttribute(UserServer.SESSION_ID, info);
		return true;
	}

	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (getAuthorization(request, response)) {
				response.sendRedirect("./");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void index(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!getAuthorization(request, response)) {
				response.sendRedirect("./login.jsp");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	public String tile(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!getAuthorization(request, response)) {
				response.sendRedirect("./login.jsp");
				return null;
			}
			return "hello world";
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
