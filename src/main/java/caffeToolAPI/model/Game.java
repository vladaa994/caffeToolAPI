package caffeToolAPI.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the game database table.
 * 
 */
@Entity
@Table(name = "game")
public class Game implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;

	private String type;

	private boolean paid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	private Date startTime;

	@Column(name="table_number")
	private int tableNumber;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//uni-directional many-to-many association to Player
	@ManyToMany(cascade = CascadeType.ALL)
//	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "player_game",
			joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"))
	private List<Player> players;

	@Column(name = "deleted")
	private boolean deleted;

	@Column(name = "bill")
	private float bill;

	public Game() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getPaid() {
		return this.paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getTableNumber() {
		return this.tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public boolean getIsPaid() {
		return paid;
	}

	public boolean getIsDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public float getBill() {
		return bill;
	}

	public void setBill(float bill) {
		this.bill = bill;
	}
}