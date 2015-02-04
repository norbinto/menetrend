package com.example.menetrend;

public class Megallo {
	private int id;
	private String nev;
	private int ido;
	
	public Megallo(int id,String nev, int ido) {
		super();
		this.id=id;
		this.nev = nev;
		this.ido = ido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNev() {
		return nev;
	}

	public void setNev(String nev) {
		this.nev = nev;
	}

	public int getIdo() {
		return ido;
	}

	public void setIdo(int ido) {
		this.ido = ido;
	}
	
	@Override
	public String toString() {
		return nev;
	}
}
