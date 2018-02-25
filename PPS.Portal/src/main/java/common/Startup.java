package common;

import javax.servlet.http.HttpServlet;

import dao.MasterDao;

public class Startup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Startup() {
        super();
    }
    
    public void init() {
    	MasterDao.initialize();
    }
}
