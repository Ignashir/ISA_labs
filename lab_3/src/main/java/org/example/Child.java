package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
public class Child implements Comparable<Child>, Serializable {
    private final long id;
    private final String name;
    private Parent parent;


    private Child(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    @Override
    public int compareTo(Child other) {
        return Long.compare(this.id, other.id);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child)) return false;
        Child child = (Child) o;
        return id == child.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + (parent != null ? parent.getId() : null) +
                '}';
    }


    public static class Builder {
        private long id;
        private String name;


        public Builder id(long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }


        public Child build() { return new Child(this); }
    }
}
