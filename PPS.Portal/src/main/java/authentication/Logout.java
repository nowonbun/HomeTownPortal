package authentication;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;

import common.IServlet;
import common.Util;
import dao.CookieDao;
import dao.FactoryDao;

@WebServlet("/Logout")
public class Logout extends IServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doGet() {
		try {
			Cookie cookie = getCookie(Certification.COOKIE_KEY);
			UserServer info = getUserinfo();
			CookieDao dao = FactoryDao.getDao(CookieDao.class);
			model.Cookie cookieitem = dao.getEntity(info.getUser().getId(), cookie.getValue());
			if (cookieitem != null) {
				//dao.delete(cookieitem);
				cookieitem.getStateInfo().setIsDelete(true);
				cookieitem.update(info.getId());
				dao.update(cookieitem);
			}

			getSession().invalidate();
			if (cookie != null) {
				cookie.setPath(Util.getCookiePath());
				cookie.setMaxAge(0);
				getResponse().addCookie(cookie);
			}
			Redirect("./login.jsp");
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPost() {
		doGet();
	}
}
