package HT.Portal.authentication;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import HT.Portal.common.IServlet;
import HT.Portal.common.Util;
import dao.CookieinfoDao;
import dao.FactoryDao;
import model.Cookieinfo;

@WebServlet("/Logout")
public class Logout extends IServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doGet() {
		try {
			Cookie cookie = getCookie(Certification.COOKIE_KEY);
			UserServerInfo info = getUserinfo();
			CookieinfoDao dao = FactoryDao.getCookieinfoDao();
			Cookieinfo cookieitem = dao.getEntity(info.getUserinfo().getId(), cookie.getValue());
			if (cookieitem != null) {
				dao.delete(cookieitem);
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
