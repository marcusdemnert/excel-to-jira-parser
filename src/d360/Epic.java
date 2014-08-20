package d360;

import java.util.ArrayList;
import java.util.List;

public class Epic {
    private final String name;
    private List<Story> stories = new ArrayList<Story>();

    private String component;

    private String deliveryPackage;

    public Epic(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this epic.
     * 
     * @return the name of this epic.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds the given story to this Epic.
     * 
     * @param story
     *            the story to add to this epic.
     */
    public void addStory(Story story) {
        stories.add(story);
    }

    /**
     * Gets the stories that belongs to this epic.
     * 
     * @return a <code>List</code> of <code>Story</code> objects.
     */
    public List<Story> getStories() {
        return stories;
    }

    /**
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * @param component
     *            the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return the deliveryPackage
     */
    public String getDeliveryPackage() {
        return deliveryPackage;
    }

    /**
     * @param deliveryPackage
     *            the deliveryPackage to set
     */
    public void setDeliveryPackage(String deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Epic [name=").append(name).append(", stories=")
                .append(stories).append(", component=").append(component)
                .append(", deliveryPackage=").append(deliveryPackage)
                .append("]");
        return builder.toString();
    }

}
