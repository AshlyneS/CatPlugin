package net.foxgenesis.cats.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

import net.foxgenesis.cats.bean.CatPicture;

public class SearchRequest<T> {
	private final Map<String, String> properties;
	private final String endpoint;
	private final Class<T> responseClass;

	SearchRequest(Builder<T> builder) {
		this.properties = Map.copyOf(Objects.requireNonNull(builder.map));
		this.endpoint = Objects.requireNonNull(builder.endpoint);
		this.responseClass = Objects.requireNonNull(builder.responseClass);
	}

	public void forEach(BiConsumer<String, String> consumer) {
		for (Map.Entry<String, String> param : properties.entrySet())
			consumer.accept(param.getKey(), param.getValue());
	}

	public Map<String, String> getQueryParameters() {
		return properties;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public Class<T> getResponseType() {
		return responseClass;
	}

	public static abstract class Builder<T> implements IBuilder<SearchRequest<T>> {
		public static class Default extends Builder<CatPicture[]> {

			public Default() {
				super("images/search", CatPicture[].class);
			}

			public Default setSize(Size size) {
				map.put("size", Optional.ofNullable(size).map(Object::toString).map(String::toLowerCase).orElse(""));
				return this;
			}

			public Default setMimeTypes(String[] types) {
				map.put("mime_types",
						Optional.ofNullable(types).map(Builder::joinStrings).map(String::toLowerCase).orElse(""));
				return this;
			}

			public Default setOnlyBreeds(boolean onlyBreeds) {
				map.put("onlyBreeds", Optional.of(onlyBreeds).map(b -> "" + b.compareTo(false)).orElse(""));
				return this;
			}

			public Default setIncludeBreeds(boolean includeBreeds) {
				map.put("include_breeds", Optional.of(includeBreeds).map(b -> "" + b.compareTo(false)).orElse(""));
				return this;
			}

			public Default setIncludeCategories(boolean includeCategories) {
				map.put("include_categories",
						Optional.of(includeCategories).map(b -> "" + b.compareTo(false)).orElse(""));
				return this;
			}
		}

		public static class Uploaded extends Builder<CatPicture[]> {

			public Uploaded() {
				super("images", CatPicture[].class);
			}

			public Uploaded setSubID(String subid) {
				map.put("sub_id", Optional.ofNullable(subid).orElse(""));
				return this;
			}
		}

		private static String joinStrings(String[] arr) {
			if (arr == null)
				return "";

			String out = "";
			for (int i = 0; i < arr.length; i++) {
				String a = arr[i];
				if (a == null)
					continue;
				if (i != 0)
					out += ',';
				out += a;
			}

			return out;
		}

		private static String joinInts(int[] arr) {
			if (arr == null)
				return "";

			String out = "";
			for (int i = 0; i < arr.length; i++) {
				if (i != 0)
					out += ',';
				out += arr[i];
			}
			return out;
		}

		protected final Map<String, String> map = new HashMap<>();
		protected final String endpoint;
		protected final Class<T> responseClass;

		Builder(String endpoint, Class<T> responseClass) {
			this.endpoint = Objects.requireNonNull(endpoint);
			this.responseClass = Objects.requireNonNull(responseClass);
		}

		public Builder<T> setOrder(Order order) {
			map.put("order", Optional.ofNullable(order).map(Object::toString).orElse(""));
			return this;
		}

		public Builder<T> setPage(int page) {
			map.put("page", page + "");
			return this;
		}

		public Builder<T> setLimit(int limit) {
			map.put("limit", limit + "");
			return this;
		}

		public Builder<T> setCategories(int[] categories) {
			map.put("category_ids", Optional.ofNullable(categories).map(Builder::joinInts).orElse(""));
			return this;
		}

		public Builder<T> setBreeds(String[] breeds) {
			map.put("breed_ids", Optional.ofNullable(breeds).map(Builder::joinStrings).orElse(""));
			return this;
		}

		@Override
		public SearchRequest<T> build() {
			return new SearchRequest<>(this);
		}
	}
}
