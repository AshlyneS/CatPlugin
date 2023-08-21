package net.foxgenesis.cats;

import java.util.Collection;
import java.util.Set;

import net.foxgenesis.cats.listener.RandomCats;
import net.foxgenesis.util.resource.ConfigType;
import net.foxgenesis.watame.WatameBot;
import net.foxgenesis.watame.plugin.CommandProvider;
import net.foxgenesis.watame.plugin.IEventStore;
import net.foxgenesis.watame.plugin.Plugin;
import net.foxgenesis.watame.plugin.PluginConfiguration;
import net.foxgenesis.watame.plugin.SeverePluginException;

import org.apache.commons.configuration2.Configuration;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * @author Ashley
 *
 */
@PluginConfiguration(defaultFile = "/META-INF/cats/settings.properties", identifier = "catSettings", outputFile = "cats/settings.properties", type = ConfigType.PROPERTIES)
public class CatPlugin extends Plugin implements CommandProvider {
	private final String catAPIKey;

	public CatPlugin() {
		super();
		String key = null;

		for (String id : configurationKeySet()) {
			Configuration config = getConfiguration(id);
			switch (id) {
				case "catSettings" -> { key = config.getString("thecatapi_key", key); }
			}
		}

		catAPIKey = key;
	}

	@Override
	public void preInit() {}

	@Override
	protected void init(IEventStore eventStore) throws SeverePluginException {
		eventStore.registerListeners(this, new RandomCats(catAPIKey));
	}

	@Override
	public void postInit(WatameBot bot) {}

	@Override
	public void onReady(WatameBot bot) {}

	@Override
	public void close() throws Exception {

	}

	@Override
	public Collection<CommandData> getCommands() {
		return Set.of(
//				// Search cat images
				Commands.slash("cat", "Get images of cats")
						.addOption(OptionType.STRING, "breed", "Breed to search for", false, true)
						.addOption(OptionType.BOOLEAN, "server-only", "Only search for cats from server members")
						.addOption(OptionType.USER, "from", "Search for cats from a user (server-only must be true)"),

				// Upload cat pictures
				Commands.slash("catupload", "Upload a picture of a cat").setGuildOnly(true)
						.setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
						.addOption(OptionType.ATTACHMENT, "file", "Cat picture to upload", true));
	}
}
