package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
public class Parent implements Comparable<Parent>, Serializable {
    private final long id;
    private final String name;
    private final List<Child> children;


    private Parent(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.children = new ArrayList<>();
    }

    public void addChild(Child child) {
        children.add(child);
        child.setParent(this);
    }


    @Override
    public int compareTo(Parent other) {
        return Long.compare(this.id, other.id);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parent)) return false;
        Parent parent = (Parent) o;
        return id == parent.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", childrenIds=" + children.stream().map(Child::getId).toList() +
                '}';
    }


    public static class Builder {
        private long id;
        private String name;


        public Builder id(long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }


        public Parent build() { return new Parent(this); }
    }
}
