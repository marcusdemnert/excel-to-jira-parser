package d360.config;

import java.util.ArrayList;
import java.util.List;

public class RowConfig {

    private List<FieldConfig> fields;

    protected RowConfig() {
        fields = new ArrayList<FieldConfig>();
    }

    public void addField(FieldConfig f) {
        fields.add(f);
    }

    public List<FieldConfig> getFields() {
        return fields;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RowConfig [fields=").append(fields).append("]");
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        return result;
    }

    /* (non-Javadoc)
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
