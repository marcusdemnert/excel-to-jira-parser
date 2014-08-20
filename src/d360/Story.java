package d360;

public class Story {
    private String summary;
    private String description;

    public Story(String summary, String description) {
        this.summary = summary;
        this.description = description;
    }

    /**
     * Gets the summary for this Story.
     * 
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Gets the description for this Story.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
