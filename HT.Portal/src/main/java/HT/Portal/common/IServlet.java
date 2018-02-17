package HT.Portal.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HT.Portal.authentication.UserServer;
import dao.CookieDao;
import dao.FactoryDao;
import model.CookiePK;

public abstract class IServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String COOKIE_KEY = "HomePortalKey";
	private HttpServletRequest request;
	private HttpServletResponse response;

	protected boolean checkAuthorization() {
		HttpSession session = request.getSession();
		if (session.getAttribute(UserServer.SESSION_ID) != null) {
			return true;
		}
		return false;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		doGet();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		doPost();
	}

	protected HttpServletRequest getRequest() {
		return this.request;
	}

	protected HttpServletResponse getResponse() {
		return this.response;
	}

	protected HttpSession getSession() {
		return request.getSession();
	}

	protected Cookie[] getCookies() {
		return request.getCookies();
	}

	protected Cookie getCookie(String name) {
		return Util.searchArray(getCookies(), (node) -> {
			return Util.StringEquals(name, node.getName());
		});
	}

	protected UserServer getUserinfo() {
		HttpSession session = request.getSession();
		return (UserServer) session.getAttribute(UserServer.SESSION_ID);
	}

	protected void Redirect(String url) {
		try {
			getResponse().sendRedirect(url);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	protected String getStreamData() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream()))) {
			return br.readLine();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	protected PrintWriter getPrinter() {
		try {
			return getResponse().getWriter();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	protected void setStatus(int code) {
		getResponse().setStatus(403);
	}

	public void setLoginSession(UserServer user) {

		getSession().setAttribute(UserServer.SESSION_ID, user);
		String key = Util.createCookieKey();
		Cookie cookie = new Cookie(COOKIE_KEY, key);
		cookie.setMaxAge(Util.getCookieExpire());
		cookie.setPath(Util.getCookiePath());
		getResponse().addCookie(cookie);

		CookieDao dao = FactoryDao.getDao(CookieDao.class);
		model.Cookie entity = dao.getEntityByCookiekey(key);
		if (entity != null) {
			// dao.delete(entity);
			// entity.getStateInfo().setIsDelete(true);
			// dao.update(entity);
			entity.update(user.getId());
		}else {
			entity = new model.Cookie(user.getId());
		}
		CookiePK pk = new CookiePK();
		pk.setId(user.getId());
		pk.setCookiekey(key);
		entity.setId(pk);
		entity.setUser(user.getUser());
		entity.setIpaddress(Util.getRemoteAddr(getRequest()));
		entity.setLastConnectDate(new Date());
		dao.create(entity);
	}

	protected abstract void doGet();

	protected abstract void doPost();
}
