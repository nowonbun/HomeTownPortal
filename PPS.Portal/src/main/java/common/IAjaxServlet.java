package common;

import java.io.PrintWriter;
import entity.bean.ObjectBean;
import model.User;
import reference.CardMaster;
import reference.CardRoleCache;

public abstract class IAjaxServlet extends IServlet {

	private static final long serialVersionUID = 1L;

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
		PermissionServlet servlet = this.getClass().getDeclaredAnnotation(PermissionServlet.class);
		if (servlet != null) {
			String code = servlet.value();
			if (!isRoleCheck(getUserinfo().getUser(), code)) {
				setStatus(401);
				return;
			}
		}
		try (PrintWriter writer = super.getPrinter()) {
			writer.println(doAjax());
		}
	}

	protected boolean isRoleCheck(User user, String code) {
		return CardRoleCache.hasPermission(user, CardMaster.getDao().getCard(code));
	}

	protected final String getDataTableData(Object obj) {
		ObjectBean node = new ObjectBean();
		node.setData(obj);
		return getJsonData(node);
	}

	protected final String getJsonData(Object obj) {
		return JsonConverter.create(obj);
	}

	protected abstract String doAjax();
}
