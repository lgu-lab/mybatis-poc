package org.demo.domain;

public class Comment {
	private int    id;
	private String text;

	public Comment() {
		super();
	}

	public Comment(int id, String text) {
		super();
		this.id = id ;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + "]";
	}

}