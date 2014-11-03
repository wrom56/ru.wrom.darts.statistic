package ru.wrom.darts.statistic.persist.entity;

import ru.wrom.darts.statistic.util.Utils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
public class PlayerGameAttempt {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date attemptDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private PlayerGame playerGame;

	private String dart1Score;
	private String dart2Score;
	private String dart3Score;

	@Column(nullable = false)
	private int totalScore;

	@Column(nullable = false)
	private boolean isLegalAttempt;

	@Column(nullable = false)
	private int dartCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAttemptDate() {
		return attemptDate;
	}

	public void setAttemptDate(Date attemptDate) {
		this.attemptDate = attemptDate;
	}

	public PlayerGame getPlayerGame() {
		return playerGame;
	}

	public void setPlayerGame(PlayerGame playerGame) {
		this.playerGame = playerGame;
	}

	public String getDart1Score() {
		return dart1Score;
	}

	public void setDart1Score(String dart1Score) {
		this.dart1Score = dart1Score;
	}

	public String getDart2Score() {
		return dart2Score;
	}

	public void setDart2Score(String dart2Score) {
		this.dart2Score = dart2Score;
	}

	public String getDart3Score() {
		return dart3Score;
	}

	public void setDart3Score(String dart3Score) {
		this.dart3Score = dart3Score;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public boolean isLegalAttempt() {
		return isLegalAttempt;
	}

	public void setLegalAttempt(boolean isLegalAttempt) {
		this.isLegalAttempt = isLegalAttempt;
	}

	@Transient
	public int getDartsScoreSum() {
		return Utils.getScoreAsInt(dart1Score) + Utils.getScoreAsInt(dart2Score) + Utils.getScoreAsInt(dart3Score);
	}

	@Transient
	public List<String> getDartScoreList() {
		return Arrays.asList(dart1Score, dart2Score, dart3Score);
	}

	@Transient
	public Integer getLastDartNumber() {
		if (Utils.getScoreAsInt(dart3Score) > 0) {
			return 3;
		}
		if (Utils.getScoreAsInt(dart2Score) > 0) {
			return 2;
		}
		if (Utils.getScoreAsInt(dart1Score) > 0) {
			return 1;
		}
		return null;
	}

	public int getDartCount() {
		return dartCount;
	}

	public void setDartCount(int dartCount) {
		this.dartCount = dartCount;
	}

	@Transient
	public String getLastDartScore() {
		Integer dartNumber = getLastDartNumber();
		if (dartNumber == null) {
			return null;
		}
		return getDartScoreList().get(dartNumber - 1);
	}
}
