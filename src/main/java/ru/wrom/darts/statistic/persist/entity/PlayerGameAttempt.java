package ru.wrom.darts.statistic.persist.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
public class PlayerGameAttempt {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date attemptDate;

	@ManyToOne(cascade = CascadeType.ALL)
	private PlayerGame playerGame;

	private Integer dart1Score;
	private Integer dart2Score;
	private Integer dart3Score;

	private Integer totalScore;

	private Integer usedDarts;

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

	public Integer getDart1Score() {
		return dart1Score;
	}

	public void setDart1Score(Integer dart1Score) {
		this.dart1Score = dart1Score;
	}

	public Integer getDart2Score() {
		return dart2Score;
	}

	public void setDart2Score(Integer dart2Score) {
		this.dart2Score = dart2Score;
	}

	public Integer getDart3Score() {
		return dart3Score;
	}

	public void setDart3Score(Integer dart3Score) {
		this.dart3Score = dart3Score;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getUsedDarts() {
		return usedDarts;
	}

	public void setUsedDarts(Integer usedDarts) {
		this.usedDarts = usedDarts;
	}

	@Transient
	public int getDartsScoreSum() {
		return (dart1Score != null ? dart1Score : 0) + (dart2Score != null ? dart2Score : 0) + (dart3Score != null ? dart3Score : 0);
	}

}
