package ru.reformers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Reformer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Lob
    private String shortBio;

    @Lob
    private String detailedBio;

    /** URL портрета (Wikimedia Commons, загрузка с диска или плейсхолдер). */
    @Column(name = "image_url", length = 512)
    private String imageUrl;

    private Integer birthYear;

    private Integer deathYear;

    private String era;

    // Ratings: values S/A/B/C/D/F
    private String economyRating;
    private String militaryRating;
    private String socialRating;
    private String educationRating;
    private String governanceRating;

    /** Итоговый рейтинг (вычисляется по сумме баллов пяти сфер). */
    private String overallRating;

    /** Ссылка на статью в русской Википедии. */
    @Column(name = "wikipedia_url", length = 512)
    private String wikipediaUrl;

    @Lob
    private String reforms;

    @Lob
    private String keyAchievements;

    public Reformer() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public String getDetailedBio() {
        return detailedBio;
    }

    public void setDetailedBio(String detailedBio) {
        this.detailedBio = detailedBio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getEconomyRating() {
        return economyRating;
    }

    public void setEconomyRating(String economyRating) {
        this.economyRating = economyRating;
    }

    public String getMilitaryRating() {
        return militaryRating;
    }

    public void setMilitaryRating(String militaryRating) {
        this.militaryRating = militaryRating;
    }

    public String getSocialRating() {
        return socialRating;
    }

    public void setSocialRating(String socialRating) {
        this.socialRating = socialRating;
    }

    public String getEducationRating() {
        return educationRating;
    }

    public void setEducationRating(String educationRating) {
        this.educationRating = educationRating;
    }

    public String getGovernanceRating() {
        return governanceRating;
    }

    public void setGovernanceRating(String governanceRating) {
        this.governanceRating = governanceRating;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public String getReforms() {
        return reforms;
    }

    public void setReforms(String reforms) {
        this.reforms = reforms;
    }

    public String getKeyAchievements() {
        return keyAchievements;
    }

    public void setKeyAchievements(String keyAchievements) {
        this.keyAchievements = keyAchievements;
    }
}

