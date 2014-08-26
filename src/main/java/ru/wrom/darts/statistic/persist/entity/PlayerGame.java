package ru.wrom.darts.statistic.persist.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PlayerGame {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	private Game game;


	private Integer orderNumber;

	private Player player;

	private Dart dart;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "PlayerGame")
	private List<PlayerGameAttempt> attempts;

	private Integer totalScore;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<PlayerGameAttempt> getAttempts() {
		if (attempts == null) {
			attempts = new ArrayList<>();
		}
		return attempts;
	}

	public void addAttempt(PlayerGameAttempt attempt) {
		totalScore = (totalScore != null ? totalScore : 0) + attempt.getTotalScore();
		getAttempts().add(attempt);
	}

	public void setAttempts(List<PlayerGameAttempt> attempts) {
		this.attempts = attempts;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Dart getDart() {
		return dart;
	}

	public void setDart(Dart dart) {
		this.dart = dart;
	}

	@Transient
	public int getDartCount() {
		int result = 0;
		for (PlayerGameAttempt attempt : getAttempts()) {
			result += attempt.getUsedDarts();
		}
		return result;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
}



