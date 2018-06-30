package reference;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import common.FactoryDao;
import common.Permission;
import dao.CardDao;
import model.Card;
import model.User;

public class CardRoleCache {

	private List<Permission> permissionlist = null;

	private CardRoleCache() {
		this.permissionlist = FactoryDao.getDao(CardDao.class).getPermission();
	}

	private static CardRoleCache singleton = null;

	private static CardRoleCache getinstance() {
		if (singleton == null) {
			reset();
		}
		return singleton;
	}

	public static void reset() {
		singleton = new CardRoleCache();
	}

	public static List<Card> getCardByUser(User user) {
		if (user == null) {
			return null;
		}
		List<Card> ret = new ArrayList<>();
		for (Permission permission : getinstance().permissionlist) {
			if (permission.getCompanyid() != null && permission.getCompanyid() != user.getCompany().getId()) {
				continue;
			}
			if (permission.getGroupid() != null && permission.getGroupid() != user.getGroup().getId()) {
				continue;
			}
			if (permission.getUserid() != null && user.getId().equals(permission.getUserid())) {
				continue;
			}
			ret.add(CardMaster.getDao().getCard(permission.getCode()));
		}
		return ret;
	}

	public static boolean hasPermission(User user, Card card) {
		try {
			if (user == null || card == null) {
				return false;
			}
			return getCardByUser(user).stream().filter(x -> CardMaster.equals(x, card)).findFirst().isPresent();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
