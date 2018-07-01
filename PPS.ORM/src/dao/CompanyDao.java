package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.FactoryDao;
import common.Manager;
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
		return Manager.transaction(() -> {
			try {
				String qy = "SELECT c FROM Company c WHERE c.stateInfo.isDelete = false";
				Query query = Manager.get().createQuery(qy);
				return (List<Company>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
