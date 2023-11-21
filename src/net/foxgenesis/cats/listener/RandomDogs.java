package net.foxgenesis.cats.listener;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.foxgenesis.cats.api.SearchRequest;
import net.foxgenesis.cats.api.TheDogAPI;
import net.foxgenesis.cats.bean.Breed;
import net.foxgenesis.cats.bean.DogPicture;
import net.foxgenesis.util.Pair;
import net.foxgenesis.watame.util.Colors;
import net.foxgenesis.watame.util.DiscordUtils;
import net.foxgenesis.watame.util.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.ErrorResponse;
import okhttp3.OkHttpClient;

public class RandomDogs extends ListenerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(RandomDogs.class);

	private static final Emoji EMOJI_HAPPY = Emoji.fromCustom("happeh", 478378484025131010L, false);

	private static final String FOOTER_TEXT = "The Dog API";
	private static final String FOOTER_ICON = "https://thedogapi.com/favicon.ico";

	private static final String FIELD_FORMAT = "**%s:** %s\n";
	private static final String FLAG_FORMAT = ":flag_%s:";

	private final TheDogAPI api;
	private final OkHttpClient client;

	public RandomDogs(String apiKey) {
		api = new TheDogAPI(apiKey);

		client = new OkHttpClient().newBuilder().callTimeout(3, TimeUnit.SECONDS).build();
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		try {
			switch (event.getFullCommandName()) {
				case "dog" -> {
					// Parse options
					String[] breeds = { event.getOption("breed", OptionMapping::getAsString) };

					// Build request
					SearchRequest.Builder.Default b = new SearchRequest.Builder.Default();
					b.setBreeds(breeds);
					SearchRequest request = b.build();

					// Search
					event.deferReply().queue();
					api.search(client, request).orTimeout(10, TimeUnit.SECONDS)
							.whenCompleteAsync((list, e) -> handleSearchResult(list, e, event));
				}
			}
		} catch (Exception e) {
			logger.error("Error in RandomDogs", e);
			MessageEmbed embed = Response.error("An error occured. Please try again later.");

			if (event.isAcknowledged())
				event.getHook().editOriginalEmbeds(embed).setReplace(true).queue();
			else
				event.replyEmbeds(embed).queue();
		}
	}

	@Override
	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
		switch (event.getFullCommandName()) {
			case "dog" -> {
				switch (event.getFocusedOption().getName()) {
					case "breed" -> {
						// Get all cat breeds
						api.getBreedList(client).thenAcceptAsync(breeds -> {
							// Construct a stream of breeds with all breed names and their alternative names
							Stream<Pair<String, Breed>> stream = Arrays.stream(breeds)
									.mapMulti((Breed breed, Consumer<Pair<String, Breed>> consumer) -> {
										// Add breed name
										consumer.accept(new Pair<>(breed.getName(), breed));

										// Add all alternative names if present
										boolean hasAltNames = !(breed.getAlt_names() == null
												|| breed.getAlt_names().trim().isBlank());
										if (hasAltNames)
											for (String name : breed.getAlt_names().split("[,/]"))
												consumer.accept(new Pair<>(name.trim(), breed));
									}).sorted(Comparator.comparing(a -> a.key()));

							// Filter stream if user has typed something
							String option = event.getFocusedOption().getValue();
							if (!(option == null || option.isBlank())) {
								String o = option.toLowerCase();
								stream = stream.filter(pair -> pair.key().toLowerCase().contains(o));
							}

							// Map results to choices and reply
							List<Command.Choice> choices = stream
									.map(pair -> new Command.Choice(pair.key(), pair.value().getId())).toList();
							event.replyChoices(choices.subList(0, Math.min(25, choices.size()))).queue();
						}).whenCompleteAsync((v, e) -> {
							if (e != null)
								logger.error("Error occured during api request", e);
						});
					}
				}
			}
		}
	}

	private static void handleSearchResult(DogPicture[] list, Throwable e, IReplyCallback event) {
		// Check for errors
		if (e != null) {
			event.getHook().editOriginalEmbeds(Response.error("An error occured. Please try again later.")).queue();
			logger.error("Error occured during api request", e);
			return;
		}
		if (list == null || list.length == 0) {
			event.getHook().editOriginalEmbeds(Response.error("No image found")).queue();
			return;
		}

		try {
			// Get first result
			DogPicture dog = list[0];

			// Construct embed
			EmbedBuilder builder = new EmbedBuilder();
			builder.setColor(Colors.INFO);
			builder.setImage(dog.getUrl());
			builder.setFooter(FOOTER_TEXT, FOOTER_ICON);

			StringBuilder b = new StringBuilder();

			// Append breed information if present
			if (dog.getBreeds() != null && dog.getBreeds().length > 0) {
				Breed breed = dog.getBreeds()[0];

				b.append(FIELD_FORMAT.formatted("Breed", breed.getName()));
				b.append(FIELD_FORMAT.formatted("Origin",
						FLAG_FORMAT.formatted(breed.getCountry_code().toLowerCase()) + " " + breed.getOrigin()));

				// Add alternative names if present
				if (!(breed.getAlt_names() == null || breed.getAlt_names().trim().isBlank()))
					b.append(FIELD_FORMAT.formatted("Alternative Names", breed.getAlt_names()));

				b.append(FIELD_FORMAT.formatted("Temperament", breed.getTemperament()));

				b.append(FIELD_FORMAT.formatted("Breed Group", breed.getBreed_group()));
				b.append(FIELD_FORMAT.formatted("Bred For", breed.getBred_for()));

				if (!(breed.getWikipedia_url() == null || breed.getWikipedia_url().isBlank())) {
					b.append("\n");
					b.append("[Wikipedia Page](" + breed.getWikipedia_url() + ")");
				}
			}

			// Append discord user if present
			String subid = dog.getSub_id();
			if (!(subid == null || subid.isBlank())) {
				int index = subid.indexOf(':');
				if (index == -1)
					index = subid.length();

				Member member = event.getGuild().getMemberById(subid.substring(0, index));
				if (member != null)
					builder.setAuthor(member.getEffectiveName(), null, member.getEffectiveAvatarUrl());
				else
					builder.setAuthor("Deleted User", DiscordUtils.DEFAULT_AVATAR);
			}

			// Build description
			builder.setDescription(b.toString().trim());

			// Send message
			event.getHook()
					// Add embed
					.editOriginalEmbeds(builder.build())
					// Add reaction
					.flatMap(message -> message.addReaction(EMOJI_HAPPY))
					// Send
					.queue(null, new ErrorHandler().ignore(ErrorResponse.UNKNOWN_EMOJI, ErrorResponse.MISSING_ACCESS,
							ErrorResponse.MISSING_PERMISSIONS));
		} catch (Exception e2) {
			event.getHook().editOriginalEmbeds(Response.error("An error occured. Please try again later.")).queue();
			logger.error("Error occured during api request", e2);
		}
	}
}
