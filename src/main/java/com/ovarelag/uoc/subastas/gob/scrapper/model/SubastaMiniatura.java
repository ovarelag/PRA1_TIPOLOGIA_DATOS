package com.ovarelag.uoc.subastas.gob.scrapper.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.ovarelag.uoc.subastas.gob.scrapper.model.skeleton.SkeletonEntity;

public class SubastaMiniatura extends SkeletonEntity{


	@CsvBindByName(column = "MIN_titulo")
	@CsvBindByPosition(position = 1)
	private String titulo;

	@CsvBindByName(column = "MIN_Juzgado")
	@CsvBindByPosition(position = 3)
	private String juzgado;

	@CsvBindByName(column = "MIN_expediente")
	@CsvBindByPosition(position = 2)
	private String expediente;

	@CsvBindByName(column = "MIN_estado")
	@CsvBindByPosition(position = 4)
	private String estado;

	@CsvBindByName(column = "MIN_descripcion")
	@CsvBindByPosition(position = 5)
	private String descripcion;

	@CsvBindByName(column = "MIN_enlace")
	@CsvBindByPosition(position = 6)
	private String enlace;

	public SubastaMiniatura(Integer id, String mIN_titulo, String mIN_Juzgado, String mIN_expediente, String mIN_estado,
			String descripcion, String enlace) {
		super();
		this.setId(id);
		this.enlace = enlace;
		this.descripcion = descripcion;
		titulo = mIN_titulo;
		juzgado = mIN_Juzgado;
		expediente = mIN_expediente;
		estado = mIN_estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubastaMiniatura [id=");
		builder.append(id);
		builder.append(", titulo=");
		builder.append(titulo);
		builder.append(", juzgado=");
		builder.append(juzgado);
		builder.append(", expediente=");
		builder.append(expediente);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", enlace=");
		builder.append(enlace);
		builder.append("]");
		return builder.toString();
	}

}
