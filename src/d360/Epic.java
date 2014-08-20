package d360;

import java.util.ArrayList;
import java.util.List;

public class Epic {
    final String name;
    List<Story> stories = new ArrayList<Story>();

    public Epic(String name) {
        this.name = name;
    }

    public void addStory(Story story) {
        stories.add(story);
    }
}
