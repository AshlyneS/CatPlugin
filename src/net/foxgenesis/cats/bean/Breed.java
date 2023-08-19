package net.foxgenesis.cats.bean;

import java.util.Objects;

public class Breed {

	private Weight weight;
	private String id;
	private String name;
	private String cfa_url;
	private String vetstreet_url;
	private String vcahospitals_url;
	private String temperament;
	private String origin;
	private String country_codes;
	private String country_code;
	private String description;
	private String life_span;
	private boolean indoor;
	private boolean lap;
	private String alt_names;

	// 0 - 5
	private int adaptability;
	private int affection_level;
	private int child_friendly;
	private int cat_friendly;
	private int dog_friendly;
	private int energy_level;
	private int grooming;
	private int health_issues;
	private int intelligence;
	private int shedding_level;
	private int social_needs;
	private int stranger_friendly;
	private int vocalisation;
	private int bidability;

	private boolean experimental;
	private boolean hairless;
	private boolean natural;
	private boolean rare;
	private boolean rex;
	private boolean suppressed_tail;
	private boolean short_legs;

	private String wikipedia_url;
	private boolean hypoallergenic;
	private String reference_image_id;
	private CatPicture image;

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCfa_url() {
		return cfa_url;
	}

	public void setCfa_url(String cfa_url) {
		this.cfa_url = cfa_url;
	}

	public String getVetstreet_url() {
		return vetstreet_url;
	}

	public void setVetstreet_url(String vetstreet_url) {
		this.vetstreet_url = vetstreet_url;
	}

	public String getVcahospitals_url() {
		return vcahospitals_url;
	}

	public void setVcahospitals_url(String vcahospitals_url) {
		this.vcahospitals_url = vcahospitals_url;
	}

	public String getTemperament() {
		return temperament;
	}

	public void setTemperament(String temperament) {
		this.temperament = temperament;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getCountry_codes() {
		return country_codes;
	}

	public void setCountry_codes(String country_codes) {
		this.country_codes = country_codes;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLife_span() {
		return life_span;
	}

	public void setLife_span(String life_span) {
		this.life_span = life_span;
	}

	public boolean getIndoor() {
		return indoor;
	}

	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}

	public boolean isLap() {
		return lap;
	}

	public void setLap(boolean lap) {
		this.lap = lap;
	}

	public String getAlt_names() {
		return alt_names;
	}

	public void setAlt_names(String alt_names) {
		this.alt_names = alt_names;
	}

	public int getAdaptability() {
		return adaptability;
	}

	public void setAdaptability(int adaptability) {
		this.adaptability = adaptability;
	}

	public int getAffection_level() {
		return affection_level;
	}

	public void setAffection_level(int affection_level) {
		this.affection_level = affection_level;
	}

	public int getChild_friendly() {
		return child_friendly;
	}

	public void setChild_friendly(int child_friendly) {
		this.child_friendly = child_friendly;
	}

	public int getCat_friendly() {
		return cat_friendly;
	}

	public void setCat_friendly(int cat_friendly) {
		this.cat_friendly = cat_friendly;
	}

	public int getDog_friendly() {
		return dog_friendly;
	}

	public void setDog_friendly(int dog_friendly) {
		this.dog_friendly = dog_friendly;
	}

	public int getEnergy_level() {
		return energy_level;
	}

	public void setEnergy_level(int energy_level) {
		this.energy_level = energy_level;
	}

	public int getGrooming() {
		return grooming;
	}

	public void setGrooming(int grooming) {
		this.grooming = grooming;
	}

	public int getHealth_issues() {
		return health_issues;
	}

	public void setHealth_issues(int health_issues) {
		this.health_issues = health_issues;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getShedding_level() {
		return shedding_level;
	}

	public void setShedding_level(int shedding_level) {
		this.shedding_level = shedding_level;
	}

	public int getSocial_needs() {
		return social_needs;
	}

	public void setSocial_needs(int social_needs) {
		this.social_needs = social_needs;
	}

	public int getStranger_friendly() {
		return stranger_friendly;
	}

	public void setStranger_friendly(int stranger_friendly) {
		this.stranger_friendly = stranger_friendly;
	}

	public int getVocalisation() {
		return vocalisation;
	}

	public void setVocalisation(int vocalisation) {
		this.vocalisation = vocalisation;
	}

	public int getBidability() {
		return bidability;
	}

	public void setBidability(int bidability) {
		this.bidability = bidability;
	}

	public boolean getExperimental() {
		return experimental;
	}

	public void setExperimental(boolean experimental) {
		this.experimental = experimental;
	}

	public boolean getHairless() {
		return hairless;
	}

	public void setHairless(boolean hairless) {
		this.hairless = hairless;
	}

	public boolean getNatural() {
		return natural;
	}

	public void setNatural(boolean natural) {
		this.natural = natural;
	}

	public boolean getRare() {
		return rare;
	}

	public void setRare(boolean rare) {
		this.rare = rare;
	}

	public boolean getRex() {
		return rex;
	}

	public void setRex(boolean rex) {
		this.rex = rex;
	}

	public boolean getSuppressed_tail() {
		return suppressed_tail;
	}

	public void setSuppressed_tail(boolean suppressed_tail) {
		this.suppressed_tail = suppressed_tail;
	}

	public boolean getShort_legs() {
		return short_legs;
	}

	public void setShort_legs(boolean short_legs) {
		this.short_legs = short_legs;
	}

	public String getWikipedia_url() {
		return wikipedia_url;
	}

	public void setWikipedia_url(String wikipedia_url) {
		this.wikipedia_url = wikipedia_url;
	}

	public boolean getHypoallergenic() {
		return hypoallergenic;
	}

	public void setHypoallergenic(boolean hypoallergenic) {
		this.hypoallergenic = hypoallergenic;
	}

	public String getReference_image_id() {
		return reference_image_id;
	}

	public void setReference_image_id(String reference_image_id) {
		this.reference_image_id = reference_image_id;
	}

	public CatPicture getImage() {
		return image;
	}

	public void setImage(CatPicture image) {
		this.image = image;
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
		Breed other = (Breed) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Breed [" + (weight != null ? "weight=" + weight + ", " : "") + (id != null ? "id=" + id + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (cfa_url != null ? "cfa_url=" + cfa_url + ", " : "")
				+ (vetstreet_url != null ? "vetstreet_url=" + vetstreet_url + ", " : "")
				+ (vcahospitals_url != null ? "vcahospitals_url=" + vcahospitals_url + ", " : "")
				+ (temperament != null ? "temperament=" + temperament + ", " : "")
				+ (origin != null ? "origin=" + origin + ", " : "")
				+ (country_codes != null ? "country_codes=" + country_codes + ", " : "")
				+ (country_code != null ? "country_code=" + country_code + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (life_span != null ? "life_span=" + life_span + ", " : "") + "indoor=" + indoor + ", lap=" + lap
				+ ", " + (alt_names != null ? "alt_names=" + alt_names + ", " : "") + "adaptability=" + adaptability
				+ ", affection_level=" + affection_level + ", child_friendly=" + child_friendly + ", cat_friendly="
				+ cat_friendly + ", dog_friendly=" + dog_friendly + ", energy_level=" + energy_level + ", grooming="
				+ grooming + ", health_issues=" + health_issues + ", intelligence=" + intelligence + ", shedding_level="
				+ shedding_level + ", social_needs=" + social_needs + ", stranger_friendly=" + stranger_friendly
				+ ", vocalisation=" + vocalisation + ", bidability=" + bidability + ", experimental=" + experimental
				+ ", hairless=" + hairless + ", natural=" + natural + ", rare=" + rare + ", rex=" + rex
				+ ", suppressed_tail=" + suppressed_tail + ", short_legs=" + short_legs + ", "
				+ (wikipedia_url != null ? "wikipedia_url=" + wikipedia_url + ", " : "") + "hypoallergenic="
				+ hypoallergenic + ", "
				+ (reference_image_id != null ? "reference_image_id=" + reference_image_id + ", " : "")
				+ (image != null ? "image=" + image : "") + "]";
	}

}
