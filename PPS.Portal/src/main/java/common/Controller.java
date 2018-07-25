package common;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import dao.CompanyDao;
import dao.GroupDao;
import entity.SelectNode;
import entity.bean.TileBean;
import model.Company;
import model.Group;
import model.User;
import reference.CardTypeMaster;

public abstract class Controller extends IWorkflow {

	private Logger logger = null;

	protected Logger getLogger() {
		if (logger == null) {
			logger = LoggerManager.getLogger(setLogClass());
		}
		return logger;
	}

	protected abstract Class<?> setLogClass();

	protected TileBean createTile(model.Card card) {

		if (Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_CARD) || Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_MENU_CARD)) {
			TileBean entity = new TileBean();
			entity.setTypeHeaderClass("card-image");
			if (card.getImg() != null) {
				entity.setBackground("url('" + new String(card.getImg()) + "') no-repeat center");
			} else {
				entity.setBackground("url('./contents/no_card.jpg') no-repeat center");
			}
			entity.setBorder("");
			entity.setHeader("");
			entity.setBody("<span class='card-caption-body'>" + card.getTitle() + "</span>");
			entity.setMenu(Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_MENU_CARD));
			entity.setHref(card.getHref());
			entity.setControl(card.getControl());
			entity.setTemplate(card.getTemplate());
			return entity;
		} else if (Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_CARD) || Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_MENU_CARD)) {
			TileBean entity = new TileBean();
			entity.setTypeHeaderClass("card-event");
			entity.setBackground("");
			entity.setBorder("mdl-card--border");
			entity.setHeader("<div class='card-icon'><i class='" + card.getIcon() + "' style='color:" + card.getColor() + ";'></i></div>");
			entity.setBody("<span class='card-caption-body'>" + card.getTitle() + "</span><div class='mdl-layout-spacer'></div>");
			entity.setMenu(Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_MENU_CARD));
			entity.setHref(card.getHref());
			entity.setControl(card.getControl());
			entity.setTemplate(card.getTemplate());
			return entity;
		}
		return null;
	}

	protected List<SelectNode> getSelectCompany() {
		List<SelectNode> list = new ArrayList<>();
		for (Company com : FactoryDao.getDao(CompanyDao.class).getCompanyAll()) {
			if (com.getStateInfo().getIsDelete()) {
				continue;
			}
			SelectNode select = new SelectNode();
			list.add(select);
			select.setValue(String.valueOf(com.getId()));
			select.setName(com.getName());
		}
		return list;
	}

	protected List<SelectNode> getSelectGroup(int companykey) {
		List<SelectNode> list = new ArrayList<>();
		for (Group grp : FactoryDao.getDao(CompanyDao.class).getComany(companykey).getGroups()) {
			if (grp.getStateInfo().getIsDelete()) {
				continue;
			}
			SelectNode select = new SelectNode();
			list.add(select);
			select.setValue(String.valueOf(grp.getId()));
			select.setName(grp.getName());
		}
		return list;
	}

	protected List<SelectNode> getSelectUser(int groupkey) {
		List<SelectNode> list = new ArrayList<>();
		for (User usr : FactoryDao.getDao(GroupDao.class).getGroup(groupkey).getUsers()) {
			if (usr.getStateInfo().getIsDelete()) {
				continue;
			}
			SelectNode select = new SelectNode();
			list.add(select);
			select.setValue(usr.getId());
			select.setName(usr.getName());
		}
		return list;
	}

	protected List<Group> getRoleGroup(List<Group> list, List<Group> role) {
		List<Group> ret = new ArrayList<>();
		for (Group grp : list) {
			if (grp.getStateInfo().getIsDelete()) {
				continue;
			}
			if (role.contains(grp)) {
				ret.add(grp);
			}
		}
		return ret;
	}

	protected List<User> getRoleUser(List<User> list, List<User> role) {
		List<User> ret = new ArrayList<>();
		for (User usr : list) {
			if (usr.getStateInfo().getIsDelete()) {
				continue;
			}
			if (role.contains(usr)) {
				ret.add(usr);
			}
		}
		return ret;
	}
}
