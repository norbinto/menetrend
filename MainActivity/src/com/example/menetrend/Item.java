package com.example.menetrend;

public class Item {
	private String cim;
	private String megallo;
	
	public Item(){
		
	}
	
	public Item(String cim,String megallo){
		this.cim=cim;
		this.megallo=megallo;
	}
	
	public String getCim() {
		return cim;
	}
	public void setCim(String cim) {
		this.cim = cim;
	}
	public String getMegallo() {
		return megallo;
	}
	public void setMegallo(String megallo) {
		this.megallo = megallo;
	}
	
	
}
