package net.foxgenesis.cats.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Supplier;

import net.foxgenesis.cats.bean.Breed;
import net.foxgenesis.cats.bean.UploadResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.dv8tion.jda.api.entities.Message.Attachment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AbstractAPI<V> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final String API_URL;

	protected final Optional<String> apiKey;

	private final CopyOnWriteArraySet<Breed> breeds = new CopyOnWriteArraySet<>();
	private final Class<V> beanClass;
	private final Class<V[]> arrClass;

	@SuppressWarnings("unchecked")
	public AbstractAPI(@NotNull String apiURL, @Nullable String key, @NotNull Class<V> beanClass) {
		this.API_URL = Objects.requireNonNull(apiURL);
		this.apiKey = Optional.ofNullable(key);
		this.beanClass = Objects.requireNonNull(beanClass);
		this.arrClass = (Class<V[]>) beanClass.arrayType();
	}

	@NotNull
	public CompletableFuture<V[]> search(@NotNull OkHttpClient client, SearchRequest request) {
		return submit(client, newRequest(Method.GET, request.getEndpoint(), request.getQueryParameters(), null))
				.thenApplyAsync(response -> readJSONResponse(response, arrClass));
	}

	@NotNull
	public CompletableFuture<UploadResponse> uploadPicture(@NotNull OkHttpClient client, @NotNull Attachment attachment,
			@NotNull String subid) {
		// Validate attachment
		if (attachment.isVideo())
			return CompletableFuture.failedFuture(new IllegalArgumentException("Unable to upload videos"));
		if (!(attachment.getFileExtension() == null || attachment.getFileExtension().matches("jpg|jpeg|png|gif")))
			return CompletableFuture.failedFuture(new IllegalArgumentException("Invalid file type"));

		// Download image to byte array
		return attachment.getProxy().download().thenApplyAsync(in -> {
			try {
				return in.readAllBytes();
			} catch (IOException e) {
				throw new CompletionException(e);
			}
		}).thenComposeAsync(data -> {
			// Add request body data
			MultipartBody.Builder builder = new MultipartBody.Builder();
			builder.setType(MultipartBody.FORM);
			builder.addFormDataPart("sub_id", subid);
			builder.addFormDataPart("file", attachment.getFileName(),
					RequestBody.create(data, MediaType.parse(attachment.getContentType())));

			// Enqueue and map to result
			return submit(client, newRequest(Method.POST, "images/upload", null, builder::build));
		}).thenApplyAsync(response -> readJSONResponse(response, UploadResponse.class));
	}

	@NotNull
	public CompletableFuture<V> getPictureFromID(@NotNull OkHttpClient client, @NotNull String id,
			@NotNull Optional<String> subid, @NotNull Optional<Size> size, @NotNull Optional<Boolean> includeVote,
			@NotNull Optional<Boolean> includeFavorite) {
		// Construct query parameters
		Map<String, String> map = new HashMap<>();
		map.put("size", size.map(Object::toString).map(String::toLowerCase).orElse(""));
		map.put("sub_id", subid.orElse(""));
		map.put("include_vote", includeVote.map(b -> "" + b.compareTo(false)).orElse(""));
		map.put("include_favorite", includeFavorite.map(b -> "" + b.compareTo(false)).orElse(""));

		// Enqueue and map to result
		return submit(client, newRequest(Method.GET, "images/" + id, map, null))
				.thenApplyAsync(response -> readJSONResponse(response, beanClass));
	}

	@NotNull
	public CompletableFuture<Breed[]> getBreedList(@NotNull OkHttpClient client) {
		if (!breeds.isEmpty())
			return CompletableFuture.completedFuture(breeds.toArray(Breed[]::new));

		// Enqueue and map to result
		logger.debug("Getting breed list");
		return submit(client, newRequest(Method.GET, "breeds", null, null))
				.thenApplyAsync(response -> readJSONResponse(response, Breed[].class)).handleAsync((breeds, err) -> {
					if (err == null) {
						// Sort breeds by name
						Arrays.sort(breeds, (a, b) -> a.getName().compareTo(b.getName()));

						// Update breed list
						synchronized (this.breeds) {
							this.breeds.clear();
							this.breeds.addAll(List.of(breeds));
							logger.debug("Found {} breeds", breeds.length);
							return this.breeds.toArray(Breed[]::new);
						}
					}
					throw new CompletionException(err);
				});
	}

	private Request newRequest(Method method, String endpoint, Map<String, String> params, Supplier<RequestBody> body) {
		Objects.requireNonNull(method);

		// Create URL builder for the specified end point
		HttpUrl.Builder httpBuilder = HttpUrl.parse(API_URL + '/' + endpoint).newBuilder();

		// Add URL parameters
		if (params != null)
			for (Map.Entry<String, String> param : params.entrySet())
				httpBuilder.addQueryParameter(param.getKey(), param.getValue());

		// Construct a new request builder
		Builder builder = new Request.Builder();
		builder.url(httpBuilder.build());

		// Add request body if specified
		if (body != null) {
			RequestBody b = body.get();
			builder.addHeader("Content-Type", b.contentType().toString());
			builder.method(method.name().toUpperCase(), b);
		} else
			builder.addHeader("Content-Type", "application/json");

		// Add API key if present
		apiKey.ifPresent(key -> builder.addHeader("x-api-key", key));

		// Build the request
		return builder.build();
	}

	/**
	 * Enqueue a {@link Request} to an {@link OkHttpClient} and map it's
	 * {@link Callback} to a {@link CompletableFuture}.
	 * 
	 * @param client  - HTTP client to use
	 * @param request - request to enqueue
	 * 
	 * @return Returns a {@link CompletableFuture} that will complete normally when
	 *         the {@link Callback#onResponse(Call, Response)} is called. Otherwise
	 *         will complete exceptionally if the
	 *         {@link Callback#onFailure(Call, IOException)} is called.
	 */
	private CompletableFuture<Response> submit(OkHttpClient client, Request request) {
		Objects.requireNonNull(client);
		CompletableFuture<Response> callback = new CompletableFuture<>();

		logger.debug("{} {}", request.method().toUpperCase(), request.url().toString());
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				callback.completeExceptionally(arg1);
			}

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				callback.complete(arg1);
			}
		});
		return callback;
	}

	/**
	 * Map a {@link Response} to a JavaBean. The specified response's content type
	 * must be {@code application/json}.
	 * 
	 * @param <T>      JavaBean class
	 * @param response - response to get data from
	 * @param javaBean - JavaBean class to map JSON to
	 * 
	 * @return Returns an instance of the {@code javaBean} that was constructed with
	 *         the response's {@link okhttp3.ResponseBody#string() body} content
	 */
	@Nullable
	private <T> T readJSONResponse(Response response, Class<? extends T> javaBean) {
		// Ensure content type is application/json
		try (ResponseBody body = response.body()) {
			String b = body.string();
			logger.debug(b);
			if (!body.contentType().subtype().equals("json"))
				throw new CompletionException(new IOException("Returned content type is not application/json: " + b));

			// Check for empty body
			if (b.equals("[]") || b.equals("{}"))
				return null;

			// Construct the JavaBean from the response body
			ObjectMapper mapper = new ObjectMapper();
			try (JsonParser parser = mapper.createParser(b)) {
				return mapper.readValue(parser, javaBean);
			}
		} catch (IOException e) {
			throw new CompletionException(e);
		}
	}
}
