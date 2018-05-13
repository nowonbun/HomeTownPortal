package dao;

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
}
