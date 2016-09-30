package jo.javaee.jpa.cascade.onetomany;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmpRepo {
	@PersistenceContext(unitName = "cascade-persistence-unit")
	private EntityManager em;
	
	public Emp get(Long id) {
		return em.find(Emp.class, id);
	}
}
