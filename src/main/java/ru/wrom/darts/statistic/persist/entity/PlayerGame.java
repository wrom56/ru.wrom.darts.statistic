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

	@Column(nullable = false)
	private int orderNumber;

	@JoinColumn(nullable = false)
	private Player player;

	private Dart dart;

	@Column(nullable = false)
	private int dartCount;

	@Column(nullable = false)
	private int totalScore;

	@Column(nullable = false)
	private boolean isFinished;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "PlayerGame")
	private List<PlayerGameAttempt> attempts;

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
		attempt.setPlayerGame(this);
		getAttempts().add(attempt);
		dartCount += attempt.getDartCount();
		if (attempt.isLegalAttempt()) {
			totalScore += attempt.getTotalScore();
		}
	}

	public void removeLastAttempt() {
		if (getAttempts().size() > 0) {
			PlayerGameAttempt removedAttempt = getAttempts().remove(getAttempts().size() - 1);
			dartCount -= removedAttempt.getDartCount();
			if (removedAttempt.isLegalAttempt()) {
				totalScore -= removedAttempt.getTotalScore();
			}
		}
	}


	public void setAttempts(List<PlayerGameAttempt> attempts) {
		this.attempts = attempts;
	}

	public int getTotalScore() {
		return totalScore;
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

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getDartCount() {
		return dartCount;
	}

	public void setDartCount(int dartCount) {
		this.dartCount = dartCount;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
}



