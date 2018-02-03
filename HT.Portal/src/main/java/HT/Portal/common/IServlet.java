package HT.Portal.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HT.Portal.authentication.UserServerInfo;

public abstract class IServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;

	protected boolean checkAuthorization() {
		HttpSession session = request.getSession();
		if (session.getAttribute(UserServerInfo.SESSION_ID) != null) {
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

	protected UserServerInfo getUserinfo() {
		HttpSession session = request.getSession();
		return (UserServerInfo) session.getAttribute(UserServerInfo.SESSION_ID);
	}

	protected void Redirect(String url) throws IOException {
		getResponse().sendRedirect(url);
	}

	protected abstract void doGet();

	protected abstract void doPost();
}
