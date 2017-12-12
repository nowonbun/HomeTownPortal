package HT.Portal.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletHeader {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public ServletHeader(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void login() {
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute(UserSession.SESSION_ID) != null) {
				response.sendRedirect("./index.jsp");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	public void index() {
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute(UserSession.SESSION_ID) == null) {
				response.sendRedirect("./login.jsp");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
