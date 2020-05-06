import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;


/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {

    /**
     * Maximum rating allowed
     */
    public final float MAX_RATING = 5.0f;

    /**
     * Minimum rating allowed
     */
    public final float MIN_RATING = 0.0f;

    /**
     * Minimum page number allowed
     */
    public final int MIN_PAGES = 0;

    /**
     * Book title
     */
    private final String title;

    /**
     * List of authors
     */
    private final String[] authors;

    /**
     * Book rating
     */
    private final float rating;

    /**
     * ISBN of the book
     */
    private final String ISBN;

    /**
     * Number of pages
     */
    private final int pages;

    /**
     * Create a book entry given all the book details
     *
     * @param title   Title of the book
     * @param authors Authors of the book
     * @param rating  Book rating (must be between MIN_RATING and MAX_RATING)
     * @param ISBN    ISBN of the book
     * @param pages   Page number of the book (must not get below MIN_PAGES)
     * @throws IllegalArgumentException if rating or page number are invalid, or any element in author list is null
     * @throws NullPointerException     if title, authors or ISBN is null
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        Objects.requireNonNull(title, "Given book title must not be null");
        Objects.requireNonNull(authors, "Given authors' array must not be null");
        Objects.requireNonNull(ISBN, "Given ISBN is null");
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(String.format("The rating must be between %f and %f", MIN_RATING, MAX_RATING));
        }
        if (pages < MIN_PAGES) {
            throw new IllegalArgumentException(String.format("The page number must not be less then %d", MIN_PAGES));
        }
        if (Arrays.asList(authors).contains(null)) {
            throw new IllegalArgumentException("The author list contains at least one null value");
        }

        this.title = title.strip();
        stripStringArray(authors); // Remove leading and tailing spaces of all authors
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN.strip();
        this.pages = pages;
    }

    /**
     * Getter for title
     *
     * @return title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for authors
     *
     * @return all authors of the book
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * Getter for rating
     *
     * @return book rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Getter for ISBN
     *
     * @return ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Getter for pages
     *
     * @return page number of the book
     */
    public int getPages() {
        return pages;
    }

    /**
     * Retrieve the first character of the book for grouping purposes
     *
     * @return prefix type of the book
     */
    public TitlePrefix getTitlePrefix() {
        return TitlePrefix.getTitlePrefix(this.title);
    }

    /**
     * See if the book is written by a given author
     *
     * @param author name of the author
     * @return true if a given author is one of the book authors, false otherwise
     */
    public boolean hasAuthor(String author) {
        return Arrays.asList(this.authors).contains(author);
    }

    /**
     * String representation of the book entry, including all the relevant infos of the book
     *
     * @return String representation of the book entry
     */
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("\n", "", "");
        sj.add(this.title);
        sj.add(String.format("by %s", String.join(", ", this.authors)));
        sj.add(String.format("Rating: %.2f", this.rating));
        sj.add(String.format("ISBN: %s", this.ISBN));
        sj.add(String.format("%d pages", this.pages));
        return sj.toString();
    }

    /**
     * Compare this book with another object.
     *
     * @param obj the object this book will compare to
     * @return If the object is a book and contains all exactly same infos of the book, return true. Otherwise, return false
     */
    @Override
    public boolean equals(Object obj) {
        // If the entry is compared to itself (reference to the same object), then return True
        if (this == obj) return true;
        // If the Object cannot be casted to BookEntry, then return false
        if (!(obj instanceof BookEntry)) return false;

        BookEntry bookEntry = (BookEntry) obj;

        return (this.title.equals(bookEntry.title) &&
                Arrays.equals(bookEntry.authors, authors) &&
                this.rating == bookEntry.rating &&
                this.ISBN.equals(bookEntry.ISBN) &&
                this.pages == bookEntry.pages);
    }

    /**
     * Generate hashcode given all the infos of the book entry.
     * If a.equals(b), it is guaranteed that a.hashCode() == b.hashCode().
     *
     * @return hashcode for this entry
     */
    @Override
    public int hashCode() {
        int[] hashes = {
                this.title.hashCode(),
                Arrays.hashCode(this.authors),
                Float.hashCode(this.rating),
                this.ISBN.hashCode(),
                Integer.hashCode(this.pages)
        };

        return Arrays.hashCode(hashes);
    }

    /**
     * Remove the leading and tailing spaces for every String in an array.
     *
     * @param strings the String array needed to be stripped
     */
    private void stripStringArray(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].strip();
        }
    }


}
