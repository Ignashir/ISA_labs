package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildDTO implements Comparable<ChildDTO> {
    private final long id;
    private final String name;
    private final long parentId;


    private ChildDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.parentId = builder.parentId;
    }

    @Override
    public int compareTo(ChildDTO other) {
        return Long.compare(this.id, other.id);
    }


    @Override
    public String toString() {
        return "ChildDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }


    public static class Builder {
        private long id;
        private String name;
        private long parentId;


        public Builder id(long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder parentId(long parentId) { this.parentId = parentId; return this; }


        public ChildDTO build() { return new ChildDTO(this); }
    }
}
