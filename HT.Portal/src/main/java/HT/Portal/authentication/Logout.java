package HT.Portal.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HT.Portal.common.Util;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie cookie = Util.searchArray(request.getCookies(), (node) -> {
			return Certification.COOKIE_KEY.equals(node.getName());
		});
		if(cookie != null) {
			request.getSession().invalidate();
			cookie.setPath(Util.getCookiePath());
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		response.sendRedirect("./login.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
