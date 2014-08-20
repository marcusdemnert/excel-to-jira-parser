package d360;

import java.util.ArrayList;
import java.util.List;

public class Epic {
    private final String name;
    private List<Story> stories = new ArrayList<Story>();

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
}
