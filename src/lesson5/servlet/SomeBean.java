package lesson5.servlet;

import java.io.Serializable;
import java.util.Date;

public class SomeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String version = "version 1.1";
	private Date date = new Date();
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SomeBean()
	{
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
