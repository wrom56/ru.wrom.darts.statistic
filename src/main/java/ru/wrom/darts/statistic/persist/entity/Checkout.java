package ru.wrom.darts.statistic.persist.entity;

import javax.persistence.*;

@Entity
public class Checkout {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private int finishScore;

	private String dart1Score;
	private String dart2Score;
	private String dart3Score;

	public Checkout(int finishScore, String dart1Score, String dart2Score, String dart3Score) {
		this.finishScore = finishScore;
		this.dart1Score = dart1Score;
		this.dart2Score = dart2Score;
		this.dart3Score = dart3Score;
	}

	public Checkout(int finishScore, String dart1Score, String dart2Score) {
		this(finishScore, dart1Score, dart2Score, null);
	}

	public Checkout(int finishScore, String dart1Score) {
		this(finishScore, dart1Score, null, null);
	}

	public Checkout() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFinishScore() {
		return finishScore;
	}

	public void setFinishScore(int finishScore) {
		this.finishScore = finishScore;
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
}
