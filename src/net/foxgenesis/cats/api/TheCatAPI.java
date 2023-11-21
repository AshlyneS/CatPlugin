package net.foxgenesis.cats.api;

import net.foxgenesis.cats.bean.CatPicture;

import org.jetbrains.annotations.Nullable;

public class TheCatAPI extends AbstractAPI<CatPicture> {

	public TheCatAPI(@Nullable String key) {
		super("https://api.thecatapi.com/v1", key, CatPicture.class);
	}
}
