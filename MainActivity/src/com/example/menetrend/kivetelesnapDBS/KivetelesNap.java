package com.example.menetrend.kivetelesnapDBS;


public class KivetelesNap {
	public int id;
	public String datum;
	public String közlekedik;
	
	public KivetelesNap(){
		id=-1;
		datum=null;
		közlekedik=null;
	}
	
	public KivetelesNap(int id,String datum,String közlekedik){
		this.id=id;
		this.datum=datum;
		this.közlekedik=közlekedik;
	}
	
	public KivetelesNap(String datum,String közlekedik){
		this.datum=datum;
		this.közlekedik=közlekedik;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getKözlekedik() {
		return közlekedik;
	}
	public void setKözlekedik(String közlekedik) {
		this.közlekedik = közlekedik;
	}
}
