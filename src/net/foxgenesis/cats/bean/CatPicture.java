package net.foxgenesis.cats.bean;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatPicture {
	private String id;
	private String url;
	private int width;
	private int height;

	@JsonProperty(required = false)
	private String sub_id;
	@JsonProperty(required = false)
	private String created_at;
	@JsonProperty(required = false)
	private String original_filename;
	@JsonProperty(required = false)
	private String[] breed_ids;

	private Breed[] breeds;
	private Category[] categories;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSub_id() {
		return sub_id;
	}

	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getOriginal_filename() {
		return original_filename;
	}

	public void setOriginal_filename(String original_filename) {
		this.original_filename = original_filename;
	}

	public String[] getBreed_ids() {
		return breed_ids;
	}

	public void setBreed_ids(String[] breed_ids) {
		this.breed_ids = breed_ids;
	}

	public Breed[] getBreeds() {
		return breeds;
	}

	public void setBreeds(Breed[] breeds) {
		this.breeds = breeds;
	}

	public Category[] getCategories() {
		return categories;
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatPicture other = (CatPicture) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CatPicture [" + (id != null ? "id=" + id + ", " : "") + (url != null ? "url=" + url + ", " : "")
				+ "width=" + width + ", height=" + height + ", " + (sub_id != null ? "sub_id=" + sub_id + ", " : "")
				+ (created_at != null ? "created_at=" + created_at + ", " : "")
				+ (original_filename != null ? "original_filename=" + original_filename + ", " : "")
				+ (breed_ids != null ? "breed_ids=" + Arrays.toString(breed_ids) + ", " : "")
				+ (breeds != null ? "breeds=" + Arrays.toString(breeds) + ", " : "")
				+ (categories != null ? "categories=" + Arrays.toString(categories) : "") + "]";
	}

}
