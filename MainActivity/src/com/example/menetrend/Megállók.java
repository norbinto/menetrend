package com.example.menetrend;

import java.util.LinkedList;
import java.util.List;

public class Megállók {
	public LinkedList<Megallo> HS_DB;
	public LinkedList<Megallo> HK_DB;
	public LinkedList<Megallo> SK_DB;
	public LinkedList<Megallo> NA_DB;
	public LinkedList<Megallo> SZ_DB;
	public static List ALL = new LinkedList<Megallo>() {
		{
			add(new Megallo(28, "Debrecen, autóbusz-állomás", 60));
			add(new Megallo(27, "Debrecen, vasútállomás", 57));
			add(new Megallo(26, "Debrecen, Hajnal u. 9-11", 52));
			add(new Megallo(25, "Debrecen, Kassai u. (Árpád tér)", 49));
			add(new Megallo(24, "Debrecen, Sámsoni u. ABC", 45));
			add(new Megallo(23, "Debrecen, Hőforrás 3. sz. kapu", 41));
			add(new Megallo(22, "Debrecen, FREÓNIA Kft.", 39));
			add(new Megallo(21, "Útkaparó Presszó (471-es út)", 37));
			add(new Megallo(20, "Debrecen, Vaskapu", 35));
			add(new Megallo(19, "Szikigyakor", 33));
			add(new Megallo(18, "Sámsonkert bejárati út", 31));
			add(new Megallo(17, "Sámsonkert, Fõ utca", 14));
			add(new Megallo(16, "Sámsonkert, forduló", 13));
			add(new Megallo(15, "Sámsonkert, Szűcs utca", 31));
			add(new Megallo(14, "Sámsonkert, Kiscsere", 30));
			add(new Megallo(1415, "Kiscsere-Szűcs utca", 30));
			add(new Megallo(13, "Hajdúsámson, Petőfi utca", 28));
			add(new Megallo(12, "Hajdúsámson, városháza", 26));
			add(new Megallo(11, "Hajdúsámson, Árpád u. 26.", 25));
			add(new Megallo(10, "Hajdúsámson, Árpád út 92.", 24));
			add(new Megallo(9, "Hajdúsámson, Dohányfermentáló Üzem", 22));
			add(new Megallo(8, "Nyíradony, Tamásipuszta", 19));
			add(new Megallo(7, "Nyíradony, Tiszavíz szövetkezeti bolt", 17));
			add(new Megallo(6, "Nyíradony,Aradványpuszta Vasút út", 14));
			add(new Megallo(5, "Nyíradony, Ruhagyár", 8));
			add(new Megallo(4, "Nyíradony, Debreceni u.70.", 7));
			add(new Megallo(3, "Nyíradony, Áruház", 5));
			add(new Megallo(2, "Nyíradony, Szakolykert vasúti átjáró", 1));
			add(new Megallo(1, "Nyíradony, Szakolykert", 0));

		}
	};

	public Megállók() {
		HS_DB = new LinkedList<Megallo>();
		HS_DB.add(new Megallo(9, "Hajdúsámson, autóbusz-forduló", 0));
		HS_DB.add(new Megallo(10, "Hajdúsámson, Árpád út 92.", 2));
		HS_DB.add(new Megallo(11, "Hajdúsámson, Árpád u. 26.", 4));
		HS_DB.add(new Megallo(12, "Hajdúsámson, városháza", 6));
		HS_DB.add(new Megallo(13, "Hajdúsámson, Petõfi utca", 8));
		HS_DB.add(new Megallo(14, "Sámsonkert, Kiscsere", 10));
		HS_DB.add(new Megallo(1415, "Sámsonkert, Kiscsere", 10));
		HS_DB.add(new Megallo(18, "Sámsonkert bejárati út", 11));
		HS_DB.add(new Megallo(19, "Szikigyakor", 13));
		HS_DB.add(new Megallo(20, "Debrecen, Vaskapu", 15));
		HS_DB.add(new Megallo(21, "Útkaparó Presszó (471-es út)", 17));
		HS_DB.add(new Megallo(22, "Debrecen, FREÓNIA Kft.", 19));
		HS_DB.add(new Megallo(23, "Debrecen, Hõforrás 3. sz. kapu", 21));
		HS_DB.add(new Megallo(24, "Debrecen, Sámsoni u. ABC", 25));
		HS_DB.add(new Megallo(25, "Debrecen, Kassai u. (Árpád tér)", 29));
		HS_DB.add(new Megallo(26, "Debrecen, Hajnal u. 9-11", 32));
		HS_DB.add(new Megallo(27, "Debrecen, vasútállomás", 37));
		HS_DB.add(new Megallo(28, "Debrecen, autóbusz-állomás", 40));

		NA_DB = new LinkedList<Megallo>();
		NA_DB.add(new Megallo(3, "Nyíradony, Áruház", 0));
		NA_DB.add(new Megallo(4, "Nyíradony, Debreceni u.70.", 2));
		NA_DB.add(new Megallo(5, "Nyíradony, Ruhagyár	17:28", 3));
		NA_DB.add(new Megallo(6, "Nyíradony,Aradványpuszta Vasút út", 9));
		NA_DB.add(new Megallo(7, "Nyíradony, Tiszavíz szövetkezeti bolt", 12));
		NA_DB.add(new Megallo(8, "Nyíradony, Tamásipuszta", 14));
		NA_DB.add(new Megallo(9, "Hajdúsámson, Dohányfermentáló Üzem", 17));
		NA_DB.add(new Megallo(10, "Hajdúsámson, Árpád út 92.", 19));
		NA_DB.add(new Megallo(11, "Hajdúsámson, Árpád u. 26.", 20));
		NA_DB.add(new Megallo(12, "Hajdúsámson, városháza", 21));
		NA_DB.add(new Megallo(13, "Hajdúsámson, Petõfi utca", 23));
		NA_DB.add(new Megallo(14, "Sámsonkert, Kiscsere", 25));
		NA_DB.add(new Megallo(1415, "Sámsonkert, Kiscsere", 25));
		NA_DB.add(new Megallo(18, "Sámsonkert bejárati út", 26));
		NA_DB.add(new Megallo(19, "Szikigyakor", 28));
		NA_DB.add(new Megallo(20, "Debrecen, Vaskapu", 30));
		NA_DB.add(new Megallo(21, "Útkaparó Presszó (471-es út)", 32));
		NA_DB.add(new Megallo(22, "Debrecen, FREÓNIA Kft.", 34));
		NA_DB.add(new Megallo(23, "Debrecen, Hõforrás 3. sz. kapu", 36));
		NA_DB.add(new Megallo(24, "Debrecen, Sámsoni u. ABC", 40));
		NA_DB.add(new Megallo(25, "Debrecen, Kassai u. (Árpád tér)", 44));
		NA_DB.add(new Megallo(26, "Debrecen, Hajnal u. 9-11", 47));
		NA_DB.add(new Megallo(27, "Debrecen, vasútállomás", 52));
		NA_DB.add(new Megallo(28, "Debrecen, autóbusz-állomás", 55));

		SZ_DB = new LinkedList<Megallo>();
		SZ_DB.add(new Megallo(1, "Nyíradony, Szakolykert", 0));
		SZ_DB.add(new Megallo(2, "Nyíradony, Szakolykert vasúti átjáró", 1));
		SZ_DB.add(new Megallo(3, "Nyíradony, Áruház", 5));
		SZ_DB.add(new Megallo(4, "Nyíradony, Debreceni u.70.", 7));
		SZ_DB.add(new Megallo(5, "Nyíradony, Ruhagyár	17:28", 8));
		SZ_DB.add(new Megallo(6, "Nyíradony,Aradványpuszta Vasút út", 14));
		SZ_DB.add(new Megallo(7, "Nyíradony, Tiszavíz szövetkezeti bolt", 17));
		SZ_DB.add(new Megallo(8, "Nyíradony, Tamásipuszta", 19));
		SZ_DB.add(new Megallo(9, "Hajdúsámson, Dohányfermentáló Üzem", 22));
		SZ_DB.add(new Megallo(10, "Hajdúsámson, Árpád út 92.", 24));
		SZ_DB.add(new Megallo(11, "Hajdúsámson, Árpád u. 26.", 25));
		SZ_DB.add(new Megallo(12, "Hajdúsámson, városháza", 26));
		SZ_DB.add(new Megallo(13, "Hajdúsámson, Petõfi utca", 28));
		SZ_DB.add(new Megallo(14, "Sámsonkert, Kiscsere", 30));
		SZ_DB.add(new Megallo(1415, "Sámsonkert, Kiscsere", 30));
		SZ_DB.add(new Megallo(18, "Sámsonkert bejárati út", 31));
		SZ_DB.add(new Megallo(19, "Szikigyakor", 33));
		SZ_DB.add(new Megallo(20, "Debrecen, Vaskapu", 35));
		SZ_DB.add(new Megallo(21, "Útkaparó Presszó (471-es út)", 37));
		SZ_DB.add(new Megallo(22, "Debrecen, FREÓNIA Kft.", 39));
		SZ_DB.add(new Megallo(23, "Debrecen, Hõforrás 3. sz. kapu", 41));
		SZ_DB.add(new Megallo(24, "Debrecen, Sámsoni u. ABC", 45));
		SZ_DB.add(new Megallo(25, "Debrecen, Kassai u. (Árpád tér)", 49));
		SZ_DB.add(new Megallo(26, "Debrecen, Hajnal u. 9-11", 52));
		SZ_DB.add(new Megallo(27, "Debrecen, vasútállomás", 57));
		SZ_DB.add(new Megallo(28, "Debrecen, autóbusz-állomás", 60));

		SK_DB = new LinkedList<Megallo>();
		SK_DB.add(new Megallo(1415, "Sámsonkert Szűcs utca", 0));
		SK_DB.add(new Megallo(15, "Sámsonkert Szűcs utca", 0));
		SK_DB.add(new Megallo(16, "Sámsonkert forduló", 2));
		SK_DB.add(new Megallo(17, "Sámsonkert Fő utca", 3));
		SK_DB.add(new Megallo(19, "Szikigyakor", 5));
		SK_DB.add(new Megallo(20, "Debrecen, Vaskapu", 7));
		SK_DB.add(new Megallo(21, "Útkaparó Presszó (471-es út)", 9));
		SK_DB.add(new Megallo(22, "Debrecen, FREÓNIA Kft.", 11));
		SK_DB.add(new Megallo(23, "Debrecen, Hőforrás 3. sz. kapu", 13));
		SK_DB.add(new Megallo(24, "Debrecen, Sámsoni u. ABC", 17));
		SK_DB.add(new Megallo(25, "Debrecen, Kassai u. (Árpád tér)", 21));
		SK_DB.add(new Megallo(26, "Debrecen, Hajnal u. 9-11", 23));
		SK_DB.add(new Megallo(27, "Debrecen, vasútállomás", 28));
		SK_DB.add(new Megallo(28, "Debrecen, autóbusz-állomás", 34));

		HK_DB = new LinkedList<Megallo>();
		HK_DB.add(new Megallo(9, "Hajdúsámson, autóbusz-forduló", 0));
		HK_DB.add(new Megallo(10, "Hajdúsámson, Árpád út 92.", 2));
		HK_DB.add(new Megallo(11, "Hajdúsámson, Árpád u. 26.", 4));
		HK_DB.add(new Megallo(12, "Hajdúsámson, városháza", 6));
		HK_DB.add(new Megallo(13, "Hajdúsámson, Petõfi utca", 8));
		HK_DB.add(new Megallo(15, "Sámsonkert Szűcs utca", 11));
		HK_DB.add(new Megallo(1415, "Sámsonkert Szűcs utca", 11));
		HK_DB.add(new Megallo(16, "Sámsonkert forduló", 13));
		HK_DB.add(new Megallo(17, "Sámsonkert Fő utca", 14));
		HK_DB.add(new Megallo(19, "Szikigyakor", 16));
		HK_DB.add(new Megallo(20, "Debrecen, VaHKapu", 18));
		HK_DB.add(new Megallo(21, "Útkaparó Presszó (471-es út)", 20));
		HK_DB.add(new Megallo(22, "Debrecen, FREÓNIA Kft.", 22));
		HK_DB.add(new Megallo(23, "Debrecen, Hőforrás 3. sz. kapu", 24));
		HK_DB.add(new Megallo(24, "Debrecen, Sámsoni u. ABC", 28));
		HK_DB.add(new Megallo(25, "Debrecen, Kassai u. (Árpád tér)", 32));
		HK_DB.add(new Megallo(26, "Debrecen, Hajnal u. 9-11", 34));
		HK_DB.add(new Megallo(27, "Debrecen, vasútállomás", 39));
		HK_DB.add(new Megallo(28, "Debrecen, autóbusz-állomás", 45));

	}

}
