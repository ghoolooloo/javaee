package jo.javaee.jpa.cascade.onetomany;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Emp
 *
 */
@Entity
@Table(name = "EMPS")
public class Emp implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "DEPT_ID")
	private Dept dept;
	private static final long serialVersionUID = 1L;

	public Emp() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param dept
	 */
	public Emp(Long id, String name, Dept dept) {
		this.id = id;
		this.name = name;
		this.dept = dept;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dept
	 */
	public Dept getDept() {
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(Dept dept) {
		this.dept = dept;
	}

}
