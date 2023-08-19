package my.plugin;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.foxgenesis.watame.WatameBot;
import net.foxgenesis.watame.plugin.CommandProvider;
import net.foxgenesis.watame.plugin.IEventStore;
import net.foxgenesis.watame.plugin.Plugin;
import net.foxgenesis.watame.plugin.SeverePluginException;

import org.apache.commons.configuration2.Configuration;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * @author Ashley
 *
 */
public class PluginTemplate extends Plugin implements CommandProvider {

	@Override
	public void preInit() {}

	@Override
	protected void init(IEventStore eventStore) throws SeverePluginException {}

	@Override
	public void postInit(WatameBot bot) {}

	@Override
	public void onReady(WatameBot bot) {}

	@Override
	public void close() throws Exception {

	}

	@Override
	public Collection<CommandData> getCommands() {
		return Set.of();
	}

	@Override
	protected void onConstruct(Properties meta, Map<String, Configuration> configs) {}
}
