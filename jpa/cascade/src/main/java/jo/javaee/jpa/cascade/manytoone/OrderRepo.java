package jo.javaee.jpa.cascade.manytoone;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderRepo {
	@PersistenceContext(unitName = "cascade-persistence-unit")
	private EntityManager em;
	
	public void save(Order entity) {
		if (entity.getCustomer() != null && entity.getCustomer().getId() != null) {
			Customer c = em.find(Customer.class, entity.getCustomer().getId());
			c.setName(entity.getCustomer().getName());
			entity.setCustomer(c);
		}
		em.persist(entity);
	}
	
	public void update(Order entity) {
		em.merge(entity);
	}
}
