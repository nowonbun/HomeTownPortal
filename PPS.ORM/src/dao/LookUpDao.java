package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Manager;
import common.MasterDao;
import model.LookUp;

public class LookUpDao extends MasterDao<LookUp> {

	protected LookUpDao() {
		super(LookUp.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<LookUp> getDataList() {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get().createNamedQuery("LookUp.findAll", LookUp.class);
				return (List<LookUp>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public LookUp getLookUp(String key) {
		return getData().stream().filter(x -> x.getKey().equals(key)).findFirst().get();
	}

	public String getValueString(String key) {
		return getLookUp(key).getValue();
	}

	public int getValueInt(String key) {
		return Integer.parseInt(getLookUp(key).getValue());
	}
}
