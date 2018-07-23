package common;

import javax.persistence.EntityManager;

public interface EntityManagerRunable {
	void run(EntityManager em);
}
