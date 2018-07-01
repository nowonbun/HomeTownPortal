package dao;

import java.util.List;
import java.util.NoSuchElementException;

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
		try {
			return getData().stream().filter(x -> x.getKey().equals(key)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public String getValueString(String key) {
		LookUp lookup = getLookUp(key);
		if (lookup == null) {
			return null;
		}
		return lookup.getValue();
	}

	public int getValueInt(String key) {
		return Integer.parseInt(getLookUp(key).getValue());
	}
}
