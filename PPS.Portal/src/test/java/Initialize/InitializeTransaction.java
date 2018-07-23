package Initialize;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import common.EntityManagerRunable;
import common.FactoryDao;
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
	private static void transaction(EntityManagerRunable runnable) {
		transaction(runnable, false);
	}

	private static void transaction(EntityManagerRunable runnable, boolean readonly) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("portal");
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			runnable.run(em);
			if (readonly) {
				transaction.rollback();
			} else {
				transaction.commit();
			}
		} catch (Throwable e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException(e);
		} finally {
			em.clear();
			em.close();
			emf.close();
		}
	}

	public static void main(String... arg) {
		try {
			transaction((em) -> {
				em.createQuery("DELETE FROM Cookie").executeUpdate();
				em.createQuery("DELETE FROM Comment").executeUpdate();
				em.createQuery("DELETE FROM Password").executeUpdate();
				em.createQuery("DELETE FROM User").executeUpdate();
				em.createQuery("DELETE FROM MapActionRoleCompany").executeUpdate();
				em.createQuery("DELETE FROM MapActionRoleGroup").executeUpdate();
				em.createQuery("DELETE FROM MapActionRoleUser").executeUpdate();
				em.createQuery("DELETE FROM MapViewRoleCompany").executeUpdate();
				em.createQuery("DELETE FROM MapViewRoleGroup").executeUpdate();
				em.createQuery("DELETE FROM MapViewRoleUser").executeUpdate();
				em.createQuery("DELETE FROM Group").executeUpdate();
				em.createQuery("DELETE FROM Company").executeUpdate();
				em.createQuery("DELETE FROM StateInfo").executeUpdate();
			});
			Company company = new Company("INIT");
			company.setName("Administrator Company");
			company.getStateInfo().setState(StateMaster.getDefaultTransactionData());
			// TODO : profile ??
			company.setCards(FactoryDao.getDao(CardDao.class).getCardAll());
			FactoryDao.getDao(CompanyDao.class).update(company);
			company = FactoryDao.getDao(CompanyDao.class).getCompanyAll().stream().filter(x -> x.getName().equals("Administrator Company")).findFirst().get();

			Group group = new Group("INIT");
			group.setName("Administrator");
			group.setCompany(company);
			group.getStateInfo().setState(StateMaster.getDefaultTransactionData());
			group.setCards(FactoryDao.getDao(CardDao.class).getCardAll());
			FactoryDao.getDao(GroupDao.class).update(group);
			group = FactoryDao.getDao(GroupDao.class).getGroupAll().stream().filter(x -> x.getName().equals("Administrator")).findFirst().get();

			Group group1 = new Group("INIT");
			group1.setName("Guest");
			group1.setCompany(company);
			group1.getStateInfo().setState(StateMaster.getDefaultTransactionData());
			FactoryDao.getDao(GroupDao.class).update(group1);
			group1 = FactoryDao.getDao(GroupDao.class).getGroupAll().stream().filter(x -> x.getName().equals("Guest")).findFirst().get();

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
