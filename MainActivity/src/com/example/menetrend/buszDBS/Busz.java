package com.example.menetrend.buszDBS;

public class Busz {
	/* mikor indul pontosan pl: 04:30 */
	private String indul;
	/*
	 * Melyik nap indul 0 naponta áthúzott kör szabadnapokon ...
	 */
	private int nap;
	/* DB */
	private String honnan;
	/* HS */
	private String hova;
	/* SK- */
	private String keresztul;
	private int id;
	/* menetrend tábla sorának id-je */
	private int menetrend_id;

	public Busz() {
		indul = null;
		nap = -1;
		honnan = null;
		hova = null;
		keresztul = null;
		id = -1;
		menetrend_id = -1;

	}

	public Busz(String indul, int nap, String honnan, String hova,
			String keresztul, int menetrend_id) {
		super();
		this.indul = indul;
		this.nap = nap;
		this.honnan = honnan;
		this.hova = hova;
		this.keresztul = keresztul;
		this.menetrend_id = menetrend_id;
	}

	public Busz(String indul, int nap, String honnan, String hova,
			String keresztul, int id, int menetrend_id) {
		super();
		this.indul = indul;
		this.nap = nap;
		this.honnan = honnan;
		this.hova = hova;
		this.keresztul = keresztul;
		this.id = id;
		this.menetrend_id = menetrend_id;
	}

	public int getMenetrend_id() {
		return menetrend_id;
	}

	public void setMenetrend_id(int menetrend_id) {
		this.menetrend_id = menetrend_id;
	}

	public String getIndul() {
		return indul;
	}

	public void setIndul(String indul) {
		this.indul = indul;
	}

	public int getNap() {
		return nap;
	}

	public void setNap(int nap) {
		this.nap = nap;
	}

	public String getHonnan() {
		return honnan;
	}

	public void setHonnan(String honnan) {
		this.honnan = honnan;
	}

	public String getHova() {
		return hova;
	}

	public void setHova(String hova) {
		this.hova = hova;
	}

	public String getKeresztul() {
		return keresztul;
	}

	public void setKeresztul(String keresztul) {
		this.keresztul = keresztul;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
