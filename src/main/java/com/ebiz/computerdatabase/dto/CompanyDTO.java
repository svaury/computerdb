package com.ebiz.computerdatabase.dto;

public class CompanyDTO {

	private int id;
	private String name;

  /**
  * companyDTO constructor.
  * @param id .
  * @param name .
  */
	public CompanyDTO(int id, String name) {

		this.id = id;
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


}
