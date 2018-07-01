package Initialize;

import java.util.ArrayList;
import common.FactoryDao;
import common.Manager;
import common.Util;
import dao.CardDao;
import dao.CompanyDao;
import dao.GroupDao;
import dao.LookUpDao;
import dao.UserDao;
import model.Company;
import model.Group;
import model.LookUp;
import model.Password;
import model.User;
import reference.StateMaster;

public class InitializeTransaction {
	public static void main(String... arg) {
		try {
			Manager.transaction(() -> {
				Manager.get().createQuery("DELETE FROM Cookie").executeUpdate();
				Manager.get().createQuery("DELETE FROM Comment").executeUpdate();
				Manager.get().createQuery("DELETE FROM Password").executeUpdate();
				Manager.get().createQuery("DELETE FROM User").executeUpdate();
				Manager.get().createQuery("DELETE FROM MapActionRoleCompany").executeUpdate();
				Manager.get().createQuery("DELETE FROM MapActionRoleGroup").executeUpdate();
				Manager.get().createQuery("DELETE FROM MapActionRoleUser").executeUpdate();
				Manager.get().createQuery("DELETE FROM MapViewRoleCompany").executeUpdate();
				Manager.get().createQuery("DELETE FROM MapViewRoleGroup").executeUpdate();
				Manager.get().createQuery("DELETE FROM MapViewRoleUser").executeUpdate();
				Manager.get().createQuery("DELETE FROM Group").executeUpdate();
				Manager.get().createQuery("DELETE FROM Company").executeUpdate();
				Manager.get().createQuery("DELETE FROM StateInfo").executeUpdate();
			});
			Company company = new Company("INIT");
			company.setName("Administrator Company");
			company.getStateInfo().setState(StateMaster.getDefaultTransactionData());
			//TODO : profile ??
			company.setCards(FactoryDao.getDao(CardDao.class).getCardAll());
			FactoryDao.getDao(CompanyDao.class).update(company);
			company = FactoryDao.getDao(CompanyDao.class).getCompanyAll().stream()
					.filter(x -> x.getName().equals("Administrator Company")).findFirst().get();

			Group group = new Group("INIT");
			group.setName("Administrator");
			group.setCompany(company);
			group.getStateInfo().setState(StateMaster.getDefaultTransactionData());
			group.setCards(FactoryDao.getDao(CardDao.class).getCardAll());
			FactoryDao.getDao(GroupDao.class).update(group);
			group = FactoryDao.getDao(GroupDao.class).getGroupAll().stream()
					.filter(x -> x.getName().equals("Administrator")).findFirst().get();

			Group group1 = new Group("INIT");
			group1.setName("Guest");
			group1.setCompany(company);
			group1.getStateInfo().setState(StateMaster.getDefaultTransactionData());
			FactoryDao.getDao(GroupDao.class).update(group1);
			group1 = FactoryDao.getDao(GroupDao.class).getGroupAll().stream().filter(x -> x.getName().equals("Guest"))
					.findFirst().get();

			LookUp lookup = FactoryDao.getDao(LookUpDao.class).findOne("DefaultCompany");
			lookup.setValue(String.valueOf(company.getId()));
			FactoryDao.getDao(LookUpDao.class).update(lookup);

			lookup = FactoryDao.getDao(LookUpDao.class).findOne("DefaultGroup");
			lookup.setValue(String.valueOf(group1.getId()));
			FactoryDao.getDao(LookUpDao.class).update(lookup);

			User user = new User("INIT", StateMaster.getDefaultTransactionData());
			user.setId("admin");
			user.setGivenName("Administrator");
			user.setName("Administrator");
			user.setNickName("Administrator");
			user.setCompany(company);
			user.setGroup(group);
			user.setPasswords(new ArrayList<Password>());
			user.getPasswords().add(new Password(user, "INIT"));
			user.getPasswords().get(0).setPassword(Util.convertMD5("admin"));

			FactoryDao.getDao(UserDao.class).update(user);
			user = FactoryDao.getDao(UserDao.class).getUser("admin");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
