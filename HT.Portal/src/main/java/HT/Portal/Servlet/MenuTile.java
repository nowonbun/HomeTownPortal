package HT.Portal.Servlet;

import javax.servlet.annotation.WebServlet;

import HT.Portal.common.IServlet;

@WebServlet("/MenuTile")
public class MenuTile extends IServlet {
	private static final long serialVersionUID = 1L;

	public MenuTile() {
		super();
	}

	protected void doGet() {
		try {
			if (checkAuthorization()) {
				Redirect("./login.jsp");
			}
			getResponse().getWriter().write("hello world");
		}catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPost() {
		doGet();
	}
}
