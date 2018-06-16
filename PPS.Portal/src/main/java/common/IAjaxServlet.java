package common;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class IAjaxServlet extends IServlet {

	private static final long serialVersionUID = 1L;

	private class Node {
		private Object data;
	}

	@Override
	protected final void doGet() {
		super.setStatus(403);
	}

	@Override
	protected final void doPost() {
		if (!checkAuthorization()) {
			super.setStatus(403);
		}
		try {
			super.getRequest().setCharacterEncoding("UTF-8");
			super.getResponse().setContentType("text/html;charset=UTF-8");
		} catch (Exception e) {
			LoggerManager.getLogger(IAjaxServlet.class).error(e);
		}

		try (PrintWriter writer = super.getPrinter()) {
			writer.println(doAjax());
		}
	}

	protected final String getDataTableData(Object obj) {
		Node node = new Node();
		// node.data = new ArrayList<>();
		// node.data.add(obj);
		node.data = obj;
		return getJsonData(node);
	}

	protected final String getJsonData(Object obj) {
		return JsonConverter.create(obj);
	}

	protected abstract String doAjax();
}
