package jo.javaee.jpa.cascade.onetomany;

import static org.junit.Assert.fail;

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
public class DeptRepoTest {
	@Inject
	private DeptRepo repo;

	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class)
				.addPackages(true, Emp.class.getPackage())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	            .addAsWebInfResource("h2-ds.xml")
	            .addAsResource("import-cascade-onetomany.sql", "import.sql")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		// System.out.println(archive.toString(true));
		return war;
	}

	@Test
	public void testSave() {
		Dept d = new Dept(null, "市场部");
		Emp e = new Emp(null, "Tom", d);
		d.getEmps().add(e);
		repo.save(d);
		Assert.assertNotNull("id不能为null", d.getId());
	}
	
	@Test
	public void testSave2() {
		Dept d = new Dept(null, "研发部");
		Emp e = new Emp(1L, "Tom", d);
		d.getEmps().add(e);
		repo.save(d);
		Assert.assertNotNull("id不能为null", d.getId());
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
