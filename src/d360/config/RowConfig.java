package d360.config;

import java.util.ArrayList;
import java.util.List;

public class RowConfig {

    private List<FieldConfig> fields;

    /**
     * 
     * @param b
     */
    private RowConfig(Builder b) {
        fields = new ArrayList<FieldConfig>(b.fields);
    }

    /**
     * Gets all fields added to this row configuration.
     * 
     * @return all fields added to this row configuration.
     */
    public List<FieldConfig> getFields() {
        return fields;
    }

    /**
     * 
     * @author Marcus Demnert, @marcusdemnert
     * 
     */
    public static class Builder {

        private List<FieldConfig> fields;

        public Builder() {
            fields = new ArrayList<FieldConfig>();
        }

        public Builder fields(FieldConfig... fields) {
            for (FieldConfig f : fields) {
                this.fields.add(f);
            }
            return this;
        }

        public RowConfig build() {
            // Ensure all required fields have been set.
            if (fields.isEmpty()) {
                throw new RuntimeException("No fields added to row.");
            }

            return new RowConfig(this);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RowConfig [fields=").append(fields).append("]");
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RowConfig other = (RowConfig) obj;
        if (fields == null) {
            if (other.fields != null) {
                return false;
            }
        }
        else if (!fields.equals(other.fields)) {
            return false;
        }
        return true;
    }

}
