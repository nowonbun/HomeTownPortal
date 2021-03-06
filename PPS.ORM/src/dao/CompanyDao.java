package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.FactoryDao;
import common.TransactionDao;
import model.Company;

public class CompanyDao extends TransactionDao<Company> {
	protected CompanyDao() {
		super(Company.class);
	}

	public Company getComany(int id) {
		return super.findOne(id);
	}

	public Company getDefaultCompany() {
		return getComany(FactoryDao.getDao(LookUpDao.class).getValueInt("DefaultCompany"));
	}

	@SuppressWarnings("unchecked")
	public List<Company> getCompanyAll() {
		return transaction((em) -> {
			try {
				String qy = "SELECT c FROM Company c WHERE c.stateInfo.isDelete = false";
				Query query = em.createQuery(qy);
				return (List<Company>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Company> getCompanyAllIncludeDelete() {
		return transaction((em) -> {
			try {
				String qy = "SELECT c FROM Company c";
				Query query = em.createQuery(qy);
				return (List<Company>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Company getCompanyByName(String name) {
		return transaction((em) -> {
			try {
				String qy = "SELECT c FROM Company c WHERE c.name = :name AND c.stateInfo.isDelete = false";
				Query query = em.createQuery(qy);
				query.setParameter("name", name);
				return (Company) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
