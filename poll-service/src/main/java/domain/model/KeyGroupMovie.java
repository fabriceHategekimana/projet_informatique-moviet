package domain.model;

import java.io.Serializable;
import java.util.Objects;

// https://www.baeldung.com/jpa-composite-primary-keys
public class KeyGroupMovie implements Serializable {

    private final int group_id;
    private final int movie_id;

    @SuppressWarnings("unused")
    public KeyGroupMovie() {
        this.group_id = -1;
        this.movie_id = -1;
    }

    public KeyGroupMovie(int group_id, int movie_id) {
        this.group_id = group_id;
        this.movie_id = movie_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyGroupMovie that = (KeyGroupMovie) o;
        return group_id == that.group_id && movie_id == that.movie_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(group_id, movie_id);
    }
}
