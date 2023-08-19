package net.foxgenesis.cats.bean;

public class UploadResponse {
	private String id;
	private String url;
	private String sub_id;
	private int width;
	private int height;
	private String original_filename;

	private boolean pending;
	private boolean approved;

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

	public String getSub_id() {
		return sub_id;
	}

	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
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

	public String getOriginal_filename() {
		return original_filename;
	}

	public void setOriginal_filename(String original_filename) {
		this.original_filename = original_filename;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "UploadResponse [" + (id != null ? "id=" + id + ", " : "") + (url != null ? "url=" + url + ", " : "")
				+ (sub_id != null ? "sub_id=" + sub_id + ", " : "") + "width=" + width + ", height=" + height + ", "
				+ (original_filename != null ? "original_filename=" + original_filename + ", " : "") + "pending="
				+ pending + ", approved=" + approved + "]";
	}
}
