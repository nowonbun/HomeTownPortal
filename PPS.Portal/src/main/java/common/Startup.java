package common;

import javax.servlet.annotation.WebServlet;

@WebServlet("/Startup")
public class Startup extends IServlet {
	private static final long serialVersionUID = 1L;

	public Startup() {
		super();
	}

	public void init() {
		FactoryDao.initializeMaster();
	}

	public void doGet() {
		FactoryDao.resetMaster();
	}

	public void doPost() {
		FactoryDao.resetMaster();
	}
}
