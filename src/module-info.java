/**
 * 
 */
module watamebot.cats {
	requires transitive watamebot;
	requires static org.jetbrains.annotations;
	requires okhttp3;
	requires com.fasterxml.jackson.databind;

	exports net.foxgenesis.cats;
	exports net.foxgenesis.cats.bean to com.fasterxml.jackson.databind;

	provides net.foxgenesis.watame.plugin.Plugin with net.foxgenesis.cats.CatPlugin;
}