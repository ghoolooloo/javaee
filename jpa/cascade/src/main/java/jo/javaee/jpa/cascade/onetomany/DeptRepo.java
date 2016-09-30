package jo.javaee.jpa.cascade.onetomany;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DeptRepo {
	@PersistenceContext(unitName = "cascade-persistence-unit")
	private EntityManager em;
	
	public void save(Dept entity) {
		em.persist(entity);
	}
	
	public void update(Dept entity) {
		em.merge(entity);
	}

}
