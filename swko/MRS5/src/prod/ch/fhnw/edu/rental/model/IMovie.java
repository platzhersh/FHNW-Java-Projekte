package ch.fhnw.edu.rental.model;

import java.sql.Date;

/**
 * Interface for Movie objects.
 * 
 * @author Christoph Denzler
 *
 */
public interface IMovie {

  /**
   * @return get movie's price category.
   */
  PriceCategory getPriceCategory();

  /**
   * @param aPriceCategory set the price category for this movie.
   */
  void setPriceCategory(PriceCategory aPriceCategory);

  /**
   * @return retrieve movie's title.
   */
  String getTitle();

  /**
   * @param aTitle set title of movie.
   */
  void setTitle(String aTitle);

  /**
   * @return request the release date.
   */
  Date getReleaseDate();

  /**
   * @param aReleaseDate set the release date of this movie.
   */
  void setReleaseDate(Date aReleaseDate);

  /**
   * @return whether movie is rented.
   */
  boolean isRented();

  /**
   * @param isRented marks a movie as rented or not rented.
   */
  void setRented(boolean isRented);

  /**
   * @return unique identification.
   */
  int getId();

  /**
   * @param anId can only be set once.
   */
  void setId(int anId);

  /**
   * @return the minimum age to see this film.
   */
  int getAgeRating();

}
