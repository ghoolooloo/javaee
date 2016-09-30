package jo.javaee.jpa.cascade.onetomany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Dept
 *
 */
@Entity
@Table(name = "DEPTS")
public class Dept implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany(mappedBy = "dept", cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	private Collection<Emp> emps = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	public Dept() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 */
	public Dept(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Emp> getEmps() {
		return this.emps;
	}

	public void setEmps(Collection<Emp> emps) {
		this.emps = emps;
	}

}
