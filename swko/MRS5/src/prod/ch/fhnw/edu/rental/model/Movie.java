package ch.fhnw.edu.rental.model;

import java.sql.Date;
import java.util.Calendar;

import org.apache.log4j.Logger;

import ch.fhnw.edu.rental.Logging;

/**
 * Represents a movie.
 * 
 */
public class Movie {

    /**
     * Get logger instance.
     */
    private Logger logger = Logging.getLogger();

    /**
     * unique movie id.
     */
    private int id;

    /**
     * the name of the move.
     */
    private String title;

    /**
     * is true if it is rented.
     */
    private boolean rented;

    /**
     * the release date of the movie.
     */
    private Date releaseDate;

    /**
     * minimum age of user to rent the movie.
     */
    private int ageRating;

    /**
     * the rental cost of the movie.
     */
    private PriceCategory priceCategory;

    /**
     * Ctor only for testing needed.
     */
    protected Movie() {  
    }

    /**
     * @param aTitle none.
     * @param aPriceCategory none.
     * @param ageRating none.
     */
    public Movie(String aTitle, PriceCategory aPriceCategory, int ageRating) {
        this(aTitle, new Date(Calendar.getInstance().getTimeInMillis()), aPriceCategory, ageRating);
    }

    /**
     * @param aTitle none.
     * @param aReleaseDate none.
     * @param aPriceCategory none.
     * @param ageRating none.
     */
    public Movie(String aTitle, Date aReleaseDate, PriceCategory aPriceCategory, int ageRating) {
        if ((aTitle == null) || (aReleaseDate == null) || (aPriceCategory == null)) {
            throw new NullPointerException("not all input parameters are set!");
        }
        this.title = aTitle;
        this.releaseDate = aReleaseDate;
        this.priceCategory = aPriceCategory;
        this.rented = false;
        this.ageRating = ageRating;
        logger.debug("Movie object " + this.title + " created");
    }

    /**
     * @return none.
     */
    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    /**
     * @param aPriceCategory none.
     */
    public void setPriceCategory(PriceCategory aPriceCategory) {
        this.priceCategory = aPriceCategory;
    }

    /**
     * @return none.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param aTitle none.
     */
    public void setTitle(String aTitle) {
        if (this.title != null) {
            logger.warn("Tried to overwrite title of movie " + this.title + " to " + aTitle);
            throw new IllegalStateException();
        }
        this.title = aTitle;
    }

    /**
     * @return none.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param aReleaseDate none.
     */
    public void setReleaseDate(Date aReleaseDate) {
        if (this.releaseDate != null) {
            logger.warn("Tried to overwrite release date of movie");
            throw new IllegalStateException();
        }
        this.releaseDate = aReleaseDate;
    }

    /**
     * @return none.
     */
    public boolean isRented() {
        return rented;
    }

    /**
     * @param isRented none.
     */
    public void setRented(boolean isRented) {
        this.rented = isRented;
        logger.debug("Set movie " + this.title + " rented state to " + isRented);
    }

    /**
     * @return none.
     */
    public int getId() {
        return id;
    }

    /**
     * @param anId none.
     */
    public void setId(int anId) {
        this.id = anId;
        logger.debug("Set movie " + this.title + " id to " + anId);
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return none.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + id;
        result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj none.
     * @return none.
     */
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Movie)) {
            return false;
        }

        // declare other object to avoid casts in the following
        final Movie other = (Movie) obj;
        if (id != other.id) {
            return false;
        }

        if (!equalsReleaseDate(other.getReleaseDate())) {
            return false;
        }

        if (!equalsTitle(other.getTitle())) {
            return false;
        }

        return true;
    }

    /**
     * Compares a Date to the releaseDate of this Movie.
     * 
     * @param otherReleaseDate Date to compare to
     * @return result as a boolean
     */
    private boolean equalsReleaseDate(Date otherReleaseDate) {
        if (releaseDate == null) {
            if (otherReleaseDate != null) {
                return false;
            }
        } else if (!releaseDate.equals(otherReleaseDate)) {
            return false;
        }

        return true;
    }

    /**
     * Compares a String to the title of this Movie.
     * 
     * @param otherTitle String to compare to
     * @return result as a boolean
     */
    private boolean equalsTitle(String otherTitle) {
        if (title == null) {
            if (otherTitle != null) {
                return false;
            }
        } else if (!title.equals(otherTitle)) {
            return false;
        }

        return true;
    }

    /**
     * returns the min age for the movie.
     * 
     * @return int
     */
    public int getAgeRaiting() {
        return ageRating;
    }
}
