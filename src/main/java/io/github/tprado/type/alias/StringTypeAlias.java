package io.github.tprado.type.alias;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class StringTypeAlias {

    private final String value;

    protected StringTypeAlias(String value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    protected StringTypeAlias(Pattern pattern, String value) {
        this(value);
        if (!pattern.matcher(value).matches()) {
            throw new IllegalArgumentException(String.format("'%s' does not match '%s'", value, pattern));
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringTypeAlias that = (StringTypeAlias) o;
        return value.equals(that.value);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public final String toString() {
        return value;
    }
}
