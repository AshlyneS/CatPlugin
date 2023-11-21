package net.foxgenesis.cats.api;

import net.foxgenesis.cats.bean.DogPicture;

import org.jetbrains.annotations.Nullable;

public class TheDogAPI extends AbstractAPI<DogPicture> {

	public TheDogAPI(@Nullable String key) {
		super("https://api.thedogapi.com/v1", key, DogPicture.class);
	}
}
