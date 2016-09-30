package jo.javaee.jpa.cascade.manytoone;

import static org.junit.Assert.fail;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OrderRepoTest {
	@Inject
	private OrderRepo repo;
	
	@Deployment
    public static Archive<?> createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "jpa-test.war")
	            .addPackages(true, Order.class.getPackage())
	            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	            .addAsWebInfResource("h2-ds.xml")
	            .addAsResource("import-cascade-manytoone.sql", "import.sql")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		
		return war;
	}

	@Test
	public final void testSave() {
		Customer tom = new Customer(null, "Tom");
		Order order = new Order(null, "P023", tom);
		repo.save(order);
		Assert.assertNotNull("id不能为null", order.getId());
	}
	
	@Test
	public final void testSave2() {
		Customer mary = new Customer(1L, "Mary2");
		Order order = new Order(null, "P026", mary);
		repo.save(order);
		//order.setCustomer(mary);
		//repo.update(order);
		Assert.assertNotNull("id不能为null", order.getId());
	}

	@Test
	public final void testUpdate() {
		fail("Not yet implemented");
	}

}
