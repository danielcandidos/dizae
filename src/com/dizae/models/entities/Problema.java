package com.dizae.models.entities;


public class Problema {
	
	int id;
	User usuario;
	String titulo;
	String descricao;
	String categoria;
	String foto;
	double latitude,longitude;
	
	public Problema(User u, String t, String des, String cat, String f, double la, double lo){
		this.usuario = u;
		this.titulo = t;
		this.descricao = des;
		this.categoria = cat;
		this.foto = f;
		this.latitude = la;
		this.longitude = lo;
	}
	
	public Problema() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	
	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User delator) {
		this.usuario = delator;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
