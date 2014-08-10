package ru.wrom.darts.statistic.persist.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ApplicationInfo {

	@Id
	private Integer id;


	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastRunTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
}
